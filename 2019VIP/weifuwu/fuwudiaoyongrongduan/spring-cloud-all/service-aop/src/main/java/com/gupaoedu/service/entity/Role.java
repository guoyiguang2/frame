package com.gupaoedu.service.entity;


import lombok.Data;

import java.util.List;

@Data
public class Role {
    private String id;
    private String name;
    private List<PermissionVo> permissions;


    public Role() {
    }
    public Role(String id, String name,List<PermissionVo> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
