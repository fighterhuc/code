package com.huc.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.huc.interceptor.MyBatisPageInterceptopr;
import com.huc.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


@Slf4j
public class MybatisConfig  implements ApplicationContextAware {
    private static PropertiesUtil propertiesUtil;
    private ApplicationContext applicationContext;
    static {
        propertiesUtil = new PropertiesUtil("jdbc.properties");
    }

    /**
     * 配置连接池
     * @return
     */
    @Bean
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(propertiesUtil.getProperty("db.url"));
        dataSource.setUsername(propertiesUtil.getProperty("db.username"));
        dataSource.setPassword(propertiesUtil.getProperty("db.password"));
        dataSource.setDriverClassName(propertiesUtil.getProperty("db.driverName"));
        dataSource.setMaxActive(Integer.parseInt(propertiesUtil.getProperty("db.maxActive")));
        return dataSource;
    }

    /**
     * 配置sqlfactory
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        Resource[] resources = applicationContext.getResources("classpath:mappers/**/*.xml");
        factoryBean.setMapperLocations(resources);
        //设置分页的插件
        Interceptor[] interceptors = new Interceptor[]{new MyBatisPageInterceptopr()};
        factoryBean.setPlugins(interceptors);
        return factoryBean.getObject();
    }

    /**
     * 配置全局事务
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManager(){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
