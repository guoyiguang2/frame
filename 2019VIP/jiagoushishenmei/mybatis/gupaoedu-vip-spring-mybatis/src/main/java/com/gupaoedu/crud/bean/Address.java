package com.gupaoedu.crud.bean;

import java.io.Serializable;

public class Address implements Serializable {

    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Address{" +
                "detail='" + detail + '\'' +
                '}';
    }
}
