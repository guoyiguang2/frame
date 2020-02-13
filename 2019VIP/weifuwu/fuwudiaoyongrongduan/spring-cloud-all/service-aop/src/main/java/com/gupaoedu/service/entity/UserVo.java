package com.gupaoedu.service.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserVo {

    private String name;

    private String password;

    private List<Role> rolesList;

    public UserVo() {
    }

    public UserVo(String name, String password,List<Role> rolesList) {
        this.name = name;
        this.password = password;
        this.rolesList = rolesList;
    }


    @Override
    public String toString() {
        return "UserVo{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", rolesList=" + rolesList +
                '}';
    }
}
