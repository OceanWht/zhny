package com.xl.ems.ynnhjc.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class GetYnHeaders {

    public static HttpHeaders getYnHeaders(String prefix,String AK){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON.toString());
        headers.add("Authorization",prefix+" "+AK);
        return headers;
    }

    public static  HttpHeaders getYnHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON.toString());
        return headers;
    }

    public static  HttpHeaders getYnHeaders(String AK){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON.toString());
        headers.add("Authorization",AK);
        return headers;
    }
}
