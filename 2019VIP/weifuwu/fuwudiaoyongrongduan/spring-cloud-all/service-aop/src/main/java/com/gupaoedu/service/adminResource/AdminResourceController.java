package com.gupaoedu.service.adminResource;

import com.gupaoedu.service.annotation.Limited;
import com.gupaoedu.service.annotation.Timeout;
import com.gupaoedu.service.entity.UserVo;
import com.gupaoedu.service.service.UserService;
import com.gupaoedu.service.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminResourceController {

    // 外部化配置其实是有点不靠谱的 - 它并非完全静态，也不一定及时返回

    private final Environment environment;

    public  AdminResourceController(Environment environment) {
        this.environment = environment;
    }

    private String getPort() {
        return environment.getProperty("local.server.port");
    }


    @Autowired
    public UserService userService;

    @Autowired
    public RedisUtil redisUtil;

    @RequestMapping(value = "/admin/loginSubmit")
    public String loginSubmit(@RequestParam String loginName,@RequestParam String password,@RequestParam String token) {
        return "[ADMIN   ECHO:" + getPort() + "]  Hello ," + loginName+"   YOUR PASSWORD IS  "+password+"  TOKEN IS  "+token;
    }

    @RequestMapping(value = "/admin/loginSubmitSuccess")
    public String loginSubmitSuccess(@RequestParam String loginName,@RequestParam String password,@RequestParam String token) {

        UserVo userVo = userService.getUserFromDataBase(loginName);
        redisUtil.setValue(token,userVo,30*60*1000L);
        return "[ADMIN   ECHO:" + getPort() + "]  Hello ," + loginName+"   YOUR PASSWORD IS  "+password+"  TOKEN IS  "+token;
    }

    @RequestMapping(value = "/admin/loginSubmitFailed")
    public String loginSubmitFailed(@RequestParam String loginName,@RequestParam String password) {
        return "[ADMIN   ECHO:" + getPort() + "]  Hello ," + loginName+"   LOGIN  FAILED";
    }

    @RequestMapping(value = "/admin/echo/{message}")
    public String echo(@PathVariable String message) {
        return "[ADMIN   ECHO:" + getPort() + "] " + message;
    }

    @RequestMapping(value = "/admin/add/{message}")
    public String add(@PathVariable String message) {
        return "[ADMIN   ECHO  Add:" + getPort() + "] " + message;
    }

    @RequestMapping(value = "/admin/delete/{message}")
    public String delete(@PathVariable String message) {
        return "[ADMIN   ECHO  Delete:" + getPort() + "] " + message;
    }

    @GetMapping(value = "/admin/update/{message}")
    public String update(@PathVariable String message) {
        return "[ADMIN   ECHO  Update:" + getPort() + "] " + message;
    }


    @RequestMapping(value = "/admin/get/{message}")
    public String get(@PathVariable String message) {


        return "[ADMIN   ECHO  Get:" + getPort() + "] " + message;
    }

}
