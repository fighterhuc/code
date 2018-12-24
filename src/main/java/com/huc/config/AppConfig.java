package com.huc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huc.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Configuration
@EnableWebMvc
@ComponentScan("com.huc")
@Import({MybatisConfig.class,ShiroConfig.class})
public class AppConfig extends WebMvcConfigurerAdapter {
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(this.mappingJackson2HttpMessageConverter());
    }

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        ArrayList httpMessageConverterList = new ArrayList();
        httpMessageConverterList.add(this.mappingJackson2HttpMessageConverter());
        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
        adapter.setMessageConverters(httpMessageConverterList);
        /// TODO: 2018/12/20  可以 通过继承WebBindingInitializer 设置类型绑定转化器
        adapter.setWebBindingInitializer(new MyWebBindingInitializer());
        return adapter;
    }
    @Bean
    public HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter httpMessageConverter = new MappingJackson2HttpMessageConverter();
        httpMessageConverter.setObjectMapper(objectMapper());
        return httpMessageConverter;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return objectMapper;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/*");
    }

}
