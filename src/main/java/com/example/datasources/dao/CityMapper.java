package com.example.datasources.dao;

import com.example.datasources.annotation.MasterDataSource;
import com.example.datasources.entity.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CityMapper {

    @MasterDataSource
    void insertCity(City city);
    /**
     * 根据城市名称，查询城市信息
     *
     * @param cityName 城市名
     */
    //@Select("select * from city where city_name like CONCAT('%', #{cityName},'%')")
    List<City> selectByName(@Param("cityName") String cityName);
}