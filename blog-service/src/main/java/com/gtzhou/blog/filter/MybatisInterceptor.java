package com.gtzhou.blog.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;

/**
 * Created with IntelliJ IDEA.
 * Description:监控记录sql
 * User: ZGW
 * Date: 2018/7/18
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class,method = "update",args = {
                MappedStatement.class,Object.class
        }),
        @Signature(type = Executor.class,method = "query",args = {
                MappedStatement.class,Object.class,RowBounds.class,
                ResultHandler.class
        })
})
public class MybatisInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        Object result = null;
        if (target instanceof Executor){
            long start = System.currentTimeMillis();
            Method method = invocation.getMethod();
            //执行方法
            result = invocation.proceed();
            long end = System.currentTimeMillis();
            log.info("[TimerInterceptor] execute [" + method.getName() + "] cost [" + (end - start) + "] ms");
        }

        try{
            Object params = null;
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            if (invocation.getArgs().length>1){
                params = invocation.getArgs()[1];
                System.out.println("参数："+params);
            }
            String sqlId = mappedStatement.getId();
            BoundSql boundSql = mappedStatement.getBoundSql(params);
            Configuration configuration = mappedStatement.getConfiguration();
            String sql = getSql(sqlId,boundSql,configuration);
            System.out.println("完整的sql："+sql);

        }catch (Exception e){

        }


        return result;
    }

    private  String getSql(String sqlId,BoundSql boundSql,Configuration configuration){
        String  sql = showSql(boundSql,configuration);
        StringBuilder str = new StringBuilder();
        str.append(sqlId);
        str.append(":");
        str.append(sql);

        return str.toString();
    }
    private String showSql(BoundSql boundSql,Configuration configuration){
        Object parameter = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]", " ");
        if (parameter!=null && !CollectionUtils.isEmpty(parameterMappings)){
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameter.getClass())){
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameter)));
            }else {
                MetaObject metaObject = configuration.newMetaObject(parameter);// MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,主要支持对JavaBean、Collection、Map三种类型对象的操作
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);  // 该分支是动态sql
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    }else{sql=sql.replaceFirst("\\?","缺失");}//打印出缺失，提醒该参数缺失并防止错位
                }
            }
        }
        return sql;

    }

    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }

    @Override
    public void setProperties(Properties properties) {

    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
