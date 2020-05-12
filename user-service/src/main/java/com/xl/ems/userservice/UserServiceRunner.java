package com.xl.ems.userservice;

import com.xl.ems.userservice.service.SyncCompanyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class UserServiceRunner implements ApplicationRunner {

    @Autowired
    SyncCompanyDataService syncCompanyDataService;

    public void run(ApplicationArguments args) throws Exception {
        //同步综合能源企业基本档案
        syncCompanyData();
    }

    /**
     * 获取企业信息，同步企业档案，账户信息等
     */
    private void syncCompanyData() {
        new Thread(){
            @Override
            public void run() {
                //企业档案
                syncCompanyDataService.getPlatFormCompany();
                //公开账户
                syncCompanyDataService.getAccountFeePublic();
                //所有的能源类型
                syncCompanyDataService.getAllDataCode();
            }
        }.start();
    }
}
