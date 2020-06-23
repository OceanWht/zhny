package com.xl.ems.apigateway.sm4;

/**
 * Created by
 *
 * @Auther:liuzhengliang
 * @Date: 2018/11/12
 */
public class SMS4Context{
	
    public int mode;  
      
    public long[] sk;  
      
    public boolean isPadding;  
  
    public SMS4Context()   
    {  
        this.mode = 1;  
        this.isPadding = true;  
        this.sk = new long[32];  
    }  
} 
