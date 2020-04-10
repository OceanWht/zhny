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

    private void syncCompanyData() {
        new Thread(){
            @Override
            public void run() {
                syncCompanyDataService.GetPlatFormCompany();
            }
        }.start();
    }
}
