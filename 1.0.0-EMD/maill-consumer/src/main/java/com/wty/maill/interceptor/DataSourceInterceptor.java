package com.wty.maill.interceptor;

import com.wty.maill.annotation.DataSourceCondition;
import com.wty.maill.config.database.DataBaseContextHolder;
import com.wty.maill.enumeration.DataSourceType;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.lang.reflect.Field;

@Component
@Aspect
public class DataSourceInterceptor implements Ordered {

    @Resource
    private ApplicationContext applicationContext;

    @Before("@annotation(condition)")
    public void changeDataSource(DataSourceCondition condition) throws Exception {

        DataBaseContextHolder.setDataSourceType(condition.value());
        SqlSessionFactory sqlSessionFactory = applicationContext.getBean(SqlSessionFactory.class);
        DataSource dataSource = (DataSource)applicationContext.getBean(DataBaseContextHolder.getDataSourceType().getName());
        Environment environment = sqlSessionFactory.getConfiguration().getEnvironment();
        Field dataSourceFiled = environment.getClass().getDeclaredField("dataSource");
        dataSourceFiled.setAccessible(true);
        dataSourceFiled.set(environment,dataSource);
    }

    @After("@annotation(condition)")
    public void clearDataSource(DataSourceCondition condition) throws Exception {
        DataBaseContextHolder.clearDataSourceType();
        if(!condition.value().getName().equals(DataSourceType.MASTER.getName())){
            SqlSessionFactory sqlSessionFactory = applicationContext.getBean(SqlSessionFactory.class);
            DataSource dataSource = (DataSource)applicationContext.getBean(DataSourceType.MASTER.getName());
            Environment environment = sqlSessionFactory.getConfiguration().getEnvironment();
            Field dataSourceFiled = environment.getClass().getDeclaredField("dataSource");
            dataSourceFiled.setAccessible(true);
            dataSourceFiled.set(environment,dataSource);
        }

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
