package com.xl.ems.userservice.service;

import com.google.common.base.Strings;
import com.xl.ems.userservice.common.RestResponse;
import com.xl.ems.userservice.config.GenericRest;
import com.xl.ems.userservice.mapper.EmsCompanyModelMapper;
import com.xl.ems.userservice.model.EmsCompanyModel;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SyncCompanyDataService {

    @Autowired
    EmsCompanyModelMapper emsCompanyModelMapper;

    @Autowired
    GenericRest genericRest;

    @Autowired
    GetUrlUtils getUrlUtils;

    @Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.8008_surface}")
    private String softUrlType;


    private static final Logger LOGGER = LoggerFactory.getLogger(SyncCompanyDataService.class);

    public void GetPlatFormCompany() {

        LOGGER.info("获取企业档案....开始");
        //获取企业档案（根据业务类型）
        Map<String, String> requestBody = new HashMap<String, String>();
        requestBody.put("pid", "7");

        //调用接口，修改密码
        //api接口服务
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        if (Strings.isNullOrEmpty(apiUrl)) {
            return;
        }
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(softUrl)) {
            return;
        }

        requestBody.put("url", softUrl);

        ResponseEntity<RestResponse<List<EmsCompanyModel>>> entity = genericRest.post(apiUrl + "/GetPlatFormCompany", requestBody,
                new ParameterizedTypeReference<RestResponse<List<EmsCompanyModel>>>() {
                });

        if (!entity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.error("获取企业档案....请求接口发生错误");
            return;
        }
        final List<EmsCompanyModel> emsCompanyModels = entity.getBody().getResult();
        if (CollectionUtils.isEmpty(emsCompanyModels)) {
            LOGGER.error("获取企业档案....发生错误");
            return;
        }
        //遍历插入
        new Thread() {
            @Override
            public void run() {
                emsCompanyModels.forEach(emsCompanyModel -> {
                    //判断是更新还是插入
                    //emsCompanyModelMapper.
                    emsCompanyModelMapper.insertSelective(emsCompanyModel);
                });
            }
        }.start();

        LOGGER.info("获取企业档案....结束");
    }
}
