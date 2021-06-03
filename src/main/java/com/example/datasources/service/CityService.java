package com.example.datasources.service;

import com.example.datasources.entity.City;

import java.util.List;

//接口
public interface CityService {
    void addCity(City city);
 
    List<City> getCityByCityName(String cityName);
}