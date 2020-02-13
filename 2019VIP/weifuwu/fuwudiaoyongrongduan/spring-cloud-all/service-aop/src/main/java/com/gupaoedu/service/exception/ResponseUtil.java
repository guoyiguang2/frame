package com.gupaoedu.service.exception;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {


    /*
    * 将内容输出到浏览器(这样不用用户下载错误信息)
    * */
    public static void responseContent(HttpServletResponse response,String code,String msg){

        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain;charset=UTF-8");
        response.setHeader("icop-content-type", "exception");
        Map map = new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
        String result =  JSONObject.toJSONString(map);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.print(result);
        writer.flush();
        writer.close();
    }


}
