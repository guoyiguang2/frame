package com.gupaoedu.service.entity;


import lombok.Data;

import java.lang.annotation.Annotation;

@Data
public class Param {

    private String simpleName;//简单名字


    private String name;//名字

    private Class<?> type;//类型


    private Object value;//值

    private Annotation anno;//注解

    public Param() {
        super();
    }


    public Param(String simpleName,String name, Class<?> type, Object value, Annotation anno) {

        super();

        this.simpleName = simpleName;

        this.name = name;

        this.type = type;
        this.value = value;
        this.anno = anno;


    }
}
