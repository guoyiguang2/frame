package com.gupaoedu.service.controller;


import com.gupaoedu.service.annotation.Validate;
import com.gupaoedu.service.entity.TestForm;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    
    /*
    * 参数校验AOP
    * */
    @GetMapping(value = "/transParams/{message}")
    public String echo(@PathVariable String message) {

        return "[ECHO:" + "] " + message;
    }

    @RequestMapping("/test")
    @ResponseBody
    @Validate
    public Object test(Integer age,@Validate(isForm=true) TestForm form){
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBB");
        return age +"：您好！";
    }

}
