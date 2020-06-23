package com.xl.ems.ynnhjc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置类  @EnableAsync开启线程池
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    //核心线程数
    private static final int corePoolSize = Runtime.getRuntime().availableProcessors();

    //最大线程数
    private static final int maxPoolSize = Runtime.getRuntime().availableProcessors();

    //队列大小
    private static final int queueCapacity = 10;

    //空闲时间 秒
    private static final int keepAlive = 60;

    @Bean(name = "defaultThreadPool")
    public ThreadPoolTaskExecutor defaultThreadPool(){

        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAlive);
        //配置线程池中的线程的名称前缀
        threadPoolTaskExecutor.setThreadNamePrefix("ThreadPool--");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行

        return threadPoolTaskExecutor;
    }
}
