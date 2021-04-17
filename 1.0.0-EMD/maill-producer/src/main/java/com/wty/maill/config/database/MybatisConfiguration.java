package com.wty.maill.config.database;

import com.wty.maill.enumeration.DataSourceType;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.aspectj.apache.bcel.util.ClassLoaderRepository;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@AutoConfigureAfter(DataSourceConfiguration.class) //告诉spring 加载完了数据源配置再来找我
public class MybatisConfiguration  {

    @Resource(name = "masterDataSource")
    private DataSource masterDataSource;

    @Resource(name = "slaveDataSource")
    public DataSource slaveDataSource;

//    public MybatisConfiguration(MybatisProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider, ObjectProvider<TypeHandler[]> typeHandlersProvider, ObjectProvider<LanguageDriver[]> languageDriversProvider, ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider, ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
//        super(properties, interceptorsProvider, typeHandlersProvider, languageDriversProvider, resourceLoader, databaseIdProvider, configurationCustomizersProvider);
//    }


//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
////        return super.sqlSessionFactory(roundRobinDataSourceProxy());
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(masterDataSource);
////        bean.setTypeAliasesPackage("com.wty.maill.entity");
////        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResource("/com/wty/maill/mapping/*.xml"));
//        return bean.getObject();
//    }

    @Bean(name = "masterSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate masterSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "masterPlatformTransactionManager")
    @Primary
    public PlatformTransactionManager masterPlatformTransactionManager(){
        return new DataSourceTransactionManager(masterDataSource);
    }

    @Bean(name = "masterJdbcTemplate")
    @Primary
    public JdbcTemplate masterJdbcTemplate(){
        return new JdbcTemplate(masterDataSource);
    }

    @Bean(name = "slaveJdbcTemplate")
    public JdbcTemplate slaveJdbcTemplate(){
        return new JdbcTemplate(slaveDataSource);
    }

    @Bean(name = "slaveSqlSessionTemplate")
    public SqlSessionTemplate slaveSqlSessionTemplate(SqlSessionFactory sqlSessionFactory)  {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "slavePlatformTransactionManager")
    public PlatformTransactionManager slavePlatformTransactionManager(){
        return new DataSourceTransactionManager(slaveDataSource);
    }

    /** 获取AbstractRoutingDataSource
     *  负责主从切换
     * */
    @Bean
    public AbstractRoutingDataSource roundRobinDataSourceProxy(){
        //创建出来AbstractRoutingDataSource
        ReadWriteSplitRoutingDataSource proxy = new ReadWriteSplitRoutingDataSource();
        //可排序的map
//        ClassLoaderRepository.SoftHashMap targetDataSource = new ClassLoaderRepository.SoftHashMap();
        Map<Object,Object> targetDataSource = new HashMap<>();
        //主数据源
        targetDataSource.put(DataSourceType.MASTER,masterDataSource);
        //从数据源
        targetDataSource.put(DataSourceType.SLAVE,slaveDataSource);
        //设置一个默认的数据源 -> 主数据源
        proxy.setDefaultTargetDataSource(masterDataSource);
        //主从都放入
        proxy.setTargetDataSources(targetDataSource);
        return proxy;
    }

}
