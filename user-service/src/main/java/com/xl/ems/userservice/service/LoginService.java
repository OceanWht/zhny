package com.xl.ems.userservice.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.userservice.common.RestResponse;
import com.xl.ems.userservice.config.GenericRest;
import com.xl.ems.userservice.mapper.*;
import com.xl.ems.userservice.model.*;
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
public class LoginService {

    @Autowired
    UserInfoModelMapper userInfoModelMapper;

    @Autowired
    XlDatacodeModelMapper xlDatacodeModelMapper;

    @Autowired
    XlUnitlinkModelMapper xlUnitlinkModelMapper;

    @Autowired
    XlUnitCalcGroupModelMapper xlUnitCalcGroupModelMapper;

    @Autowired
    XlGroupanalogModelMapper xlGroupanalogModelMapper;

    @Autowired
    XlAidDidModelMapper xlAidDidModelMapper;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    GetUrlUtils getUrlUtils;

    @Autowired
    GenericRest genericRest;

    @Value("${user.pass.patten}")
    private String patten;

    @Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.8008_surface}")
    private String softUrlType;


    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    public UserInfoModel getUserInfoByNP(String name, String pass) {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setName(name);
        userInfoModel.setPass(pass);
        UserInfoModel result = userInfoModelMapper.getUserInfoByNP(userInfoModel);
        return result;
    }

    public boolean passValidate(String pass) {
        if (pass.matches(patten)) {
            return true;
        }

        return false;
    }

    public boolean savePass(String name, String npwd) {

        UserInfoModel record = new UserInfoModel();
        record.setName(name);
        UserInfoModel userInfoModel = userInfoModelMapper.getUserInfoByNP(record);
        if (userInfoModel == null) {
            return false;
        }

        //调用接口，修改密码
        //api接口服务
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        if (Strings.isNullOrEmpty(apiUrl)) {
            return false;
        }
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(softUrl)) {
            return false;
        }

        Map<String, Object> reqBody = new HashMap<String, Object>();
        reqBody.put("userInfoModel", userInfoModel);
        reqBody.put("npwd", npwd);
        reqBody.put("url", softUrl);
        ResponseEntity<RestResponse<String>> responseEntity = genericRest.post(apiUrl + "/savePass", reqBody, new ParameterizedTypeReference<RestResponse<String>>() {
        });
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return false;
        }

        return true;
    }


    public boolean updateUserInfo(UserInfoModel userInfoModel) {
        int result = userInfoModelMapper.updateUserInfo(userInfoModel);
        if (result == 0) {
            return false;
        }

        return true;
    }

    /**
     * 登录，为减轻数据库压力，将登录信息缓存至redis,缓存时间10分钟
     *
     * @param name
     * @param pass
     * @return
     */
    public JSONArray login(final String name, final String pass) {
        if (Strings.isNullOrEmpty(name) || Strings.isNullOrEmpty(pass)) {
            return null;
        }

        //放到redis缓存里面 下一次先到缓存里面找
        String key = name + ":" + pass;
        String loginStr = redisTemplate.opsForValue().get(key);
        if (!Strings.isNullOrEmpty(loginStr)) {
            return JSONArray.parseArray(loginStr);
        }

        final String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(softUrl)) {
            return null;
        }
        final String apiUrl = getUrlUtils.getUrl(apiUrlType);
        if (Strings.isNullOrEmpty(apiUrl)) {
            return null;
        }

        Map<String, Object> reqBody = new HashMap<String, Object>();
        reqBody.put("name", name);
        reqBody.put("pass", pass);
        reqBody.put("url", softUrl);

        ResponseEntity<RestResponse<String>> responseEntity = genericRest.post(apiUrl + "/login", reqBody, new ParameterizedTypeReference<RestResponse<String>>() {
        });
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return null;
        }
        String res = responseEntity.getBody().getResult();
        JSONObject resJson = JSONObject.parseObject(res);
        JSONArray body = null;
        if (resJson.getJSONObject("header").getInteger("status").equals(0)) {
            //登录成功之后，先判断数据库中有没有该用户，没有则新增，有就更新一下token
            body = resJson.getJSONArray("body");
            final JSONObject object = (JSONObject) body.get(0);
            if (redisTemplate.opsForValue().get(key) == null) {
                //将登录信息放到缓存
                String value = JSONArray.toJSONString(body);
                redisTemplate.opsForValue().set(key, value);

                //缓存时间,1个小时
                redisTemplate.expire(key, 1, TimeUnit.HOURS);
            }
            //开启一个线程处理数据库userinfo,初始化该企业的相关基础数据
            new Thread() {
                @Override
                public synchronized void run() {
                    LOGGER.info("登录获取企业基本信息....开始");
                    //跟新userinfo
                    setUserInfo(name, pass, object);
                    //获取企业树结构
                    getUnitLink(object, softUrl, apiUrl);
                    //获取企业单元计算组档案
                    getUnitCalcGroup(object,softUrl,apiUrl);
                    //获取该企业下得模拟量档案和设备(测量点)档案
                    //1.获取计算组和模拟量关系(需要支持多个groupid,逗号分隔）
                    getGroupAnalog(object,softUrl,apiUrl);
                    //2.根据模拟量id查找上级所属设备
                    //3.获取指定设备下的所有模拟量
                    getDidByAnalogId(object,softUrl,apiUrl);
                    //4.获取测量点基本档案（设备档案）
                  //  getMeterArchive(object,softUrl,apiUrl);
                    //5.获取模拟量档案  似乎不需要，先不写
                   // getAnalogDoc(object,softUrl,apiUrl);

                    //设置unitlink的dataid
                    //setUnitLink();
                    LOGGER.info("登录获取企业基本信息....结束");
                }
            }.start();

        }
        return body;
    }

    private void getMeterArchive(JSONObject object, String softUrl, String apiUrl) {
        LOGGER.info("登录获取企业基本信息....获取设备档案 开始");
        Hashtable<String,String> requestBody = new Hashtable<>();
        String token = object.getString("token");
        requestBody.put("token",token);
        requestBody.put("url",softUrl);

        LOGGER.info("登录获取企业基本信息....获取设备档案 结束");
    }

    public void getAnalogDoc(JSONObject object, String softUrl, String apiUrl) {
        LOGGER.info("登录获取企业基本信息....获取模拟量档案 开始");


        LOGGER.info("登录获取企业基本信息....获取模拟量档案 结束");
    }

    public void getDidByAnalogId(JSONObject object, String softUrl, String apiUrl) {
        LOGGER.info("登录获取企业基本信息....根据模拟量id查找上级所属设备 开始");
        Hashtable<String,String> requestBody = new Hashtable<>();
        String token = object.getString("token");
        requestBody.put("token",token);
        requestBody.put("url",softUrl);

        List<Integer> uids = xlUnitlinkModelMapper.getUidsByUid(Integer.valueOf(object.getString("uid")));
        if (CollectionUtils.isEmpty(uids)){
            LOGGER.error("登录获取企业基本信息....根据模拟量id查找上级所属设备 获取uids 错误");
            return;
        }
        List<String> groupIds = xlUnitCalcGroupModelMapper.getGroupIds(uids);
        if (CollectionUtils.isEmpty(groupIds)){
            LOGGER.error("登录获取企业基本信息....根据模拟量id查找上级所属设备 获取groupIds 错误");
            return;
        }
        List<Integer> aids = xlGroupanalogModelMapper.getAids(groupIds);
        if (CollectionUtils.isEmpty(aids)){
            LOGGER.error("登录获取企业基本信息....根据模拟量id查找上级所属设备 获取aids 错误");
            return;
        }
        StringBuilder sb = new StringBuilder();
        aids.forEach(aid ->{
            sb.append(aid).append(",");
        });

        String aidStr = sb.substring(0,sb.lastIndexOf(","));
        requestBody.put("aid",aidStr);
        ResponseEntity<RestResponse<List<XlAidDidModel>>> entity = genericRest.post(apiUrl + "/GetDidByAnalogId", requestBody,
                new ParameterizedTypeReference<RestResponse<List<XlAidDidModel>>>() {});
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            List<XlAidDidModel> aidDidModelList = xlAidDidModelMapper.selectByUid(Integer.parseInt(object.getString("uid")));
            //获取设备ID集合
            List<XlAidDidModel> aidDidModels = entity.getBody().getResult();
            if (CollectionUtils.isEmpty(aidDidModels)){
                LOGGER.error("登录获取企业基本信息....根据模拟量id查找上级所属设备 aidDidModels 错误");
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            aidDidModels.forEach(aidDid->{
                stringBuilder.append(aidDid.getDid()).append(",");
            });


            String dids = stringBuilder.substring(0,stringBuilder.lastIndexOf(","));
            requestBody.put("did",dids);
            ResponseEntity<RestResponse<List<XlAidDidModel>>> responseEntity = genericRest.post(apiUrl + "/GetAnalogByDid", requestBody,
                    new ParameterizedTypeReference<RestResponse<List<XlAidDidModel>>>() {});
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
                List<XlAidDidModel> xlAidDidModels = responseEntity.getBody().getResult();
                if (CollectionUtils.isEmpty(xlAidDidModels)){
                    LOGGER.error("登录获取企业基本信息....根据模拟量id查找上级所属设备 xlAidDidModels 错误");
                    return;
                }
                //先塞uid
                xlAidDidModels.forEach(xlAidDidModel -> {
                    xlAidDidModel.setUid(Integer.parseInt(object.getString("uid")));
                });
                if (CollectionUtils.isEmpty(aidDidModelList)){
                    xlAidDidModelMapper.bantchInsert(xlAidDidModels);
                }else {
                    //先删后插，省得麻烦
                    int ret = xlAidDidModelMapper.bantchDelete(Integer.parseInt(object.getString("uid")));
                    if (ret != 0){
                        xlAidDidModelMapper.bantchInsert(xlAidDidModels);
                    }
                }

            }else {
                LOGGER.error("登录获取企业基本信息....根据模拟量id查找上级所属设备 responseEntity 错误");
                return;
            }

        }else {
            LOGGER.error("登录获取企业基本信息....根据模拟量id查找上级所属设备 entity 错误");
            return;
        }


        String uid = object.getString("uid");
        //获取企业下的能源类型
        List<XlUnitCalcGroupModel> unitCalcGroupModels = xlUnitCalcGroupModelMapper.selectByUid(Integer.valueOf(uid));
        if (!CollectionUtils.isEmpty(unitCalcGroupModels)){
            for (XlUnitCalcGroupModel xlUnitCalcGroupModel:unitCalcGroupModels){
                //获取企业下该能源类型的设备id下的所有模拟量
                HashMap<String,Integer> map = new HashMap<>();
                map.put("uid",Integer.valueOf(uid));
                map.put("dataid",Integer.valueOf(xlUnitCalcGroupModel.getDataid()));
                List<XlAidDidModel> xlAidDidModels =  xlAidDidModelMapper.getByUidDataid(map);
                if (!CollectionUtils.isEmpty(xlAidDidModels)){
                    for (XlAidDidModel aidDidModel:xlAidDidModels){
                        //设置daitaid
                        aidDidModel.setDataid(Integer.valueOf(xlUnitCalcGroupModel.getDataid()));
                    }

                    //更新，批量
                    xlAidDidModelMapper.updateBanthAidDid(xlAidDidModels);
                }
            }


        }



        LOGGER.info("登录获取企业基本信息....根据模拟量id查找上级所属设备 结束");
    }

    public void getGroupAnalog(JSONObject object, String softUrl, String apiUrl) {
        LOGGER.info("登录获取企业基本信息....获取计算组和模拟量关系开始");
        Hashtable<String,String> requestBody = new Hashtable<>();
        String token = object.getString("token");
        requestBody.put("token",token);
        requestBody.put("url",softUrl);

        //先获取的企业树结构,串行直行,uids理论上不可能为空
        List<Integer> uids = xlUnitlinkModelMapper.getUidsByUid(Integer.valueOf(object.getString("uid")));
        List<String> groupIds = xlUnitCalcGroupModelMapper.getGroupIds(uids);
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isEmpty(groupIds)){
            LOGGER.error("登录获取企业基本信息....获取计算组和模拟量关系 发生错误");
            return;
        }
        groupIds.forEach(id->{
            sb.append(id).append(",");
        });
        String groupId = sb.substring(0,sb.lastIndexOf(","));
        requestBody.put("groupids",groupId);
        ResponseEntity<RestResponse<CopyOnWriteArrayList<XlGroupanalogModel>>> entity =
                genericRest.post(apiUrl + "/GetGroupAnalog", requestBody,
                        new ParameterizedTypeReference<RestResponse<CopyOnWriteArrayList<XlGroupanalogModel>>>() {});

        if (entity.getStatusCode().equals(HttpStatus.OK)){
            CopyOnWriteArrayList<XlGroupanalogModel> xlGroupanalogModelList = xlGroupanalogModelMapper.selectByGroupIds(groupIds);
            CopyOnWriteArrayList<XlGroupanalogModel> xlGroupanalogModels = entity.getBody().getResult();
            if (CollectionUtils.isEmpty(xlGroupanalogModelList)){
                xlGroupanalogModelMapper.insertBantch(xlGroupanalogModels);
            }else {
                //先删再插，方便快捷
                int ret = xlGroupanalogModelMapper.deleteByGroupIds(groupIds);
                if (ret != 0){
                    //批量插入
                    xlGroupanalogModelMapper.insertBantch(xlGroupanalogModels);
                }
            }

        }


        //更新xl_unitlink表的dataid
        String uid = object.getString("uid");
        //获取企业下的能源类型
        List<XlUnitCalcGroupModel> unitCalcGroupModels = xlUnitCalcGroupModelMapper.selectByUid(Integer.valueOf(uid));
        //xl_unitlink表的dataid
        for (XlUnitCalcGroupModel xlUnitCalcGroupModel:unitCalcGroupModels){
            List<XlUnitCalcGroupModel> calcGroupModels = xlUnitCalcGroupModelMapper.selectUnitByUid(Integer.valueOf(uid), xlUnitCalcGroupModel.getDataid(), "1");
            List<XlUnitlinkModel> xlUnitlinkModels = xlUnitlinkModelMapper.getByUids(uid);
            if (CollectionUtils.isEmpty(calcGroupModels) || CollectionUtils.isEmpty(xlUnitlinkModels)) {
                LOGGER.error("登录获取企业基本信息 ......calcGroupModels or xlUnitlinkModels error");
            }

            for (XlUnitlinkModel xlUnitlinkModel : xlUnitlinkModels) {
                if (xlUnitlinkModel.getUid().equals(Integer.valueOf(object.getString("uid")))) {
                    continue;
                }
                for (XlUnitCalcGroupModel calcGroupModel : calcGroupModels) {
                    if (calcGroupModel.getUid().equals(xlUnitlinkModel.getUid())) {
                        xlUnitlinkModel.setDataid(Integer.valueOf(calcGroupModel.getDataid()));
                    }
                }
            }
            int ret = xlUnitlinkModelMapper.banthUpdate(xlUnitlinkModels);
            if (ret == 0) {
                LOGGER.error("登录获取企业基本信息 ......banthUpdate error");
            }
        }


        //更新xl_groupanalog，根据企业的能源类型设置i1,i2字段
        for (XlUnitCalcGroupModel unitCalcGroupModel:unitCalcGroupModels){
            HashMap<String,Integer> map = new HashMap<>();
            map.put("uid",Integer.valueOf(uid));
            map.put("dataid",Integer.valueOf(unitCalcGroupModel.getDataid()));
            List<XlGroupanalogModel> groupanalogModels = xlGroupanalogModelMapper.getGroupAnalogbyUidDataid(map);
            if (!CollectionUtils.isEmpty(groupanalogModels)){
                for (XlGroupanalogModel groupanalogModel:groupanalogModels){
                    groupanalogModel.setUid(Integer.valueOf(uid));
                    groupanalogModel.setDataid(Integer.valueOf(unitCalcGroupModel.getDataid()));
                }

                xlGroupanalogModelMapper.banthUpdateByUidDataid(groupanalogModels);
            }
        }

        LOGGER.info("登录获取企业基本信息....获取计算组和模拟量关系结束");
    }

    public void getUnitCalcGroup(JSONObject object, String softUrl, String apiUrl) {
        LOGGER.info("登录获取企业基本信息....获取企业单元计算组档案开始");
        Hashtable<String,String> requestBody = new Hashtable<>();

        //先获取的企业树结构,串行直行,uids理论上不可能为空
        StringBuilder sb = new StringBuilder();
        String uid = getUids(object, sb);
        requestBody.put("uids",uid);
        requestBody.put("token",object.getString("token"));
        requestBody.put("url",softUrl);

        ResponseEntity<RestResponse<CopyOnWriteArrayList<XlUnitCalcGroupModel>>> entity = genericRest.post(apiUrl + "/GetUnitCalcGroup", requestBody,
                new ParameterizedTypeReference<RestResponse<CopyOnWriteArrayList<XlUnitCalcGroupModel>>>() {
                });

        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            CopyOnWriteArrayList<XlUnitCalcGroupModel> xlUnitCalcGroupModelList = xlUnitCalcGroupModelMapper.getAll();
            CopyOnWriteArrayList<XlUnitCalcGroupModel> xlUnitCalcGroupModels = entity.getBody().getResult();
            if (CollectionUtils.isEmpty(xlUnitCalcGroupModels)) {
                return;
            }

            CopyOnWriteArrayList<XlUnitCalcGroupModel> calcGroupModels = new CopyOnWriteArrayList<>();
            if (CollectionUtils.isEmpty(xlUnitCalcGroupModelList)){
                xlUnitCalcGroupModels.forEach(xlUnitCalcGroupModel -> {
                    xlUnitCalcGroupModelMapper.insertSelective(xlUnitCalcGroupModel);
                });
            }else {
                xlUnitCalcGroupModels.forEach(xlUnitCalcGroupModel -> {
                    xlUnitCalcGroupModelList.forEach(xlUnitCalcGroupModel1 -> {
                        if (xlUnitCalcGroupModel1.getGroupid().equals(xlUnitCalcGroupModel.getGroupid())){
                            calcGroupModels.add(xlUnitCalcGroupModel);
                            xlUnitCalcGroupModel.setId(xlUnitCalcGroupModel1.getId());
                            xlUnitCalcGroupModelMapper.updateByPrimaryKeySelective(xlUnitCalcGroupModel);
                        }
                    });
                });
                //查看是否有新增的单元计算组
                xlUnitCalcGroupModels.removeAll(calcGroupModels);
                if (!CollectionUtils.isEmpty(xlUnitCalcGroupModels)){
                    xlUnitCalcGroupModels.forEach(xlUnitCalcGroupModel -> {
                        xlUnitCalcGroupModelMapper.insertSelective(xlUnitCalcGroupModel);
                    });
                }
            }
        }
        LOGGER.info("登录获取企业基本信息....获取企业单元计算组档案结束");
    }

    private String getUids(JSONObject object, StringBuilder sb) {
        List<Integer> uids = xlUnitlinkModelMapper.getUidsByUid(Integer.valueOf(object.getString("uid")));
        uids.forEach(u->{
            sb.append(u).append(",");
        });
        return sb.substring(0,sb.lastIndexOf(","));
    }

    public void getUnitLink(JSONObject object, String softUrl, String apiUrl) {
        LOGGER.info("登录获取企业基本信息....获取企业树结构开始");
        List<String> dataIds = xlDatacodeModelMapper.getAllDataCode2();
        StringBuilder sb = new StringBuilder();
        if (!CollectionUtils.isEmpty(dataIds)) {
            dataIds.forEach(dataid -> {
                sb.append(dataid).append(",");
            });

            String dataids = sb.substring(0, sb.lastIndexOf(","));
            String uid = object.getString("uid");
            Hashtable<String, String> requestBody = new Hashtable<>();
            requestBody.put("dataids", dataids);
            requestBody.put("uid", uid);
            requestBody.put("token", object.getString("token"));
            requestBody.put("url", softUrl);
            ResponseEntity<RestResponse<String>> entity = genericRest.post(apiUrl + "/GetUnitLinkByDataType",
                    requestBody, new ParameterizedTypeReference<RestResponse<String>>() {});
            if (entity.getStatusCode().equals(HttpStatus.OK)) {
                String res = entity.getBody().getResult();
                if (Strings.isNullOrEmpty(res)){
                    return;
                }
                JSONObject obj = JSONObject.parseObject(res);
                JSONArray body = obj.getJSONArray("body");
                if (body != null) {
                    JSONObject object1 = (JSONObject) body.get(0);
                    final XlUnitlinkModel model = object1.toJavaObject(XlUnitlinkModel.class);
                    List<Integer> uids = xlUnitlinkModelMapper.getUidsByUid(Integer.valueOf(object.getString("uid")));
                    if (CollectionUtils.isEmpty(uids)){
                        xlUnitlinkModelMapper.insertSelective(model);
                        insertUninLink(model);
                    }else {
                        //批量删除
                        int ret = xlUnitlinkModelMapper.deleteByList(uids);
                        if (ret != 0){
                            //删除成功，再插入，方便快捷
                            xlUnitlinkModelMapper.insertSelective(model);
                            insertUninLink(model);
                        }
                    }
                }
            }
        }else {
            LOGGER.error("登录获取企业基本信息....获取DataCode错误");
            return;
        }

        LOGGER.info("登录获取企业基本信息....获取企业树结构结束");
    }


    private void insertUninLink(XlUnitlinkModel model) {
        List<XlUnitlinkModel> modelCu = model.getCu();
        if (!CollectionUtils.isEmpty(modelCu)){
            modelCu.forEach(unitlinkModel -> {
                xlUnitlinkModelMapper.insertSelective(unitlinkModel);
                if (!CollectionUtils.isEmpty(unitlinkModel.getCu())){
                    insertUninLink(unitlinkModel);
                }
            });
        }
    }

    private void setUserInfo(String name, String pass, JSONObject object) {
        LOGGER.info("登录获取企业基本信息....setUserInfo开始");
        UserInfoModel record = new UserInfoModel();
        record.setName(name);
        record.setPass(pass);
        UserInfoModel userInfoModel = userInfoModelMapper.getUserInfoByNP(record);
        if (userInfoModel == null) {
            //新增
            record.setToken(object.getString("token"));
            record.setUid(object.getString("uid"));
            record.setUserid(object.getString("userid"));
            userInfoModelMapper.insertSelective(record);
        } else {
            //更新token，userid
            userInfoModel.setToken(object.getString("token"));
            userInfoModel.setUserid(object.getString("userid"));
            userInfoModelMapper.updateByPrimaryKey(userInfoModel);
        }

        LOGGER.info("登录获取企业基本信息....setUserInfo结束");
    }

}
