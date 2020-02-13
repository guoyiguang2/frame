package com.gupaoedu.service;

import com.gupaoedu.service.annotation.Limited;
import com.gupaoedu.service.annotation.Timeout;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.*;

@RestController
public class EchoServiceController {

    // 外部化配置其实是有点不靠谱的 - 它并非完全静态，也不一定及时返回

    private final Environment environment;

    public EchoServiceController(Environment environment) {
        this.environment = environment;
    }

    private String getPort() {
        return environment.getProperty("local.server.port");
    }

//    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Limited(10)           // 限流
    @GetMapping("/world")
    public String world() {
        await();
        return "world";
    }

    @Timeout(value = 50L, fallback = "fallbackHello")
    @GetMapping("/hello")
    public String hello() throws ExecutionException, InterruptedException, TimeoutException {
//        Future<String> future = executorService.submit(this::doHello);
//        return future.get(50, TimeUnit.MILLISECONDS);
        return doHello();
    }

    private String doHello() {
        await();
        return "Hello!";
    }

    public String fallbackHello() {
        return "FALLBACK";
    }

    @Limited(1)
    @Timeout(value = 50L, fallback = "fallback")
    // Hystrix 配置文档：https://github.com/Netflix/Hystrix/wiki/Configuration
//    @HystrixCommand(
//            fallbackMethod = "fallback",
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "50"),
//            })
    @GetMapping(value = "/echo/{message}")
    public String echo(@PathVariable String message) {
        await();
        return "[ECHO:" + getPort() + "] " + message;
    }


    public String fallback(String abc) {
        return "FALLBACK - " + abc;
    }

    public String fallback(String abc, boolean value) {
        return "FALLBACK - " + abc;
    }

    private final Random random = new Random();

    private void await() {
        long wait = random.nextInt(100);
        System.out.printf("[当前线程 : %s] 当前方法执行(模型) 消耗 %d 毫秒\n", Thread.currentThread().getName(), wait);
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
