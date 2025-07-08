package com.air.mapper;

import com.air.entity.Flight;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface FlightMapper {
    /**
     * 分页查询航班列表
     */
    List<Flight> findByPage(@Param("offset") int offset,
                           @Param("size") int size,
                           @Param("flightNumber") String flightNumber,
                           @Param("departureCity") String departureCity,
                           @Param("arrivalCity") String arrivalCity,
                           @Param("status") Integer status);
    
    /**
     * 统计符合条件的航班总数
     */
    int count(@Param("flightNumber") String flightNumber,
             @Param("departureCity") String departureCity,
             @Param("arrivalCity") String arrivalCity,
             @Param("status") Integer status);
    
    /**
     * 根据ID查询航班
     */
    Flight findById(@Param("id") Integer id);
    
    /**
     * 添加航班
     */
    void insert(Flight flight);
    
    /**
     * 更新航班
     */
    void update(Flight flight);
    
    /**
     * 删除航班
     */
    void delete(@Param("id") Integer id);
} 