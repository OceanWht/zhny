package com.xl.ems.ynnhjc.scheduleTasks;

import com.xl.ems.ynnhjc.service.task.YNEnterpriseEnergyTaskService;
import com.xl.ems.ynnhjc.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * @ClassName YNEnterpriseEnergyTask
 * @Description 上报任务
 * @Author wht
 * @Date 9:44
 **/
@Configuration
@EnableScheduling
public class YNEnterpriseEnergyTask {


    @Autowired
    YNEnterpriseEnergyTaskService ynEnterpriseEnergyTaskService;

    /**
     * 手工填报
     */
   // @Scheduled(fixedDelay = 1000*30)  //模拟10秒一次，后面再改
   // @Scheduled(cron = "0 10 16 * * ?")
    //测试
    @Scheduled(cron = "0 0,15,30,45 0-23 * * ?")
    @Async
    public void enterPrinseEnergyTaskManual(){
        ynEnterpriseEnergyTaskService.enterPrinseEnergyTaskManual();
    }


    /**
     * 实时数据上报  暂不支持
     */
   // @Scheduled(cron = "0 0,15,30,45 0-23 * * ?")
    // @Scheduled(fixedDelay = 1000*20)  //模拟10秒一次，后面再改
    @Async
    public void enterPrinseEnergyTaskAutomatic_15(){
        //yyyy-MM-dd HH:mm
        String date = DateUtils.formatDateTime5(new Date());
        ynEnterpriseEnergyTaskService.enterPrinseEnergyTaskAutomatic_15(date);
    }


    /**
     * 自动采集  日数据 每天16:10触发
     */
   // @Scheduled(fixedDelay = 1000*30)  //模拟10秒一次，后面再改
    @Scheduled(cron = "0 10 16 * * ?")
    @Async
    public void enterPrinseEnergyTaskAutomatic_day(){
        ynEnterpriseEnergyTaskService.enterPrinseEnergyTaskAutomatic_day();
    }

    /**
     *每天下午16点到2:55期间和下午6点到6:55期间的每5分钟触发  重传之前上传失败得数据
     */
    @Scheduled(cron = "0 0/10 17-18 * * ?")
    @Async
    public void enterPrinseEnergyTaskAutomatic_day_backup(){
        ynEnterpriseEnergyTaskService.enterPrinseEnergyTaskAutomatic_day_backup();
    }


    /**
     * 自动采集 月数据  每月最后一天下午16:10触发
     */
    // @Scheduled(fixedDelay = 1000*30)  //模拟10秒一次，后面再改
    @Scheduled(cron = "0 10 16 28-31 * ?")
    @Async
    public void enterPrinseEnergyTaskAutomatic_month(){
        ynEnterpriseEnergyTaskService.enterPrinseEnergyTaskAutomatic_month();
    }

    /**
     * 月数据 漏报机制  最后一天得 17点到18点 每10分钟触发一次，上报之前上传失败得
     */
    @Scheduled(cron = "0 0/10 17-18 28-31 * ?")
    @Async
    public void enterPrinseEnergyTaskAutomatic_month_backup(){
        ynEnterpriseEnergyTaskService.enterPrinseEnergyTaskAutomatic_month_backup();
    }

    /**
     * 自动采集 年数据  每年得12月30日下午16：10 触发
     */
    //@Scheduled(fixedDelay = 1000*30)  //模拟10秒一次，后面再改
    @Scheduled(cron = "0 10 16 30 12 ?")
    @Async
    public void enterPrinseEnergyTaskAutomatic_year(){
        ynEnterpriseEnergyTaskService.enterPrinseEnergyTaskAutomatic_year();
    }

    /**
     * 自动采集 年数据  每年得12月30日下午17点到18点 每10分钟触发一次，上报之前上传失败得
     */
    //@Scheduled(fixedDelay = 1000*30)  //模拟10秒一次，后面再改
    @Scheduled(cron = "0 0/10 17-18 30 12 ?")
    @Async
    public void enterPrinseEnergyTaskAutomatic_year_backup(){
        ynEnterpriseEnergyTaskService.enterPrinseEnergyTaskAutomatic_year_backup();
    }
}
