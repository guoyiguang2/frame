package com.gupaoedu.service.utils;


import javax.servlet.http.HttpServletRequest;

public class HttpUtils {


    public static String getLastAccessUrl(HttpServletRequest request,String token) {
        String requestURL = request.getRequestURI();
        String queryString = request.getQueryString();
        if (queryString == null) {
            return requestURL.toString();
        }
        return requestURL + "?" + queryString+"&token="+token;
    }
}
