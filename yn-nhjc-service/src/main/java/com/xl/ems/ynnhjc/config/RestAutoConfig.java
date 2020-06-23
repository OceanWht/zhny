package com.xl.ems.ynnhjc.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.http.client.HttpClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;

@Configuration
public class RestAutoConfig {


    /**
     * 支持负载均衡的的RestTemplate组件
     */
    public static class RestTemplateConfig{

        /**
         * 对于bean方法里得参数，spring会自动在容器中找符合标准得实例
         * @param httpClient
         * @return
         */
        @Bean(value = "lbRestTemplate")
        //LoadBalanced 在使用springcloud ribbon客户端负载均衡的时候，
        // 可以给RestTemplate bean 加一个@LoadBalanced注解，就能让这个RestTemplate在请求时拥有客户端负载均衡的能力：
        //spring对restTemplate bean 进行定制，加入拦截器进行IP:PORT的替换，就是换成服务注册中的服务名
        @LoadBalanced
        RestTemplate lbRestTemplate(HttpClient httpClient){
            //让RestTemplate底层实现HttpClient
            RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
            //RestTemplate是一个通信组件，在发送请求和接收请求的时候需要将对象序列化成json字符串或字节数组
            //如果发送的是中文，是需要进行编码的，这时spring默认会将编码变成IOS8859，但是我们需要UTF-8的，所以需要设置一下
            restTemplate.getMessageConverters().add(0,new StringHttpMessageConverter(Charset.forName("UTF-8")));
            //另外还需要将发送的json请求反序列化
            restTemplate.getMessageConverters().add(1,new FastJsonHttpMessageConverter5());

            return restTemplate;
        }

        /**
         * 支持直连的RestTemplate
         * @param httpClient
         * @return
         */
        @Bean(value = "directRestTemplate")
        RestTemplate directRestTemplate(HttpClient httpClient){
            //让RestTemplate底层实现HttpClient
            RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
            restTemplate.getMessageConverters().add(1,new FastJsonHttpMessageConverter5());
            restTemplate.getMessageConverters().add(0,new StringHttpMessageConverter(Charset.forName("UTF-8")));
            return restTemplate;
        }


        /**
         * 因为FastJsonHttpMessageConverter 有个bug，默认支持MediaType.ALL，spring默认使用字节流而不是json,所以需要修改覆盖一下
         */
        public static class FastJsonHttpMessageConverter5 extends FastJsonHttpMessageConverter {

            private static final Charset DEFAULT_CHARSET=Charset.forName("UTF-8");

            public FastJsonHttpMessageConverter5(){
                setDefaultCharset(DEFAULT_CHARSET);
                setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,new MediaType("application","*+json")));
            }
        }
    }
}
