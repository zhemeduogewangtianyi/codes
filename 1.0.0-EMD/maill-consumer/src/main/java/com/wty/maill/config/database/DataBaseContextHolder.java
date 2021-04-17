package com.wty.maill.config.database;

import com.wty.maill.enumeration.DataSourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataBaseContextHolder {

    /** 日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseContextHolder.class);

    /** 定义线程局部变量 ThreadLocal 存放数据源枚举类标记 */
    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();

    /** 放置数据源类型标识 */
    public static void setDataSourceType(DataSourceType dataSourceType){
        if(dataSourceType == null){
            throw new NullPointerException();
        }
        LOGGER.info("设置的数据源是：{}",dataSourceType);
        contextHolder.set(dataSourceType);
    }

    /** 取出数据源类型标识 */
    public static DataSourceType getDataSourceType(){
        DataSourceType dataSourceType = contextHolder.get();
        if(dataSourceType == null){
            //null 直接返回MASTER
            return DataSourceType.MASTER;
        }
        LOGGER.info("获取的数据源是：{}",dataSourceType);
        return dataSourceType;
    }

    /** 清空缓存的数据源类型标识 */
    public static void clearDataSourceType(){
        LOGGER.info("清除数据源！");
        contextHolder.remove();
    }


}
