package com.gupaoedu.service.utils;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.gupaoedu.service.annotation.Validate;
import com.gupaoedu.service.entity.Param;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

public class AnnotationHelper {

    /**
     * 获取MethodSignature
     *
     * @param point
     * @return
     */


    public static Signature getMethod(ProceedingJoinPoint point) {
        MethodSignature sign = (MethodSignature) point.getSignature();
        return sign;
    }

    /**
     * 获取参数列表
     *
     * @param point
     * @return
     */
    public static Object[] getArgs(ProceedingJoinPoint point) {
        return point.getArgs();
    }

    /**
     * 获取参数的描述
     *
     * @param method
     * @param objs
     * @return
     */
    public static List<Param> getParms(Method method, Object[] objs) {
        Annotation[][] annos = method.getParameterAnnotations();
        Class<?>[] paramTypes = method.getParameterTypes();
        List<Param> params = new ArrayList<Param>();
        for (int i = 0; i < annos.length; i++) {
            for (int j = 0; j < annos[i].length; j++) {
                //如果出现指定的注解类型
                if (annos[i][j].annotationType() == Validate.class) {
                    Param param = new Param(paramTypes[i].getSimpleName(),
                            paramTypes[i].getName(),//名称
                            paramTypes[i],//参数类型
                            objs[i],//参数值
                            annos[i][j]);//筛选出的注解
                    params.add(param);
                }
            }
        }
        return params;
    }

}
