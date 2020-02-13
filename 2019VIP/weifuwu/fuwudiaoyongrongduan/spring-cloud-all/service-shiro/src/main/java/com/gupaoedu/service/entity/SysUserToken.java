package com.gupaoedu.service.entity;


import lombok.Data;

import java.util.Date;


/*
* redis 中存的数据
* */
@Data
public class SysUserToken {

    private String userId;

    private String name;

    private String token;


    /**
     * loginName  用户登录输入的用户名，加密后放到redis，解密用来验证用户身份的
     */
    private String loginName;

    /**
     * password  用户登录输入的密码，加密后放到redis，解密用来验证用户身份的
     */
    private String password;


    private Date lastUpdateTime;

    private Date expireTime;

}
