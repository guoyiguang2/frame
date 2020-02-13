package com.gupaoedu.service.utils;


import org.springframework.stereotype.Component;

@Component
public class RedisUtil {

   public boolean setValue(String key,Object obj,Long time){
        return true;
    }

    public Object getValue(String key){
        return new String(key);
    }

}
