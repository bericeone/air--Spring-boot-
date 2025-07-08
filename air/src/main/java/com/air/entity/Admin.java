package com.air.entity;

import lombok.Data;

@Data
public class Admin {
    private Integer id;
    private String username;
    private String password;
    private String createTime;
    private String updateTime;
} 