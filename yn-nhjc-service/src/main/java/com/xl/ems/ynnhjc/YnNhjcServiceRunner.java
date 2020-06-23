package com.xl.ems.ynnhjc;

import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.service.YNEnterpriseServcie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class YnNhjcServiceRunner implements ApplicationRunner {

    @Autowired
    YNEnterpriseServcie ynEnterpriseServcie;

    private static final Logger LOGGER = LoggerFactory.getLogger(YnNhjcServiceRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {

        applyAndDownladBasicData();
    }

    private void applyAndDownladBasicData() {

        new Thread() {
            @Override
            public void run() {
                LOGGER.info("YnNhjcServiceRunner applyAndDownladBasicData begin......");
                //获取工作密钥
                ynEnterpriseServcie.getRWK();
                //端系统基础数据下载
                ynEnterpriseServcie.dataDownloadPublic();
                //端系统注册申请
                ynEnterpriseServcie.enterpriseInfoApply();

                //获取AK token
                try {
                    ynEnterpriseServcie.requestAK();
                }catch (Exception e){

                    LOGGER.error("YnNhjcServiceRunner requestAK has error......"+e.getMessage());
                }
                //平台版本校验,有新版本才下载基础数据
                if ( ynEnterpriseServcie.checkVersion()){
                    //平台基础配置地址查询
                    ynEnterpriseServcie.platformAccessURL();

                    //下载基础数据
                    ynEnterpriseServcie.dataDownload();
                }else {
                    LOGGER.info("The PlatForm Version have not change......");
                }


                LOGGER.info("YnNhjcServiceRunner applyAndDownladBasicData success......");
            }
        }.start();
    }
}
