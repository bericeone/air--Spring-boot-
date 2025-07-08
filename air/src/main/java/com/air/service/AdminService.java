package com.air.service;

import com.air.entity.Admin;

public interface AdminService {
    /**
     * 管理员登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回管理员对象，失败返回null
     */
    Admin login(String username, String password);
} 