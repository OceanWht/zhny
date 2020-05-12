package com.xl.ems.userservice.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.userservice.bean.XlAccountBean;
import com.xl.ems.userservice.common.RestResponse;
import com.xl.ems.userservice.config.GenericRest;
import com.xl.ems.userservice.mapper.EmsCompanyModelMapper;
import com.xl.ems.userservice.mapper.UserInfoModelMapper;
import com.xl.ems.userservice.mapper.XlDatacodeModelMapper;
import com.xl.ems.userservice.mapper.XlUnitCalcGroupModelMapper;
import com.xl.ems.userservice.model.EmsCompanyModel;
import com.xl.ems.userservice.model.UserInfoModel;
import com.xl.ems.userservice.model.XlDatacodeModel;
import com.xl.ems.userservice.model.XlUnitCalcGroupModel;
import com.xl.ems.userservice.utils.GetUrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SyncCompanyDataService {


    @Value("${user.login_name}")
    private String loginName;

    @Value("${user.login_pass}")
    private String loginPass;

    @Autowired
    EmsCompanyModelMapper emsCompanyModelMapper;

    @Autowired
    UserInfoModelMapper userInfoModelMapper;

    @Autowired
    XlDatacodeModelMapper xlDatacodeModelMapper;

    @Autowired
    XlUnitCalcGroupModelMapper xlUnitCalcGroupModelMapper;

    @Autowired
    LoginService loginService;

    @Autowired
    GenericRest genericRest;

    @Autowired
    GetUrlUtils getUrlUtils;

    @Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.8008_surface}")
    private String softUrlType;


    private static final Logger LOGGER = LoggerFactory.getLogger(SyncCompanyDataService.class);

    public void getPlatFormCompany() {

        LOGGER.info("获取企业档案....开始");
        //获取企业档案（根据业务类型）
        Map<String, String> requestBody = new HashMap<String, String>();
        requestBody.put("pid", "7");

        //调用接口，修改密码
        //api接口服务
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        if (Strings.isNullOrEmpty(apiUrl)) {
            LOGGER.error("获取企业档案....获取api接口地址发生错误");
            return;
        }
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("获取企业档案....获取8008接口地址发生错误");
            return;
        }

        requestBody.put("url", softUrl);

        ResponseEntity<RestResponse<CopyOnWriteArrayList<EmsCompanyModel>>> entity = genericRest.post(apiUrl + "/GetPlatFormCompany", requestBody,
                new ParameterizedTypeReference<RestResponse<CopyOnWriteArrayList<EmsCompanyModel>>>() {
                });

        if (!entity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.error("获取企业档案....请求接口发生错误");
            return;
        }
        final CopyOnWriteArrayList<EmsCompanyModel> emsCompanyModels = entity.getBody().getResult();
        if (CollectionUtils.isEmpty(emsCompanyModels)) {
            LOGGER.error("获取企业档案....发生错误");
            return;
        }

        final CopyOnWriteArrayList<EmsCompanyModel> emsCompanyModelList = emsCompanyModelMapper.selectAll();
        //项目第一次启动
        if (CollectionUtils.isEmpty(emsCompanyModelList)) {
            emsCompanyModels.forEach(emsCompanyModel -> {
                emsCompanyModelMapper.insertSelective(emsCompanyModel);
            });
        } else {
            //相同uid的更新名字
            emsCompanyModels.forEach(ems -> {
                emsCompanyModelList.stream().filter(ems1 -> ems1.equals(ems)).forEach(ems1 -> {
                    ems1.setName(ems.getName());
                    ems1.setRemark("更新时间" + new Date());
                    emsCompanyModelMapper.updateByPrimaryKeySelective(ems1);

                    //相同企业更新，并移除，emsCompanyModels可能会比数据库中的多
                    //ConcurrentModificationException异常：
                    //当方法检测到对象的并发修改，但不允许这种修改时，抛出此异常。
                    //如果要避免这种异常，单线程情况:要么用Iterator,要么使用并发集合类来避免ConcurrentModificationException，
                    // 比如使用CopyOnArrayList，而不是ArrayList。
                    // 多线程情况：使用并发集合类，如使用ConcurrentHashMap或者CopyOnWriteArrayList。
                    emsCompanyModels.remove(ems);
                });
            });
            //如果还有，就插入
            if (!CollectionUtils.isEmpty(emsCompanyModels)) {
                emsCompanyModels.forEach(emsCompanyModel -> {
                    emsCompanyModelMapper.insertSelective(emsCompanyModel);
                });
            }
        }


        LOGGER.info("获取企业档案....结束");
    }

    /**
     * 更新企业账户信息，直接更新userinfo表，用户名和密码，不更新token
     */
    public void getAccountFeePublic() {
        LOGGER.info("获取企业公开账户....开始");
        //获取数据中所有的企业
        final CopyOnWriteArrayList<EmsCompanyModel> emsCompanyModelList = emsCompanyModelMapper.selectAll();
        //不重复的集合
        final Set<String> uidSet = new HashSet<>();
        //线程安全的
        StringBuilder sb = new StringBuilder();
        emsCompanyModelList.forEach(emsCompanyModel -> {
            uidSet.add(emsCompanyModel.getUid());
        });
        if (!CollectionUtils.isEmpty(uidSet)) {
            for (String uid : uidSet) {
                sb.append(uid).append(",");
            }
        }
        //获取uid集合字符串
        String uids = sb.substring(0, sb.toString().lastIndexOf(","));
        if (Strings.isNullOrEmpty(uids)) {
            LOGGER.error("获取企业公开账户....获取企业ID集合发生错误");
            return;
        }

        //获取企业档案（根据业务类型）
        Map<String, String> requestBody = new HashMap<String, String>();
        requestBody.put("pid", "7");

        //api接口服务
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        if (Strings.isNullOrEmpty(apiUrl)) {
            LOGGER.error("获取企业公开账户....获取api接口地址发生错误");
            return;
        }
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("获取企业公开账户....获取8008接口地址发生错误");
            return;
        }

        requestBody.put("url", softUrl);
        requestBody.put("uids", uids);

        ResponseEntity<RestResponse<CopyOnWriteArrayList<XlAccountBean>>> entity = genericRest.post(apiUrl + "/GetAccountFeePublic", requestBody,
                new ParameterizedTypeReference<RestResponse<CopyOnWriteArrayList<XlAccountBean>>>() {
                });

        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            //获取所有8002后台综合能源的公开账户
            final CopyOnWriteArrayList<XlAccountBean> xlAccountBeans = entity.getBody().getResult();
            //获取数据库中的企业账户userinfo
            final CopyOnWriteArrayList<UserInfoModel> userInfoModels = userInfoModelMapper.getAllUserInfo();
            if (userInfoModels.size() == 0) {
                xlAccountBeans.forEach(xlAccountBean -> {
                    //如果没有，就插入，没有userid和token
                    UserInfoModel record = new UserInfoModel();
                    record.setName(xlAccountBean.getName());
                    record.setPass(xlAccountBean.getPwd());
                    record.setUid(xlAccountBean.getUid());
                    userInfoModelMapper.insertSelective(record);
                });
            } else {
                //找出相同的，更新一下密码
                CopyOnWriteArrayList<XlAccountBean> xlAccountBeans1 = new CopyOnWriteArrayList<>();
                xlAccountBeans.forEach(xlAccountBean -> {
                    userInfoModels.forEach(userInfoModel -> {
                        if (userInfoModel.getName().equals(xlAccountBean.getName())
                                && userInfoModel.getUid().equals(xlAccountBean.getUid())) {
                            //在下面去重，判断是否有新的账户密码
                            xlAccountBeans1.add(xlAccountBean);
                            //更新密码
                            userInfoModel.setPass(xlAccountBean.getPwd());
                            userInfoModelMapper.updateByPrimaryKeySelective(userInfoModel);
                        }
                    });
                });

                xlAccountBeans.removeAll(xlAccountBeans1);
                if (xlAccountBeans.size() != 0) {
                    xlAccountBeans.forEach(xlAccountBean -> {
                        //如果有，就插入，没有userid和token
                        UserInfoModel record = new UserInfoModel();
                        record.setName(xlAccountBean.getName());
                        record.setPass(xlAccountBean.getPwd());
                        record.setUid(xlAccountBean.getUid());
                        userInfoModelMapper.insertSelective(record);
                    });
                }
            }


        } else {
            LOGGER.error("获取企业公开账户....获取账户发生错误");
            return;
        }
        LOGGER.info("获取企业公开账户....结束");
    }

    /**
     * 获取8002后台所有的能源类型
     */
    public void getAllDataCode() {
        LOGGER.info("获取能源类型....开始");

        //api接口服务
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        if (Strings.isNullOrEmpty(apiUrl)) {
            LOGGER.error("获取企业公开账户....获取api接口地址发生错误");
            return;
        }
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("获取企业公开账户....获取8008接口地址发生错误");
            return;
        }
        //获取一个有效token
        UserInfoModel userInfoModel = userInfoModelMapper.getAllUserInfo().get(0);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("url", softUrl);

        if (userInfoModel != null) {
            //不管数据库里的token是否有效，重新登录一次获取有效token,也不用验证token那么麻烦
            String token = getValidateToken();
            requestMap.put("token", token);
            if (Strings.isNullOrEmpty(token)) {
                LOGGER.error("获取企业公开账户....获取 token 发生错误");
                return;
            }
            ResponseEntity<RestResponse<CopyOnWriteArrayList<XlDatacodeModel>>> entity = genericRest.post(apiUrl + "/GetDataCode", requestMap,
                    new ParameterizedTypeReference<RestResponse<CopyOnWriteArrayList<XlDatacodeModel>>>() {
                    });
            LOGGER.info("获取能源类型....entity:" + entity);
            if (entity.getStatusCode().equals(HttpStatus.OK)) {
                final CopyOnWriteArrayList<XlDatacodeModel> xlDatacodeModelList = xlDatacodeModelMapper.getAllDataCode();
                final CopyOnWriteArrayList<XlDatacodeModel> xlDatacodeModels = entity.getBody().getResult();
                if (CollectionUtils.isEmpty(xlDatacodeModelList)) {
                    xlDatacodeModels.forEach(xlDatacodeModel -> {
                        xlDatacodeModelMapper.insertSelective(xlDatacodeModel);
                    });
                } else {
                    CopyOnWriteArrayList<XlDatacodeModel> xlDatacodeModelsNew = new CopyOnWriteArrayList<>();
                    xlDatacodeModels.forEach(xlDatacodeModel -> {
                        xlDatacodeModelList.forEach(xlDatacodeModel1 -> {
                            if (xlDatacodeModel1.getDataid().equals(xlDatacodeModel.getDataid())) {
                                xlDatacodeModelsNew.add(xlDatacodeModel);
                                xlDatacodeModel1.setDataname(xlDatacodeModel.getDataname());
                                xlDatacodeModel1.setPdata(xlDatacodeModel.getPdata());
                                xlDatacodeModel1.setDatacode(xlDatacodeModel.getDatacode());
                                xlDatacodeModelMapper.updateByPrimaryKeySelective(xlDatacodeModel1);
                            }
                        });
                    });

                    //逻辑同公开账户，先去重，在判断是否有新的，有就插入
                    xlDatacodeModels.removeAll(xlDatacodeModelsNew);
                    if (!CollectionUtils.isEmpty(xlDatacodeModels)) {
                        xlDatacodeModels.forEach(xlDatacodeModel -> {
                            xlDatacodeModelMapper.insertSelective(xlDatacodeModel);
                        });
                    }

                }

            }
        }
        LOGGER.info("获取能源类型....结束");
    }

    public void getAllUnitCalcGroup() {
        LOGGER.info("获取企业下的计算组档案....开始");
        //api接口服务
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        if (Strings.isNullOrEmpty(apiUrl)) {
            LOGGER.error("获取企业下的计算组档案....获取api接口地址发生错误");
            return;
        }
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("获取企业下的计算组档案....获取8008接口地址发生错误");
            return;
        }

        Hashtable<String, String> requestMap = new Hashtable<>();
        requestMap.put("url", softUrl);

        //针对每个企业都获取一下
        CopyOnWriteArrayList<EmsCompanyModel> emsCompanyModels = emsCompanyModelMapper.selectAll();
        if (CollectionUtils.isEmpty(emsCompanyModels)) {
            LOGGER.error("获取企业下的计算组档案....获取userInfoModels发生错误");
            return;
        }

        //GetUnitCalcGroup  只要token有效就行，不区分企业token权限
        //不管数据库里的token是否有效，重新登录一次获取有效token,也不用验证token那么麻烦
        String token = getValidateToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("获取企业下的计算组档案....获取 token 发生错误");
            return;
        }

        requestMap.put("token", token);
        StringBuilder sb = new StringBuilder();
        emsCompanyModels.forEach(emsCompanyModel -> {
            sb.append(emsCompanyModel.getUid()).append(",");
        });

        String uids = sb.substring(0, sb.lastIndexOf(","));
        requestMap.put("uids", uids);

        ResponseEntity<RestResponse<CopyOnWriteArrayList<XlUnitCalcGroupModel>>> entity = genericRest.post(apiUrl + "/GetUnitCalcGroup", requestMap,
                new ParameterizedTypeReference<RestResponse<CopyOnWriteArrayList<XlUnitCalcGroupModel>>>() {
                });

        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            CopyOnWriteArrayList<XlUnitCalcGroupModel> xlUnitCalcGroupModelList = xlUnitCalcGroupModelMapper.getAll();
            CopyOnWriteArrayList<XlUnitCalcGroupModel> xlUnitCalcGroupModels = entity.getBody().getResult();
            if (CollectionUtils.isEmpty(xlUnitCalcGroupModels)) {
                LOGGER.error("获取企业下的计算组档案....获取 xlUnitCalcGroupModels 失败");
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

                xlUnitCalcGroupModels.removeAll(calcGroupModels);
                if (!CollectionUtils.isEmpty(xlUnitCalcGroupModels)){
                    xlUnitCalcGroupModels.forEach(xlUnitCalcGroupModel -> {
                        xlUnitCalcGroupModelMapper.insertSelective(xlUnitCalcGroupModel);
                    });
                }
            }


        }
        LOGGER.info("获取企业下的计算组档案....结束");
    }

    private String getValidateToken() {
       // UserInfoModel infoModel = userInfoModelMapper.getAllUserInfo().get(0);
        JSONArray resJsonArray = loginService.login(loginName, loginPass);
        LOGGER.info("获取随机有效token....resJsonArray:" + resJsonArray);
        String token = null;
        if (resJsonArray != null) {
            JSONObject obj = (JSONObject) resJsonArray.get(0);
            token = obj.getString("token");
        }

        return token;
    }
}
