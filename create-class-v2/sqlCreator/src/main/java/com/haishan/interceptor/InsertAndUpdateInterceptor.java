package com.haishan.interceptor;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.util.*;

@Intercepts({@Signature(type = Executor.class, method = "update",
        args = {MappedStatement.class, Object.class})
})
public class InsertAndUpdateInterceptor implements Interceptor {

   @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object o = null;
        BoundSql boundSql = ms.getBoundSql(args[1]);

        if("".equals(boundSql.getSql())){
            o =  resetInvocation(invocation, (Map) args[1],ms);

        }
        if (o==null){
            o = invocation.proceed();
        }
        return o;
    }

    /**
     *  进行请求处理
     * @param invocation mybatis原生   sql执行器
     * @param param mybatis原生 方法参数
     * @param ms  mybatis原生 配置
     * @return 查询结果
     * @throws Exception 异常
     */
    private Object resetInvocation(Invocation invocation, Map param,MappedStatement ms) throws Exception {
        if (!param.containsKey("tableName")){
            throw new Exception("not found tableName");
        }
        String tableName = param.get("tableName").toString();

        Map<String,String> field ;
        if (!TableCache.getInstance().TABLEPOOL.containsKey(tableName)){
            TableCache.getInstance().update(tableName);
        }
        field = TableCache.getInstance().TABLEPOOL.get(tableName);
        Object o = null;
        if ("INSERT".equals(ms.getSqlCommandType().name())){
            o = insert(tableName,invocation,ms,param,field);
        }
        if ("UPDATE".equals(ms.getSqlCommandType().name())){
            o = update(tableName,invocation,ms,param,field);
        }
        if ("DELETE".equals(ms.getSqlCommandType().name())){
            o = delete(tableName,invocation,ms,param,field);
        }
        return o;
    }

    private Object delete(String tableName, Invocation invocation, MappedStatement ms, Map<String,Object> map,
                          Map<String,String> field) throws Exception {
        SqlSource sqlSource =ms.getSqlSource();
        String sql;
        if (map.containsKey("key")){
            String key =  map.get("key").toString();
            if ("".equals(key)){
                throw new Exception("key is Empty");
            }
            sql = getDeleteSql(tableName,key,field);
        }else if (map.containsKey("condition")){
            Map<String,Object> condition = (Map<String,Object>) map.get("condition");
            sql = getDeleteSql(tableName,condition,field);
        }else {
            throw new Exception("not found sql and not found Primary Key and not found Condition:\n"
                    +"you can coding sql or add param in baseDao delete method");
        }
        SqlSource sqlSource1 = new StaticSqlSource(ms.getConfiguration(), sql, null);
        System.out.println("\n"+sql+"\n");
        Field classField = MappedStatement.class.getDeclaredField("sqlSource");
        classField.setAccessible(true);
        classField.set(ms, sqlSource1);
        Object res =  invocation.proceed();
        classField.set(ms,sqlSource);
        return res;
    }

    private String getDeleteSql(String tableName, String key, Map<String, String> field) throws Exception {
        String sql = "DELETE FROM " + tableName + " WHERE 1 = 1 ";
        String keyName = field.get("key");
        if (keyName==null){
            throw new Exception("not found this table primary key by deleteByKey()");
        }
        if("varchar".equals(keyName)
                ||"date".equals(keyName)
                || "datetime".equals(keyName)
                || "char".equals(keyName)
                ||"text".equals(keyName)
                ||"longtext".equals(keyName)){
            sql += " and `"+keyName +"` = '" + key + "'";
        }else { sql += " and `"+keyName +"` = " + key;}
        return sql;
    }
    private String getDeleteSql(String tableName, Map<String,Object> condition, Map<String, String> field) throws Exception {
        boolean f = true;
        String sql = "DELETE FROM " + tableName + " WHERE 1 = 1 ";
        for (Map.Entry<String, Object> entry : condition.entrySet()) {
            if(null==entry.getValue()||"".equals(entry.getValue().toString())){
                continue;
            }
            if (!field.containsKey(entry.getKey())){
                continue;
            }
            f=false;
            if("varchar".equals(field.get(entry.getKey()))
                    ||"date".equals(field.get(entry.getKey()))
                    || "datetime".equals(field.get(entry.getKey()))
                    || "char".equals(field.get(entry.getKey()))
                    ||"text".equals(field.get(entry.getKey()))
                    ||"longtext".equals(field.get(entry.getKey()))){
                sql = sql + "\n and `" + entry.getKey()+ "` = '" + entry.getValue().toString() + "' ";
            }else {
                sql = sql + "\n and `" + entry.getKey()+ "` = " + entry.getValue().toString() + " ";
            }
        }
        if (f){
            throw new Exception("condition is Empty or not fount this field in your condition");
        }
        return sql;
    }

    private Object insert(String tableName, Invocation invocation, MappedStatement ms, Map<String,Object> map,
                          Map<String,String> field) throws Exception {
        SqlSource sqlSource =ms.getSqlSource();
        String sql;
        if (map.get("value") instanceof List){
            List<Map> value = (List<Map>) map.get("value");
            if (value==null){
                throw new Exception("not found anyOne field about " + tableName +" on your input param\n" +
                        "you can set @Param(\"value\") Map value in your method params");
            }
            if (value.size()==0){
                throw new Exception("your insert list size = 0");
            }
            sql =  getInsertListSql(value, tableName, field);
        }else {
            Map<String,Object> value = (Map<String, Object>) map.get("value");
            if (value==null||value.isEmpty()){
                throw new Exception("not found anyOne field about " + tableName +" on your input param\n" +
                        "you can set @Param(\"value\") Map value in your method params");
            }
            sql = getInsertSql(value, tableName, field);
        }
        SqlSource sqlSource1 = new StaticSqlSource(ms.getConfiguration(), sql, null);
        System.out.println("\n"+sql+"\n");
        Field classField = MappedStatement.class.getDeclaredField("sqlSource");
        classField.setAccessible(true);
        classField.set(ms, sqlSource1);
        Object res =  invocation.proceed();
        classField.set(ms,sqlSource);
        return res;
    }

    private String getInsertListSql(List<Map> value, String tableName, Map<String, String> field) {
        StringBuffer key = new StringBuffer();
        StringBuffer valueOne = new StringBuffer();
        String keyString = null;
        for (Map<String,Object> map : value) {
            StringBuffer valueTwo = new StringBuffer();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (field.containsKey(entry.getKey())){
                    if (entry.getValue()==null||"".equals(entry.getValue().toString())){ continue; }

                    if (null==keyString) { key.append("`"+entry.getKey()+"`").append(","); }
                    if("varchar".equals(field.get(entry.getKey()))
                            ||"date".equals(field.get(entry.getKey()))
                            || "datetime".equals(field.get(entry.getKey()))
                            || "char".equals(field.get(entry.getKey()))
                            ||"text".equals(field.get(entry.getKey()))
                            ||"longtext".equals(field.get(entry.getKey()))){
                        valueTwo.append("'").append(entry.getValue().toString()).append("',");
                    }else { valueTwo.append(entry.getValue().toString()).append(","); }
                }
            }

            valueOne.append("(").append(valueTwo.toString().substring(0,valueTwo.toString().length()-1)).append("),\n");
            if (keyString==null) {
                keyString = key.toString().substring(0, key.toString().length() - 1);
            }
        }

        return "INSERT INTO " + tableName + " ("+ keyString + " ) \n"
                + " VALUES " + valueOne.toString().substring(0,valueOne.toString().length()-2);
    }


    private String getInsertSql(Map<String, Object> value,  String tableName, Map<String, String> field) {
        List<Map> v = new ArrayList<>();
        v.add(value);
        return getInsertListSql(v,tableName,field);
    }

    private Object update(String tableName, Invocation invocation, MappedStatement ms, Map<String,Object> map,
                          Map<String,String> field) throws Exception {
        SqlSource sqlSource = ms.getSqlSource();
        Map<String,Object> value = (Map<String, Object>) map.get("value");
        Map<String,Object> condition = null;
        if (map.containsKey("condition")){
            condition = (Map<String, Object>) map.get("condition");
        }
        if (value==null){
            throw new Exception("not found update value");
        }
        String updateSQL = getUpdateSql(value,condition,tableName,field);
        SqlSource sqlSource1 = new StaticSqlSource(ms.getConfiguration(), updateSQL, null);
        System.out.println("\n"+updateSQL+"\n");
        Field classField = MappedStatement.class.getDeclaredField("sqlSource");
        classField.setAccessible(true);
        classField.set(ms, sqlSource1);
        Object res =  invocation.proceed();
        classField.set(ms,sqlSource);
        return res;
    }

    private String getUpdateSql(Map<String, Object> value, Map<String, Object> condition, String tableName, Map<String, String> field) throws Exception {
        StringBuffer buffer = new StringBuffer();

        buffer.append("UPDATE ").append(tableName).append(" SET ");

        for (Map.Entry<String, Object> entry : value.entrySet()) {

            if(null==entry.getValue()||"".equals(entry.getValue().toString())){
                continue;
            }
            if (field.get("key").equals(entry.getKey())){
                continue;
            }
            if (field.containsKey(entry.getKey())){
                if("varchar".equals(field.get(entry.getKey()))
                        ||"date".equals(field.get(entry.getKey()))
                        || "datetime".equals(field.get(entry.getKey()))
                        || "char".equals(field.get(entry.getKey()))
                        ||"text".equals(field.get(entry.getKey()))
                        || "longtext".equals(field.get(entry.getKey()))){
                    buffer.append("`"+entry.getKey()+"`").append("=").append("'").append(entry.getValue().toString()).append("',");
                }else {
                    buffer.append("`"+entry.getKey()+"`").append("=").append(entry.getValue().toString()).append(",");
                }
            }
        }
        String one = buffer.toString().substring(0,buffer.toString().length()-1);
        buffer = new StringBuffer();
        buffer.append("\nWHERE 1 = 1 ");
        if (condition != null) {
            boolean f = true;
            for (Map.Entry<String, Object> entry : condition.entrySet()) {
                if (field.containsKey(entry.getKey())) {
                    f = false;
                    if ("varchar".equals(field.get(entry.getKey()))
                            || "date".equals(field.get(entry.getKey()))
                            || "datetime".equals(field.get(entry.getKey()))
                            || "char".equals(field.get(entry.getKey()))
                            || "text".equals(field.get(entry.getKey()))
                            || "longtext".equals(field.get(entry.getKey()))) {
                        buffer.append("\n and ").append(entry.getKey()).append("=").append("'").append(entry.getValue().toString()).append("'\n");
                    } else {
                        buffer.append("\n and ").append(entry.getKey()).append("=").append(entry.getValue().toString()).append("\n");
                    }
                }
            }
            if (f){
                throw new Exception("condition is Empty or not fount this field in your condition");
            }
        }else {
            if (!field.containsKey("key")){
                throw new Exception("not found this table primary key");
            }
            if (!value.containsKey(field.get("key"))){
                throw new Exception("not found condition,you can set @Param(\"condition\") in your method params\n " +
                        "or set PRIMARY KEY in your methods params @Param(\"value\")\n" +
                        "or you can coding sql in your mapperXml");
            }
            buffer.append(" and ").append(field.get("key")).append("=").append("'")
                    .append(value.get(field.get("key")).toString()).append("'\n");
        }
        return one + buffer.toString();
    }

}