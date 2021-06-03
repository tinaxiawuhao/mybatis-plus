package com.example.datasources.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.example.datasources.utils.DbUtil;
import com.example.datasources.utils.DynamicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;


import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {
 
    public final static String MAPPER_XML_PATH = "classpath:mapper/*.xml";
 
    @ConfigurationProperties(prefix = "master.datasource.druid")
    @Bean(name = "masterDataSource")
    public DataSource masterDataSource() {
        return new DruidDataSource();
    }
 
 
    @Bean
    public PlatformTransactionManager txManager(DataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }
 
 
    @ConfigurationProperties(prefix = "slave.datasource.druid")
    @Bean
    public DataSource slaveDataSource(){
        return  new DruidDataSource();
    }
 
 
    @Bean
    public DynamicDataSource dynamicDataSource(){
        DynamicDataSource dynamicDataSource=new DynamicDataSource();
        Map<Object,Object> map=new HashMap<>();
        map.put(DbUtil.master,masterDataSource());
        map.put(DbUtil.slave,slaveDataSource());
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
        dynamicDataSource.setTargetDataSources(map);
        return dynamicDataSource;
    }
 
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dynamicDataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dynamicDataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_XML_PATH));
        return sqlSessionFactory;
    }
 
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
        return sqlSessionTemplate;
    }

    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<StatViewServlet>( new StatViewServlet(), "/druid/*" );

        Map<String,String> iniParms=new HashMap<>(  );

        iniParms.put( "loginUsername","admin" );//登录druid的用户名
        iniParms.put( "loginPassword","123456" );//登录druid的密码
        iniParms.put("allow","");//默认允许所有
        //iniParms.put( "deny","192.168.***.***" );//拒绝的ip地址
        bean.setInitParameters( iniParms );
        return bean;

    }

    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean= new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> iniParms=new HashMap<>();
        iniParms.put( "excliusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");//使静态文件访问，还有/druid/* 的访问不被拦截
        bean.setInitParameters( iniParms );
        bean.setUrlPatterns( Arrays.asList("/*"));
        return bean;
    }
}