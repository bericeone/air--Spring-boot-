package com.air.service;

import com.air.entity.Flight;
import java.util.List;

public interface FlightService {
    /**
     * 分页查询航班列表
     */
    List<Flight> findByPage(int page, int size, String flightNumber, String departureCity, String arrivalCity, Integer status);
    
    /**
     * 获取总页数
     */
    int getTotalPages(int size, String flightNumber, String departureCity, String arrivalCity, Integer status);
    
    /**
     * 根据ID查询航班
     */
    Flight findById(Integer id);
    
    /**
     * 添加航班
     */
    void add(Flight flight);
    
    /**
     * 更新航班
     */
    void update(Flight flight);
    
    /**
     * 删除航班
     */
    void delete(Integer id);
} 