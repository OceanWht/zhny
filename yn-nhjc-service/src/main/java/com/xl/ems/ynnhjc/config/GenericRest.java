package com.xl.ems.ynnhjc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 封装一下负载均衡RestTemplate和直连RestTempalte
 */

@Service
public class GenericRest {


    @Autowired
    private RestTemplate lbRestTemplate;

    @Autowired
    private RestTemplate directRestTemplate;

    //定义一个连接规则，以direct://开头的都是直连
    private static final String directFlag = "direct://";

    /**
     * ParameterizedTypeReference  支持反序列化的参数  包装泛型类的类
     *
     * @param url          请求链接
     * @param reqBody      请求参数
     * @param responseType 返回的数据类型
     * @param <T>
     * @return post方法
     */
    public <T> ResponseEntity<T> post(String url, Object reqBody, ParameterizedTypeReference<T> responseType) {
        RestTemplate template = getRestTemplate(url);
        url = url.replace(directFlag, "");
        //exchange既支持get,也支持post,HttpMethod指定
        return template.exchange(url, HttpMethod.POST, new HttpEntity(reqBody), responseType);
    }

    private RestTemplate getRestTemplate(String url) {
        if (url.contains(directFlag)) {
            return directRestTemplate;
        } else {
            return lbRestTemplate;
        }
    }

    /**
     * get方法没有body,用empty替换
     *
     * @param url
     * @param responseType
     * @param <T>
     * @return get方法
     */
    public <T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> responseType) {

        RestTemplate template = getRestTemplate(url);

        url = url.replace(directFlag, "");
        //exchange既支持get,也支持post,HttpMethod指定 get方法没有body,用empty替换
        return template.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, responseType);
    }
}
