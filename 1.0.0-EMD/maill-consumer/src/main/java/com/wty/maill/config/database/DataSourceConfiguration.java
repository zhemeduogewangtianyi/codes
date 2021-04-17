package com.wty.maill.config.database;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {

    /** 日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfiguration.class);

    /** 获取application.yml文件中配置的数据源类型 */
//    @Value("${druid.type}")
//    private Class<? extends DataSource> dataSourceType;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "druid.master")
    public DataSourceProperties masterDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "druid.slave")
    public DataSourceProperties slaveDataSourceProperties(){
        return new DataSourceProperties();
    }

    /** 主数据源 */
    @Bean(name = "masterDataSource")
    @Primary
    public DataSource masterDataSource(){
        DataSourceProperties dataSourceProperties = masterDataSourceProperties();
        DataSource masterDataSource = dataSourceProperties.initializeDataSourceBuilder().build();
        LOGGER.info("=========MASTAER：{}============",masterDataSource);
        return masterDataSource;
    }

    /** 从数据源 */
    @Bean(name = "slaveDataSource")
    public DataSource slaveDataSource(){
        DataSourceProperties dataSourceProperties = slaveDataSourceProperties();
        DataSource slaveDataSource = dataSourceProperties.initializeDataSourceBuilder().build();
        LOGGER.info("=========SLAVE：{}============",slaveDataSource);
        return slaveDataSource;
    }

    /** 自定义Servlet , 这里玩的Druid定义的Servlet 实现的监控台 */
    @Bean
    public ServletRegistrationBean druidServlet(){
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
//        reg.setAsyncSupported(true);
        reg.setUrlMappings(Arrays.asList(new String[]{"/druid/*"}));
        reg.addInitParameter("allow","localhost");
//        reg.addInitParameter("deny","deny");
//        reg.addInitParameter("loginUsername","wty");
//        reg.addInitParameter("loginUsername","wty");
        LOGGER.info("druid console manager init {}",reg);
        return reg;
    }

    /** Druid的过滤器 */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        LOGGER.info("druid filter register : {}",filterRegistrationBean);
        return filterRegistrationBean;
    }

}
