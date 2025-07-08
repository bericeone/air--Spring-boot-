package com.air.service.impl;

import com.air.entity.Flight;
import com.air.mapper.FlightMapper;
import com.air.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {
    
    @Autowired
    private FlightMapper flightMapper;
    
    @Override
    public List<Flight> findByPage(int page, int size, String flightNumber, String departureCity, String arrivalCity, Integer status) {
        int offset = (page - 1) * size;
        return flightMapper.findByPage(offset, size, flightNumber, departureCity, arrivalCity, status);
    }
    
    @Override
    public int getTotalPages(int size, String flightNumber, String departureCity, String arrivalCity, Integer status) {
        int total = flightMapper.count(flightNumber, departureCity, arrivalCity, status);
        return (total + size - 1) / size;
    }
    
    @Override
    public Flight findById(Integer id) {
        return flightMapper.findById(id);
    }
    
    @Override
    @Transactional
    public void add(Flight flight) {
        flightMapper.insert(flight);
    }
    
    @Override
    @Transactional
    public void update(Flight flight) {
        flightMapper.update(flight);
    }
    
    @Override
    @Transactional
    public void delete(Integer id) {
        flightMapper.delete(id);
    }
} 