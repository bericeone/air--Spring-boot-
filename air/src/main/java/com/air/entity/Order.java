package com.air.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {
    private Integer id;
    private String orderNo;
    private Integer ticketId;
    private String passengerName;
    private String passengerIdCard;
    private String contactPhone;
    private Integer status;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 关联的航班信息
    private Flight flight;
} 