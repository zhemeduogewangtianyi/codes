package com.wty.maill.enumeration;

/**
 * 枚举类，用来标记 MASTER / SLAVE
 * */
public enum DataSourceType {
    MASTER("masterDataSource"),SLAVE("slaveDataSource");

    private String name;

    DataSourceType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
