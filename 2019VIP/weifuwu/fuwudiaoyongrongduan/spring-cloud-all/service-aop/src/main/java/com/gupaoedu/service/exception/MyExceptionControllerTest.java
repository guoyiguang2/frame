package com.gupaoedu.service.exception;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyExceptionControllerTest {


    @RequestMapping("/testException")
    public String testException() throws Exception{
        throw new BusinessException("name","String");
    }

    @RequestMapping("/testMyException")
    public String testMyException() throws Exception{
        throw new Exception("i am a myException");
    }

    @RequestMapping("/nologinException")
    public String nologinException() throws Exception{
        throw new BusinessException("401","用户尚未登录");
    }

    @RequestMapping("/noAuthorizations")
    public String noAuthorizations() throws Exception{
        throw new BusinessException("401","用户未授权");
    }
}
