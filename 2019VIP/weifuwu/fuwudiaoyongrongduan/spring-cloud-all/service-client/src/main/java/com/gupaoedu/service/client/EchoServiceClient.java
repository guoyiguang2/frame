package com.gupaoedu.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


//如果被调用方挂掉了 （前端页面报错）
// There was an unexpected error (type=Internal Server Error, status=500).
//connect timed out executing GET http://service-provider/echo/aaa
@FeignClient( name = "service-provider")
public interface EchoServiceClient {

    @GetMapping(value = "/echo/{message}")
    String echo(@PathVariable(value="message") String message);
}
