package com.gupaoedu.service.publicResource;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicResourceController {

    // 外部化配置其实是有点不靠谱的 - 它并非完全静态，也不一定及时返回

    private final Environment environment;

    public PublicResourceController(Environment environment) {
        this.environment = environment;
    }

    private String getPort() {
        return environment.getProperty("local.server.port");
    }

    @GetMapping(value = "/public/echo/{message}")
    public String echo(@PathVariable String message) {
        return "[PUBLIC   ECHO:" + getPort() + "] " + message;
    }




}
