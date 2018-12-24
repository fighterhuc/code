package com.huc.config;

import com.huc.util.Page;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class Dao  extends SqlSessionDaoSupport {
    /**
     * 设置工厂类
     *
     * @param sqlSessionFactory sql工厂类
     */
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }
    public <T> T get(String prefix,String key,Object params){
        return getSqlSession().selectOne(prefix+key,params);
    }
    public void insert(String prefix,String key,Object params){
        getSqlSession().insert(prefix+key,params);
    }
    public void update(String prefix,String key,Object params){
        getSqlSession().update(prefix+key,params);
    }
    public void delete(String prefix,String key,Object params){
        getSqlSession().delete(prefix+key,params);
    }
    public <T> List<T> getList(String prefix, String key, Object params) {
        return this.getSqlSession().selectList(prefix + key, params);
    }

    /**
     * 获取分页的数据
     * @param prefix
     * @param key
     * @param params
     * @param page
     * @return
     */
    public Page page(String prefix, String key, Map<String,Object> params, Page page) {
        params.put("page",page);
        page.setList(this.getSqlSession().selectList(prefix + key, params));
        return page;
    }
}
