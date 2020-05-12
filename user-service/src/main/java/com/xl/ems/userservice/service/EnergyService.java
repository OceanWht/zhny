package com.xl.ems.userservice.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.userservice.bean.*;
import com.xl.ems.userservice.common.RestResponse;
import com.xl.ems.userservice.config.GenericRest;
import com.xl.ems.userservice.mapper.*;
import com.xl.ems.userservice.model.*;
import com.xl.ems.userservice.utils.DateUtils;
import com.xl.ems.userservice.utils.GetUrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;


@Service
public class EnergyService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    HomeService homeService;

    @Autowired
    XlGroupanalogModelMapper xlGroupanalogModelMapper;

    @Autowired
    XlUnitCalcGroupModelMapper xlUnitCalcGroupModelMapper;

    @Autowired
    XlUnitlinkModelMapper xlUnitlinkModelMapper;

    @Autowired
    XlCoalModelMapper xlCoalModelMapper;

    @Autowired
    XlAidDidModelMapper xlAidDidModelMapper;

    @Autowired
    GetUrlUtils getUrlUtils;

    @Autowired
    GenericRest genericRest;

    @Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.8008_surface}")
    private String softUrlType;


    //第一种结构树
    private static final String UNITLINK_ONE = "1";

    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(EnergyService.class);

    /**
     * 每一个访问请求都开启一个新得线程，异步执行,提高访问速率
     *
     * @param requestMap
     * @return
     */
    //@Async("defaultThreadPool")
    public JSONObject overview(Map<String, String> requestMap) {
        LOGGER.info("EnergyService overview...begin");

        JSONObject result = new JSONObject();
        if (CollectionUtils.isEmpty(requestMap)) {
            return null;
        }
        String uid = requestMap.get("uid");
        String type = requestMap.get("type");
        String token = requestMap.get("token");
        if (Strings.isNullOrEmpty(uid) || Strings.isNullOrEmpty(type) || Strings.isNullOrEmpty(token)) {
            return null;
        }
        //缓存
        String key = uid + ":" + type + ":overview";
        if (redisTemplate.opsForValue().get(key) != null) {
            LOGGER.info("EnergyService overview...end");
            return JSONObject.parseObject(redisTemplate.opsForValue().get(key));
        }

        //获取总表路数
        List<XlGroupanalogModel> xlGroupanalogModels = xlGroupanalogModelMapper.getMetersTotal(Integer.valueOf(uid), type);
        if (CollectionUtils.isEmpty(xlGroupanalogModels)) {
            LOGGER.error("EnergyService getEnergyMonthData......xlGroupanalogModels error");
            return null;
        }
        //总表路数
        result.put("metersum", xlGroupanalogModels.size());
        //获取分表路数
        int sbls = xlGroupanalogModelMapper.getFenLuMetersTotal(Integer.valueOf(uid), type);
        if (sbls == 0) {
            LOGGER.error("EnergyService getEnergyMonthData......sbls error");
            return null;
        }
        //分表路数
        result.put("sbls", sbls);

        //获取标煤折算系数
        XlCoalModelKey coalModelKey = new XlCoalModelKey();
        coalModelKey.setUid(Integer.valueOf(uid));
        coalModelKey.setDtype(Integer.valueOf(type));
        XlCoalModel coalModel = xlCoalModelMapper.selectByPrimaryKey(coalModelKey);
        if (coalModel != null) {
            result.put("radio", coalModel.getRadio());
        }

        //获取本月，上月的计算组月数据和日数据
        //上上个月第一天
        String sdt = DateUtils.getLastLastMonthFirstDay();
        //上上个月最后一天
        String lastLastMothLastDay = DateUtils.getLastLastMonthLastDay();
        //上个月最后一天
        String lastMonthLastDay = DateUtils.getLastMonthLastDay();
        //本月当天的前一天
        String edt = DateUtils.getMonthPreDay();
        //本月第一天
        String nowMonthFirstDay = DateUtils.getMonthFirstDay();
        //上个月第一天
        String lastMonthFirstDay = DateUtils.getLastMonthFirstDay();
        //当前月
        String nowMonth = DateUtils.getMonth();
        //今年第一个月
        String yearFirstMonth = DateUtils.getYearFirstMonth();
        //去年第一个月
        String lastYearFirstMonth = DateUtils.getLastYearFirstMonth();
        //去年最后一个月
        String lastYearLastMonth = DateUtils.getLastYearLastMonth();

        Hashtable<String, String> dateTable = new Hashtable<>();
        dateTable.put("sdt", sdt);
        dateTable.put("lastLastMothLastDay", lastLastMothLastDay);
        dateTable.put("lastMonthLastDay", lastMonthLastDay);
        dateTable.put("edt", edt);
        dateTable.put("nowMonthFirstDay", nowMonthFirstDay);
        dateTable.put("lastMonthFirstDay", lastMonthFirstDay);

        dateTable.put("nowMonth", nowMonth);
        dateTable.put("yearFirstMonth", yearFirstMonth);
        dateTable.put("lastYearFirstMonth", lastYearFirstMonth);
        dateTable.put("lastYearLastMonth", lastYearLastMonth);

        //先从缓存取日数据
        String keyDay = uid + ":" + sdt + ":" + edt + ":" + type;
        if (redisTemplate.opsForValue().get(keyDay) != null) {
            List<UnitGroupMounthDataBean> unitGroupMounthDataBeans =
                    JSONArray.parseArray(redisTemplate.opsForValue().get(keyDay), UnitGroupMounthDataBean.class);
            if (CollectionUtils.isEmpty(unitGroupMounthDataBeans)) {
                LOGGER.error("EnergyService getEnergyMonthData......unitGroupMounthDataBeans error");
                return null;
            }
            getDayMaxData(result, dateTable, unitGroupMounthDataBeans, type);

        } else {
            Map<String, String> request = new HashMap<>();
            request.put("sdt", sdt);
            request.put("edt", edt);
            request.put("token", token);
            request.put("dataid", type);
            request.put("uid", uid);
            request.put("io", "1");
            List<UnitGroupMounthDataBean> unitGroupMounthDataBeans = homeService.getEnergyDayData(request);
            if (CollectionUtils.isEmpty(unitGroupMounthDataBeans)) {
                LOGGER.error("EnergyService getEnergyMonthData......unitGroupMounthDataBeans11 error");
                return null;
            }
            getDayMaxData(result, dateTable, unitGroupMounthDataBeans, type);
        }

        //去年月数据 和今年月数据
        String keyMonth = uid + ":" + lastYearFirstMonth + ":" + nowMonth + ":" + type;
        if (redisTemplate.opsForValue().get(keyMonth) != null) {
            List<UnitGroupMounthDataBean> unitGroupMounthDataBeans =
                    JSONArray.parseArray(redisTemplate.opsForValue().get(keyMonth), UnitGroupMounthDataBean.class);
            if (CollectionUtils.isEmpty(unitGroupMounthDataBeans)) {
                LOGGER.error("EnergyService getEnergyMonthData......unitGroupMounthDataBeans error");
                return null;
            }
            getMonthData(result, dateTable, unitGroupMounthDataBeans, type);
        } else {
            Map<String, String> request = new HashMap<>();
            request.put("sdt", lastYearFirstMonth);
            request.put("edt", nowMonth);
            request.put("token", token);
            request.put("dataid", type);
            request.put("uid", uid);
            request.put("io", "1");

            List<UnitGroupMounthDataBean> unitGroupMounthDataBeans = homeService.getEnergyMonthData(request);
            if (CollectionUtils.isEmpty(unitGroupMounthDataBeans)) {
                LOGGER.error("EnergyService getEnergyMonthData......unitGroupMounthDataBeans11 error");
                return null;
            }
            getMonthData(result, dateTable, unitGroupMounthDataBeans, type);
        }

        //返回之前，存入缓存
        if (redisTemplate.opsForValue().get(key) == null) {
            redisTemplate.opsForValue().set(key, JSONObject.toJSONString(result));
            redisTemplate.expire(key, 1, TimeUnit.HOURS);
        }
        LOGGER.info("EnergyService overview...end");
        return result;
    }

    private void getMonthData(JSONObject result, Hashtable<String, String> table, List<UnitGroupMounthDataBean> unitGroupMounthDataBeans, String type) {
        //去年月数据
        List<UnitGroupMounthDataBean> lastYearMonthData = new ArrayList<>();
        //今年月数据
        List<UnitGroupMounthDataBean> yearMonthData = new ArrayList<>();
        unitGroupMounthDataBeans.forEach(ub -> {
            //去年月数据
            if (ub.getDt().compareTo(table.get("lastYearFirstMonth")) >= 0 &&
                    ub.getDt().compareTo(table.get("lastYearLastMonth")) <= 0 && ub.getDataid().equals(type)) {
                lastYearMonthData.add(ub);
            }

            //今年月数据
            if (ub.getDt().compareTo(table.get("yearFirstMonth")) >= 0 &&
                    ub.getDt().compareTo(table.get("nowMonth")) <= 0 && ub.getDataid().equals(type)) {
                yearMonthData.add(ub);
            }
        });

        if (!CollectionUtils.isEmpty(lastYearMonthData)) {
            result.put("lastYearMonthData", lastYearMonthData);
        }
        if (!CollectionUtils.isEmpty(yearMonthData)) {
            result.put("yearMonthData", yearMonthData);
        }

    }

    /**
     * 获取日数据最大值
     *
     * @param result
     * @param unitGroupMounthDataBeans
     */
    private void getDayMaxData(JSONObject result, Hashtable<String, String> table, List<UnitGroupMounthDataBean> unitGroupMounthDataBeans, String type) {
        //本月日用水量
        List<UnitGroupMounthDataBean> nowmonth_data = new ArrayList<>();
        //上月日用水量
        List<UnitGroupMounthDataBean> oldmonth_data = new ArrayList<>();

        List<UnitGroupMounthDataBean> lastlastmonth_data = new ArrayList<>();


        unitGroupMounthDataBeans.forEach(ub -> {

            if (ub.getDt().compareTo(table.get("nowMonthFirstDay")) >= 0 && ub.getDt().compareTo(table.get("edt")) <= 0
                    && ub.getDataid().equals(type)) {
                nowmonth_data.add(ub);
            }

            if (ub.getDt().compareTo(table.get("lastMonthFirstDay")) >= 0 && ub.getDt().compareTo(table.get("lastMonthLastDay")) <= 0
                    && ub.getDataid().equals(type)) {
                oldmonth_data.add(ub);
            }

            if (ub.getDt().compareTo(table.get("sdt")) >= 0 && ub.getDt().compareTo(table.get("lastLastMothLastDay")) <= 0
                    && ub.getDataid().equals(type)) {
                lastlastmonth_data.add(ub);
            }


        });


        //取最大值
        if (!CollectionUtils.isEmpty(nowmonth_data)) {
            double nowmonth_max_water = 0;
            double nowmonth_sum = 0;
            for (UnitGroupMounthDataBean ub : nowmonth_data) {
                if (Strings.isNullOrEmpty(ub.getSum())) {
                    continue;
                }
                if (Double.valueOf(ub.getSum()) >= nowmonth_max_water) {
                    nowmonth_max_water = Double.valueOf(ub.getSum());
                }

                nowmonth_sum = nowmonth_sum + Double.valueOf(ub.getSum());
            }
            //本月最大用水量
            result.put("nowmonth_max_water", nowmonth_max_water);

            result.put("nowmonth_water", nowmonth_sum);
        }


        //取最大值
        if (!CollectionUtils.isEmpty(oldmonth_data)) {
            double oldmonth_max_water = 0;
            double oldmonth_sum = 0;
            for (UnitGroupMounthDataBean ub : oldmonth_data) {
                if (Strings.isNullOrEmpty(ub.getSum())) {
                    continue;
                }
                if (Double.valueOf(ub.getSum()) >= oldmonth_max_water) {
                    oldmonth_max_water = Double.valueOf(ub.getSum());
                }

                oldmonth_sum = oldmonth_sum + Double.valueOf(ub.getSum());
            }
            //上月最大用水量
            result.put("oldmonth_max_water", oldmonth_max_water);

            result.put("oldmonth_water", oldmonth_sum);
        }

        //取最大值
        if (!CollectionUtils.isEmpty(lastlastmonth_data)) {
            double lastlastmonth_max_water = 0;
            double lastlastmonth_sum = 0;
            for (UnitGroupMounthDataBean ub : lastlastmonth_data) {
                if (Strings.isNullOrEmpty(ub.getSum())) {
                    continue;
                }
                if (Double.valueOf(ub.getSum()) >= lastlastmonth_max_water) {
                    lastlastmonth_max_water = Double.valueOf(ub.getSum());
                }

                lastlastmonth_sum = lastlastmonth_sum + Double.valueOf(ub.getSum());
            }
            //上月最大用水量
            result.put("oldsmonth_max_water", lastlastmonth_max_water);

            result.put("oldsmonth_water", lastlastmonth_sum);
        }

        //本月用水数据
        result.put("nowmoth_data", nowmonth_data);
        //上月用水数据
        result.put("oldmonth_data", oldmonth_data);
        //上上月用水数据
        result.put("lastlastmonth_data", lastlastmonth_data);
    }

    /**
     * 获取结构树
     *
     * @param requestMap
     * @return
     */
    public JSONObject unitTree(Map<String, String> requestMap) {
        LOGGER.info("EnergyService unitTree...begin");

        JSONObject result = new JSONObject();


        String flag = requestMap.get("flag");
        String uid = requestMap.get("uid");
        String dataid = requestMap.get("dataid");
        String ioo = requestMap.get("ioo");

        if (Strings.isNullOrEmpty(flag) || Strings.isNullOrEmpty(uid)
                || Strings.isNullOrEmpty(dataid) || Strings.isNullOrEmpty(ioo)) {
            LOGGER.error("EnergyService unitTree......params dataid or ioo error");
            return null;
        }

        //先从缓存找
        String key = uid + ":" + dataid + ":treelist:" + flag;
        if (redisTemplate.opsForValue().get(key) != null) {
            result.put("treelist", JSONObject.parse(redisTemplate.opsForValue().get(key)));
            LOGGER.info("EnergyService unitTree...redis end");
            return result;
        }
        //第一种结构树
        if (flag.equals(UNITLINK_ONE)) {

            List<XlUnitlinkModel> xlUnitlinkModelList = xlUnitlinkModelMapper.selectUnitLink(Integer.valueOf(uid), dataid);
            if (CollectionUtils.isEmpty(xlUnitlinkModelList)) {
                LOGGER.error("EnergyService unitTree......selectUnitLink xlUnitlinkModelList error");
                return null;
            }

            //根节点
            UnitLinkTree root = new UnitLinkTree();
            List<UnitLinkTree> unitLinkTreeList = new ArrayList<>();
            for (XlUnitlinkModel xlUnitlinkModel : xlUnitlinkModelList) {
                //找出根节点
                if (xlUnitlinkModel.getUid().equals(Integer.valueOf(uid))) {
                    root.setTitle(xlUnitlinkModel.getName());
                    root.setDataid(dataid);
                    root.setId(uid);
                    root.setParentid("0");
                }
                //一级节点
                if (xlUnitlinkModel.getParentunitid() != null && xlUnitlinkModel.getParentunitid().equals(Integer.valueOf(uid))) {
                    UnitLinkTree child = new UnitLinkTree();
                    child.setParentid(String.valueOf(xlUnitlinkModel.getParentunitid()));
                    child.setTitle(xlUnitlinkModel.getName());
                    child.setId(String.valueOf(xlUnitlinkModel.getUid()));
                    child.setDataid(dataid);
                    unitLinkTreeList.add(child);
                }
            }

            //找出一级节点下面的节点
            setChild(dataid, xlUnitlinkModelList, unitLinkTreeList);
            root.setChildren(unitLinkTreeList);
            //放入缓存
            if (redisTemplate.opsForValue().get(key) == null) {
                redisTemplate.opsForValue().set(key, JSONObject.toJSONString(root));
                //缓存十五分钟
                redisTemplate.expire(key, 15, TimeUnit.MINUTES);
            }
            //结构树
            result.put("treelist", root);

            LOGGER.info("EnergyService unitTree...end1");
            return result;

        }
        LOGGER.info("EnergyService unitTree...end2");
        return null;
    }

    private void setChild(String dataid, List<XlUnitlinkModel> xlUnitlinkModelList, List<UnitLinkTree> unitLinkTreeList) {
        if (!CollectionUtils.isEmpty(unitLinkTreeList)) {
            for (UnitLinkTree unitLinkTree1 : unitLinkTreeList) {
                List<UnitLinkTree> childs = new ArrayList<>();
                for (XlUnitlinkModel xlUnitlinkModel : xlUnitlinkModelList) {
                    if (xlUnitlinkModel.getParentunitid() != null && xlUnitlinkModel.getParentunitid().equals(Integer.valueOf(unitLinkTree1.getId()))) {
                        UnitLinkTree child = new UnitLinkTree();
                        child.setParentid(String.valueOf(xlUnitlinkModel.getParentunitid()));
                        child.setTitle(xlUnitlinkModel.getName());
                        child.setId(String.valueOf(xlUnitlinkModel.getUid()));
                        child.setDataid(dataid);
                        childs.add(child);
                    }
                }
                if (!CollectionUtils.isEmpty(childs)) {
                    unitLinkTree1.setChildren(childs);
                    setChild(dataid, xlUnitlinkModelList, childs);
                }

            }

        }
    }

    /**
     * 获取分路数据：实时数据，日数据，月数据，年数据，根据type区分
     *
     * @param requestMap
     * @return
     */
    public JSONObject getAllData(Map<String, String> requestMap) {
        LOGGER.info("EnergyService getAllData...begin");
        if (requestMap == null) {
            LOGGER.info("EnergyService getAllData...requestMap error");
            return null;
        }

        String uid = requestMap.get("uid");
        String token = requestMap.get("token");
        String dataid = requestMap.get("dataid");
        String type = requestMap.get("type");
        String sdt = requestMap.get("sdt");
        String edt = requestMap.get("edt");
        String flag = requestMap.get("flag");
        if (Strings.isNullOrEmpty(uid) || Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(dataid)
                || Strings.isNullOrEmpty(type) || Strings.isNullOrEmpty(sdt) || Strings.isNullOrEmpty(edt)
                || Strings.isNullOrEmpty(flag)) {
            LOGGER.info("EnergyService getAllData...params error");
            return null;
        }

        //调用接口，修改密码
        //api接口服务
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("EnergyService getEnergyMonthData......apiUrl or softUrl error");
            return null;
        }


        JSONObject result = new JSONObject();

        //根据type的类型获取不同类型的数据

        //模拟量档案集合
        List<AnalogDocBean> analogDocBeans = null;
        //设备档案集合 有删除操作，用线程安全的不会报ConcurrentModificationException
        List<MeterArchiveBean> meterArchiveBeans = null;

        //先从缓存取
        String key = uid + ":" + type + ":" + dataid + ":" + sdt + ":" + edt;
        if (redisTemplate.opsForValue().get(key) != null) {
            meterArchiveBeans = JSONArray.parseArray(redisTemplate.opsForValue().get(key), MeterArchiveBean.class);
            result.put("meterArchiveBeans", meterArchiveBeans);
            LOGGER.info("EnergyService getAllData...end111");
            return result;
        }
        //模拟量曲线数据
        List<AnalogTSCurveDataBean> analogTSCurveDataBeans = null;
        Object apiResult = null;
        //模拟量日数据
        List<AnalogDataBean> analogDataBeans = null;
        List<AnalogMonthDataBean> analogMonthDataBeans = null;

        //默认展示全部设备的数据
        List<XlAidDidModel> xlAidDidModels = null;
        //默认展示所有的表数据
        if (flag.equals("all")) {
            xlAidDidModels = xlAidDidModelMapper.selectByUidDataid(Integer.valueOf(uid), Integer.valueOf(dataid));

        } else {
            //点击树结构不同的单元
            HashMap<String, Integer> mapp = new HashMap<>();
            mapp.put("uid", Integer.valueOf(uid));
            mapp.put("dataid", Integer.valueOf(dataid));
            xlAidDidModels = xlAidDidModelMapper.getByUidDataid2(mapp);
        }
        if (!CollectionUtils.isEmpty(xlAidDidModels)) {
            SetAnaDocAndArchive setAnaDocAndArchive = new SetAnaDocAndArchive(token, apiUrl, softUrl, xlAidDidModels).invoke();
            analogDocBeans = setAnaDocAndArchive.getAnalogDocBeans();
            meterArchiveBeans = setAnaDocAndArchive.getMeterArchiveBeans();
            Map<String, Object> request = setAnaDocAndArchive.getRequest();


            if (CollectionUtils.isEmpty(analogDocBeans) || CollectionUtils.isEmpty(meterArchiveBeans)) {
                LOGGER.error("EnergyService getAllData...analogDocBeans or meterArchiveBeans error");
                return null;
            }

            //获取模拟量档案的曲线数据,并设置每个模拟量的曲线数据
            request.put("sdt", sdt);
            request.put("edt", edt);
            ResponseEntity<RestResponse<Object>> dataEntity = null;
            switch (type) {
                case "branch":
                    dataEntity = genericRest.post(apiUrl + "/GetMeterAnalogTSCurveData", request,
                            new ParameterizedTypeReference<RestResponse<Object>>() {
                            });

                    if (dataEntity != null && dataEntity.getStatusCode().equals(HttpStatus.OK)) {
                        apiResult = dataEntity.getBody().getResult();
                        analogTSCurveDataBeans = JSONArray.parseArray(JSONObject.toJSONString(apiResult), AnalogTSCurveDataBean.class);
                    }
                    if (CollectionUtils.isEmpty(analogTSCurveDataBeans)) {
                        LOGGER.error("EnergyService getAllData...analogTSCurveDataBeans error");
                        return null;
                    }

                    //设置模拟量档案里的曲线数据
                    for (AnalogDocBean analogDocBean : analogDocBeans) {
                        setTsCurveData(analogTSCurveDataBeans, analogDocBean);
                    }

                    //设置设备档案下的模拟量档案
                    for (MeterArchiveBean meterArchiveBean : meterArchiveBeans) {
                        setAnaDoc(analogDocBeans, xlAidDidModels, meterArchiveBean,uid,dataid);
                    }
                    break;
                case "branch_day":
                    analogDataBeans = getAnalogDataBeans(apiUrl, analogDataBeans, request);
                    if (analogDataBeans == null) {
                        LOGGER.error("EnergyService getAllData....branch_day error");
                        return null;
                    }
                    //设置模拟量档案里的日数据
                    for (AnalogDocBean analogDocBean : analogDocBeans) {
                        setTsCurveData(analogDataBeans, analogDocBean);
                    }

                    //设置设备档案下的模拟量档案
                    for (MeterArchiveBean meterArchiveBean : meterArchiveBeans) {
                        setAnaDoc(analogDocBeans, xlAidDidModels, meterArchiveBean,uid,dataid);
                    }
                    break;
                case "branch_month":
                    analogDataBeans = getAnalogDataBeans(apiUrl, analogDataBeans, request);
                    if (analogDataBeans == null) {
                        LOGGER.error("EnergyService getAllData....branch_month error");
                        return null;
                    }

                    //设置模拟量档案里的日数据
                    for (AnalogDocBean analogDocBean : analogDocBeans) {
                        setTsCurveData(analogDataBeans, analogDocBean);
                    }

                    //设置设备档案下的模拟量档案
                    for (MeterArchiveBean meterArchiveBean : meterArchiveBeans) {
                        setAnaDoc(analogDocBeans, xlAidDidModels, meterArchiveBean,uid,dataid);
                    }
                    break;
                case "branch_year":
                    dataEntity = genericRest.post(apiUrl + "/GetAnalogMonthData", request,
                            new ParameterizedTypeReference<RestResponse<Object>>() {
                            });
                    if (dataEntity != null && dataEntity.getStatusCode().equals(HttpStatus.OK)) {
                        apiResult = dataEntity.getBody().getResult();
                        analogMonthDataBeans = JSONArray.parseArray(JSONObject.toJSONString(apiResult), AnalogMonthDataBean.class);
                    }
                    if (CollectionUtils.isEmpty(analogMonthDataBeans)) {
                        LOGGER.error("EnergyService getAllData...GetAnalogMonthData  error");
                        return null;
                    }

                    //设置模拟量档案里的月数据
                    for (AnalogDocBean analogDocBean : analogDocBeans) {
                        setTsCurveData(analogMonthDataBeans, analogDocBean);
                    }

                    //设置设备档案下的模拟量档案
                    for (MeterArchiveBean meterArchiveBean : meterArchiveBeans) {
                        setAnaDoc(analogDocBeans, xlAidDidModels, meterArchiveBean,uid,dataid);
                    }
                    break;
                default:
                    break;
            }

        }
        if (redisTemplate.opsForValue().get(key) == null) {
            redisTemplate.opsForValue().set(key, JSONArray.toJSONString(meterArchiveBeans));
            redisTemplate.expire(key, 15, TimeUnit.MINUTES);
        }
        result.put("meterArchiveBeans", meterArchiveBeans);

        LOGGER.info("EnergyService getAllData...end");
        return result;
    }

    private List<AnalogDataBean> getAnalogDataBeans(String apiUrl, List<AnalogDataBean> analogDataBeans, Map<String, Object> request) {
        ResponseEntity<RestResponse<Object>> dataEntity;
        Object apiResult;
        dataEntity = genericRest.post(apiUrl + "/GetAnalogDayData", request,
                new ParameterizedTypeReference<RestResponse<Object>>() {
                });
        if (dataEntity != null && dataEntity.getStatusCode().equals(HttpStatus.OK)) {
            apiResult = dataEntity.getBody().getResult();
            analogDataBeans = JSONArray.parseArray(JSONObject.toJSONString(apiResult), AnalogDataBean.class);
        }
        if (CollectionUtils.isEmpty(analogDataBeans)) {
            LOGGER.error("EnergyService getAllData...analogDataBeans Day error");
            return null;
        }
        return analogDataBeans;
    }

    private void setTsCurveData(List ObjList, AnalogDocBean analogDocBean) {
        List<AnalogTSCurveDataBean> tsCurveDataBeans = new ArrayList<>();
        List<AnalogDataBean> analogDataBeans = new ArrayList<>();
        List<AnalogMonthDataBean> analogMonthDataBeans = new ArrayList<>();
        for (Object obj : ObjList) {
            //曲线数据
            if (obj instanceof AnalogTSCurveDataBean) {
                AnalogTSCurveDataBean tsCurveDataBean = (AnalogTSCurveDataBean) obj;
                if (tsCurveDataBean.getDid().equals(analogDocBean.getAid())) {
                    tsCurveDataBeans.add(tsCurveDataBean);
                }
            }
            //日数据
            if (obj instanceof AnalogDataBean) {
                AnalogDataBean dataBean = (AnalogDataBean) obj;
                if (dataBean.getAid().equals(analogDocBean.getAid())) {
                    analogDataBeans.add(dataBean);
                }

            }
            //日数据
            if (obj instanceof AnalogMonthDataBean) {
                AnalogMonthDataBean dataBean = (AnalogMonthDataBean) obj;
                if (dataBean.getAid().equals(analogDocBean.getAid())) {
                    analogMonthDataBeans.add(dataBean);
                }

            }

        }

        if (!CollectionUtils.isEmpty(tsCurveDataBeans)) {
            analogDocBean.setAnalogTSCurveDataBeans(tsCurveDataBeans);
        }

        if (!CollectionUtils.isEmpty(analogDataBeans)) {
            analogDocBean.setAnalogDayDataBeans(analogDataBeans);
        }

        if (!CollectionUtils.isEmpty(analogMonthDataBeans)) {
            analogDocBean.setAnalogMonthDataBeans(analogMonthDataBeans);
        }
    }

    public void setAnaDoc(List<AnalogDocBean> analogDocBeans, List<XlAidDidModel> xlAidDidModels, MeterArchiveBean meterArchiveBean,String uid,String dataid) {
        List<XlGroupanalogModel> groupAnalog = xlGroupanalogModelMapper.selectGroupAnalog(Integer.valueOf(uid),Integer.valueOf(dataid));
        List<AnalogDocBean> docBeans = new ArrayList<>();
        for (XlAidDidModel xlAidDidModel : xlAidDidModels) {
            for (AnalogDocBean analogDocBean : analogDocBeans) {
                if (analogDocBean.getAid().equals(String.valueOf(xlAidDidModel.getAid())) && xlAidDidModel.getDid().equals(Integer.valueOf(meterArchiveBean.getDid()))) {

                    docBeans.add(analogDocBean);
                }
            }
        }
        if (!CollectionUtils.isEmpty(docBeans)) {
            meterArchiveBean.setAnalogDocBeanList(docBeans);
        }

        if (!CollectionUtils.isEmpty(groupAnalog)){
            meterArchiveBean.setGroupAnalog(groupAnalog);
        }
    }

    public class SetAnaDocAndArchive {
        private String token;
        private String apiUrl;
        private String softUrl;
        private List<AnalogDocBean> analogDocBeans;
        private List<MeterArchiveBean> meterArchiveBeans;
        private List<XlAidDidModel> xlAidDidModels;
        private Map<String, Object> request;

        public SetAnaDocAndArchive(String token, String apiUrl, String softUrl, List<XlAidDidModel> xlAidDidModels) {
            this.token = token;
            this.apiUrl = apiUrl;
            this.softUrl = softUrl;
            this.xlAidDidModels = xlAidDidModels;
        }

        public List<AnalogDocBean> getAnalogDocBeans() {
            return analogDocBeans;
        }

        public List<MeterArchiveBean> getMeterArchiveBeans() {
            return meterArchiveBeans;
        }

        public Map<String, Object> getRequest() {
            return request;
        }

        public SetAnaDocAndArchive invoke() {
            //模拟量ID集合
            Set<Integer> aids = new HashSet<>();
            //设备ID集合
            Set<Integer> dids = new HashSet<>();
            for (XlAidDidModel xlAidDidModel : xlAidDidModels) {
                aids.add(xlAidDidModel.getAid());
                dids.add(xlAidDidModel.getDid());
            }
            //获取模拟量档案集合
            request = new HashMap<>();
            StringBuilder stringBuilder = new StringBuilder();
            aids.forEach(aid -> {
                stringBuilder.append(aid).append(",");
            });
            //模拟量ID集合
            String aid = stringBuilder.substring(0, stringBuilder.lastIndexOf(","));
            request.put("aids", aid);
            request.put("token", token);
            request.put("url", softUrl);
            ResponseEntity<RestResponse<List<AnalogDocBean>>> entity = genericRest.post(apiUrl + "/GetAnalogDoc", request,
                    new ParameterizedTypeReference<RestResponse<List<AnalogDocBean>>>() {
                    });
            if (entity.getStatusCode().equals(HttpStatus.OK)) {
                analogDocBeans = entity.getBody().getResult();
            }

            //获取设备档案集合
            StringBuilder sb = new StringBuilder();
            dids.forEach(did -> {
                sb.append(did).append(",");
            });

            String did = sb.substring(0, sb.lastIndexOf(","));
            request.put("dids", did);
            ResponseEntity<RestResponse<CopyOnWriteArrayList<MeterArchiveBean>>> responseEntity = genericRest.post(apiUrl + "/GetMeterArchive", request,
                    new ParameterizedTypeReference<RestResponse<CopyOnWriteArrayList<MeterArchiveBean>>>() {
                    });
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                meterArchiveBeans = responseEntity.getBody().getResult();
            }
            return this;
        }
    }
}
