package com.huc.interceptor;

import com.huc.util.Page;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * mybatis 分页拦截器
 *
 */
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class}))
public class MyBatisPageInterceptopr  implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler =getActuralHandlerObject(invocation);
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        String sql = statementHandler.getBoundSql().getSql();
        //检查是否是select语句，如果不是则直接放行
        if(checkIsSelectFalg(sql)){
            return invocation.proceed();
        }
        BoundSql boundSql = statementHandler.getBoundSql();
        Object paramObject = boundSql.getParameterObject();
        Page page = getPageParam(paramObject);
        //如果参数是空则直接放行
        if(page==null){
            return invocation.proceed();
        }
        //获取页码
        Integer pageNum = page.getPageNum();
        //获取每页条数
        Integer pageSize = page.getPageSize();
        int total = getTotal(invocation,metaStatementHandler,boundSql);
        //将动态获取到的分页参数会天道pageParam中
        setTotleToParam(page,total,pageSize);
        return updateSql2Limit(invocation,metaStatementHandler,pageNum,pageSize);
    }

    /**
     * 生成代理对象
     * @param o
     * @return
     */
    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
    private StatementHandler getActuralHandlerObject(Invocation invocation){
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        //获取statementHandler的代理类
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        Object  object = null;
        //分离代理链，目标可能被多个拦截器拦截，分离出最原始的目标类
        while (metaStatementHandler.hasGetter("h")){
            object = metaStatementHandler.getValue("h");
            metaStatementHandler = SystemMetaObject.forObject(object);
        }
        if(object==null){
            return  statementHandler;
        }
        return (StatementHandler)object;
    }

    /**
     * 判断是否是select语句
     * @param sql
     * @return
     */
    private boolean checkIsSelectFalg(String sql){
        return !sql.trim().toLowerCase().contains("select");
    }

    /**
     * 获取分页参数
     * @param parameObject
     * @return
     */
    private Page getPageParam(Object parameObject){
        if(parameObject==null){
            return null;
        }
        Page page =null;
        if(parameObject instanceof Map){
            Map<String,Object> params = (Map<String,Object>)parameObject;
            for(Map.Entry<String,Object> entry:params.entrySet()){
                if(entry.getValue() instanceof Page){
                    return (Page)entry.getValue();
                }
            }
        }else if(parameObject instanceof Page){
            page = (Page)parameObject;
        }
        return page;
    }

    /**
     * 获取总条数
     * @param invocation
     * @param metaStatementHandler
     * @param boundSql
     * @return
     */
    private int getTotal(Invocation invocation, MetaObject metaStatementHandler, BoundSql boundSql){
        //获取mapper文件中当前语句的配置信息
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
       //获取所有的配置
        Configuration configuration = mappedStatement.getConfiguration();
        //获取当前查询的sql
        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        String countSql = "select count(*) as total from ("+sql+")$_paging";
        //获取connection 连接对象，用于和执行countsql
        Connection conn = (Connection) invocation.getArgs()[0];
        PreparedStatement ps = null;
        int total = 0;
        try{
            //预编译统计总记录数的sql
            ps = conn.prepareStatement(countSql);
            //构建统计的BoundSql
            BoundSql countBoundSql = new BoundSql(configuration,countSql,boundSql.getParameterMappings(),boundSql.getParameterObject());
            //构建paramterHandler ，用于设置统计的sql参数
            ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement,boundSql.getParameterObject(),countBoundSql);
            //设置总数的sql参数
            parameterHandler.setParameters(ps);
            //执行查询语句
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                total = rs.getInt("total");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return total;
    }

    /**
     * 设置条数参数到Page对象中
     * @param page
     * @param total
     * @param pageSize
     */
    private void setTotleToParam(Page page,int total,int pageSize){
        page.setTotalCount(total);
        page.setTotalPage(total%pageSize==0?total/pageSize:(total/pageSize+1));
    }

    /**
     * 修改原始的sql语句为分页sql语句
     * @param invocation
     * @param metaStatementHandler
     * @param pageNum
     * @param pageSize
     * @return
     */
    private Object updateSql2Limit(Invocation invocation,MetaObject metaStatementHandler,int pageNum,int pageSize) throws InvocationTargetException, IllegalAccessException, SQLException {
        String sql = (String)metaStatementHandler.getValue("delegate.boundSql.sql");
        //构建分页的sql语句
        String limitSql = "select * from ("+sql+")$_paging_table limit ?,?";
        //修改当前要执行的sql语句
        metaStatementHandler.setValue("delegate.boundSql.sql",limitSql);
        //相当于调用prepare方法，预编译的sql并且加入参数，但是少了分页的两个参数，他返回一个ps
        PreparedStatement ps = (PreparedStatement) invocation.proceed();
        //设置分页的两个参数
        int count = ps.getParameterMetaData().getParameterCount();
        ps.setInt(count-1,(pageNum-1)*pageSize);
        ps.setInt(count,pageSize);
        return ps;
    }
}
