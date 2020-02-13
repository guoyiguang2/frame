package com.gupaoedu.service.service;

import com.gupaoedu.service.entity.UserVo;

public interface UserService {

    public UserVo getUserFromDataBase(String loginName);

    public UserVo getUserFromRedis(String loginName);

    public UserVo getUserFromRedisByToken(String token);
}
