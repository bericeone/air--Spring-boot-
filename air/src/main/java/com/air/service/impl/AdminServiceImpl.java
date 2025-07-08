package com.air.service.impl;

import com.air.entity.Admin;
import com.air.mapper.AdminMapper;
import com.air.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    
    @Autowired
    private AdminMapper adminMapper;
    
    @Override
    public Admin login(String username, String password) {
        return adminMapper.findByUsernameAndPassword(username, password);
    }
} 