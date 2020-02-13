package com.gupaoedu.service.entity;

import com.gupaoedu.service.annotation.Must;
import lombok.Data;

import java.io.Serializable;


@Data
public class TestForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Must
    private int age;

    @Must
    private String name;

    private String[] pics;

    private long goodsIds;
}
