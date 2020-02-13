package com.gupaoedu.service.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserVo {

    private String id;

    private String name;

    private String password;

    private String token;

    private int status;


    private List<Role> rolesList;

    public UserVo() {
    }

    public UserVo(String name, String password,List<Role> rolesList) {
        this.name = name;
        this.password = password;
        this.rolesList = rolesList;
    }

    public String getToken() {
        return "ABCDEFG";
    }
}
