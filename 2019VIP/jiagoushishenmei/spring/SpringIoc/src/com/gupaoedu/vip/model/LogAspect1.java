package com.gupaoedu.vip.model;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;


public class LogAspect1 {


    public void before(JoinPoint joinpoint) {
        System.out.println("LogAspect1 before  " + joinpoint);

    }

    //如果这儿返回为 void，则不会返回执行结果
    public Object around(JoinPoint joinpoint) {
        System.out.println("LogAspect1  around  " + joinpoint);
        ProceedingJoinPoint proceedingJoinPoint = (ProceedingJoinPoint)joinpoint;
        Object result = null;
        try {
            //去调用目标方法，并获得返回结果
             result = proceedingJoinPoint.proceed();
             //返回结果（不返回，调用方拿到的数据是null）
            return  result;

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return  result;
    }

    public void after(JoinPoint joinpoint) {
        System.out.println("LogAspect1 after  " + joinpoint);
    }

    public void afterReturn(JoinPoint joinpoint) {
        System.out.println("LogAspect1  afterReturn  " + joinpoint);
    }

    public void afterThrows(JoinPoint joinpoint) {
        System.out.println("LogAspect1 afterThrows  " + joinpoint);
    }

}
