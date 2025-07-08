package com.air.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Flight {
    private Long id;
    private String flightNumber;
    private String departureCity;
    private String arrivalCity;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime departureTime;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime arrivalTime;
    
    private BigDecimal price;
    private Integer totalSeats;
    private Integer availableSeats;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}