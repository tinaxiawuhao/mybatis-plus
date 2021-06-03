package com.example.datasources.utils;

public class DbUtil {
    public static String master="master";
    public static String slave="slave";
 
    private static final ThreadLocal<String> threadLocal=new ThreadLocal();
 
 
    public static void setDb(String db){
        threadLocal.set(db);
    }
 
    public static String getDb(){
        return threadLocal.get();
    }
 
}