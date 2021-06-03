package com.example.datasources.service.impl;

import com.example.datasources.dao.CityMapper;
import com.example.datasources.entity.City;
import com.example.datasources.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//实现类
@Service
public class CityServiceImpl implements CityService {
    @Autowired
    CityMapper cityMapper;

    @Override
    public void addCity(City city) {
        cityMapper.insertCity(city);
    }

    @Override
    public List<City> getCityByCityName(String cityName) {
        return cityMapper.selectByName(cityName);
    }
}