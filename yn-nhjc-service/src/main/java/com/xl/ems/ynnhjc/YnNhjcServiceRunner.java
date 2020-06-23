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
                //��ȡ������Կ
                ynEnterpriseServcie.getRWK();
                //��ϵͳ������������
                ynEnterpriseServcie.dataDownloadPublic();
                //��ϵͳע������
                ynEnterpriseServcie.enterpriseInfoApply();

                //��ȡAK token
                try {
                    ynEnterpriseServcie.requestAK();
                }catch (Exception e){

                    LOGGER.error("YnNhjcServiceRunner requestAK has error......"+e.getMessage());
                }
                //ƽ̨�汾У��,���°汾�����ػ�������
                if ( ynEnterpriseServcie.checkVersion()){
                    //ƽ̨�������õ�ַ��ѯ
                    ynEnterpriseServcie.platformAccessURL();

                    //���ػ�������
                    ynEnterpriseServcie.dataDownload();
                }else {
                    LOGGER.info("The PlatForm Version have not change......");
                }


                LOGGER.info("YnNhjcServiceRunner applyAndDownladBasicData success......");
            }
        }.start();
    }
}
