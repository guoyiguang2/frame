package com.gupaoedu.service.exception;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {


    /**
     * 返回文本，前端浏览器不需要下载 也能查看内容
     */
    public static void responseContent(HttpServletResponse response,String code,String msg){

        response.reset();
        response.setCharacterEncoding("UTF-8");
        //声明返回对象是文本
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

    /**
     * 返回json串，前端浏览器需要下载 才能查看内容，postMan不用
     */
    public static void responseContent(ServletResponse response, String code, String msg){
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        //声明返回对象是JSON串
        httpResponse.setContentType("application/json; charset=utf-8");
        Map<String,String> result = new HashMap<String,String>();
        result.put(code,msg);
        String json = JSONObject.toJSONString(result);
        try {
            httpResponse.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
