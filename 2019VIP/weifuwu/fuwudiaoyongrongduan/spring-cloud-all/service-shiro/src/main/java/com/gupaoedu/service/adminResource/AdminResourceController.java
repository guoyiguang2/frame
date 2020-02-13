package com.gupaoedu.service.adminResource;

import com.gupaoedu.service.annotation.Limited;
import com.gupaoedu.service.annotation.Timeout;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

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

    @GetMapping(value = "/admin/echo/{message}")
    public String echo(@PathVariable String message) {
        return "[ADMIN   ECHO:" + getPort() + "] " + message;
    }

}
