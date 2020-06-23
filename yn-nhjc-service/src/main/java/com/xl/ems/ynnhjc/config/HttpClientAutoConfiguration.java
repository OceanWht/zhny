package com.xl.ems.ynnhjc.config;


import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(HttpClientProperties.class)
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientAutoConfiguration {

    private final HttpClientProperties httpClientProperties;

    public HttpClientAutoConfiguration(HttpClientProperties httpClientProperties) {
        this.httpClientProperties = httpClientProperties;
    }


    @Bean
    @ConditionalOnMissingBean(HttpClient.class)
    public HttpClient httpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(httpClientProperties.getConnectTimeOut())
                .setSocketTimeout(httpClientProperties.getSocketTimeOut())
                .build();


        HttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setUserAgent(httpClientProperties.getAgent())
                .setMaxConnPerRoute(httpClientProperties.getMaxConnPerRoute())
                .setMaxConnTotal(httpClientProperties.getMaxConnTotal())
                .build();


        return client;

    }
}
