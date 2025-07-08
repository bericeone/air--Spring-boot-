package com.air.mapper;

import com.air.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
    /**
     * 根据用户名和密码查询管理员
     * @param username 用户名
     * @param password 密码
     * @return 管理员对象
     */
    Admin findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
} 