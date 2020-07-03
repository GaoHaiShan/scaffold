package org.example.clazz.create.creator;

import org.example.clazz.create.AbstartClassCreator;

import java.io.File;
import java.io.IOException;

public class InsertAndUpdateInterceptorClassCreator extends AbstartClassCreator {

    @Override
    protected File createJavaFile(String basePath, String className) {
        File file = null;
        try {
            file = createJavaFile(basePath, "InsertAndUpdate", "/interceptor/", "Interceptor.java");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected String getPackage(String basePath) {
        return getPackage(basePath, "interceptor");
    }

    @Override
    protected void deflauteEditCode(File file, String url, StringBuffer sb, String className, String page) {
        sb.append("\n").append(
                "\n" ).append(
                "import com.primeton.eos.Application;\n" ).append(
                "import com.primeton.eos.util.SpringProps;\n" ).append(
                "import org.apache.ibatis.builder.StaticSqlSource;\n" ).append(
                "import org.apache.ibatis.executor.Executor;\n" ).append(
                "import org.apache.ibatis.mapping.BoundSql;\n" ).append(
                "import org.apache.ibatis.mapping.MappedStatement;\n" ).append(
                "import org.apache.ibatis.mapping.SqlSource;\n" ).append(
                "import org.apache.ibatis.plugin.*;\n" ).append(
                "\n" ).append(
                "import java.lang.reflect.Field;\n" ).append(
                "import java.sql.*;\n" ).append(
                "import java.util.*;\n" ).append(
                "import java.util.concurrent.ConcurrentHashMap;\n" ).append(
                "\n" ).append(
                "@Intercepts({@Signature(type = Executor.class, method = \"update\",\n" ).append(
                "        args = {MappedStatement.class, Object.class})\n" ).append(
                "})\n" ).append(
                "public class InsertAndUpdateInterceptor implements Interceptor {\n" ).append(
                "\n" ).append(
                "    private Object lock = new Object();\n" ).append(
                "    private static volatile SpringProps springProps = null;\n" ).append(
                "\n" ).append(
                "    public static final ConcurrentHashMap<String, Map<String, String>> TABLEPOOL = new ConcurrentHashMap<>();\n" ).append(
                "\n" ).append(
                "    @Override\n" ).append(
                "    public Object intercept(Invocation invocation) throws Throwable {\n" ).append(
                "        Object[] args = invocation.getArgs();\n" ).append(
                "        MappedStatement ms = (MappedStatement) args[0];\n" ).append(
                "        Object o = null;\n" ).append(
                "        BoundSql boundSql = ms.getBoundSql(args[1]);\n" ).append(
                "\n" ).append(
                "        if(\"\".equals(boundSql.getSql())){\n" ).append(
                "            o =  resetInvocation(invocation, (Map) args[1],ms);\n" ).append(
                "\n" ).append(
                "        }\n" ).append(
                "        if (o==null){\n" ).append(
                "            o = invocation.proceed();\n" ).append(
                "        }\n" ).append(
                "        return o;\n" ).append(
                "    }\n" ).append(
                "\n" ).append(
                "    /**\n" ).append(
                "     *  进行请求处理\n" ).append(
                "     * @param invocation mybatis原生   sql执行器\n" ).append(
                "     * @param param mybatis原生 方法参数\n" ).append(
                "     * @param ms  mybatis原生 配置\n" ).append(
                "     * @return 查询结果\n" ).append(
                "     * @throws Exception 异常\n" ).append(
                "     */\n" ).append(
                "    private Object resetInvocation(Invocation invocation, Map param,MappedStatement ms) throws Exception {\n" ).append(
                "        if (!param.containsKey(\"tableName\")){\n" ).append(
                "            throw new Exception(\"not found tableName\");\n" ).append(
                "        }\n" ).append(
                "        String tableName = param.get(\"tableName\").toString();\n" ).append(
                "\n" ).append(
                "        Map<String,String> field ;\n" ).append(
                "        if (TABLEPOOL.containsKey(tableName)){\n" ).append(
                "            field = TABLEPOOL.get(tableName);\n" ).append(
                "        }else {\n" ).append(
                "            field = getTable(tableName);\n" ).append(
                "            TABLEPOOL.put(tableName,field);\n" ).append(
                "        }\n" ).append(
                "        Object o = null;\n" ).append(
                "        if (\"INSERT\".equals(ms.getSqlCommandType().name())){\n" ).append(
                "            o = insert(tableName,invocation,ms,param,field);\n" ).append(
                "        }\n" ).append(
                "        if (\"UPDATE\".equals(ms.getSqlCommandType().name())){\n" ).append(
                "            o = update(tableName,invocation,ms,param,field);\n" ).append(
                "        }\n" ).append(
                "        if (\"DELETE\".equals(ms.getSqlCommandType().name())){\n" ).append(
                "            o = delete(tableName,invocation,ms,param,field);\n" ).append(
                "        }\n" ).append(
                "        return o;\n" ).append(
                "    }\n" ).append(
                "\n" ).append(
                "    private Object delete(String tableName, Invocation invocation, MappedStatement ms, Map<String,Object> map,\n" ).append(
                "                          Map<String,String> field) throws Exception {\n" ).append(
                "        SqlSource sqlSource =ms.getSqlSource();\n" ).append(
                "        String sql;\n" ).append(
                "        if (map.containsKey(\"key\")){\n" ).append(
                "           String key =  map.get(\"key\").toString();\n" ).append(
                "           sql = getDeleteSql(tableName,key,field);\n" ).append(
                "        }else if (map.containsKey(\"condition\")){\n" ).append(
                "            Map<String,Object> condition = (Map<String,Object>) map.get(\"condition\");\n" ).append(
                "            sql = getDeleteSql(tableName,condition,field);\n" ).append(
                "        }else {\n" ).append(
                "            throw new Exception(\"not found sql and not found Primary Key and not found Condition:\\n\"\n" ).append(
                "                    +\"you can coding sql or add param in baseDao delete method\");\n" ).append(
                "        }\n" ).append(
                "        SqlSource sqlSource1 = new StaticSqlSource(ms.getConfiguration(), sql, null);\n" ).append(
                "        System.out.println(\"\\n\"+sql+\"\\n\");\n" ).append(
                "        Field classField = MappedStatement.class.getDeclaredField(\"sqlSource\");\n" ).append(
                "        classField.setAccessible(true);\n" ).append(
                "        classField.set(ms, sqlSource1);\n" ).append(
                "        Object res =  invocation.proceed();\n" ).append(
                "        classField.set(ms,sqlSource);\n" ).append(
                "        return res;\n" ).append(
                "    }\n" ).append(
                "\n" ).append(
                "    private String getDeleteSql(String tableName, String key, Map<String, String> field) {\n" ).append(
                "        String sql = \"DELETE FROM \" + tableName + \" WHERE 1 = 1 \";\n" ).append(
                "        String keyName = field.get(\"key\");\n" ).append(
                "        if(\"varchar\".equals(keyName)\n" ).append(
                "                ||\"date\".equals(keyName)\n" ).append(
                "                || \"datetime\".equals(keyName)\n" ).append(
                "                || \"char\".equals(keyName)\n" ).append(
                "                ||\"text\".equals(keyName)\n" ).append(
                "                ||\"longtext\".equals(keyName)){\n" ).append(
                "            sql += \" and `\"+keyName +\"` = '\" + key + \"'\";\n" ).append(
                "        }else { sql += \" and `\"+keyName +\"` = \" + key;}\n" ).append(
                "\n" ).append(
                "        return sql;\n" ).append(
                "    }\n" ).append(
                "    private String getDeleteSql(String tableName, Map<String,Object> condition, Map<String, String> field) {\n" ).append(
                "        String sql = \"DELETE FROM \" + tableName + \" WHERE 1 = 1 \";\n" ).append(
                "        for (Map.Entry<String, Object> entry : condition.entrySet()) {\n" ).append(
                "            if(null==entry.getValue()||\"\".equals(entry.getValue().toString())){\n" ).append(
                "                continue;\n" ).append(
                "            }\n" ).append(
                "            if (!field.containsKey(entry.getKey())){\n" ).append(
                "                continue;\n" ).append(
                "            }\n" ).append(
                "            if(\"varchar\".equals(field.get(entry.getKey()))\n" ).append(
                "                    ||\"date\".equals(field.get(entry.getKey()))\n" ).append(
                "                    || \"datetime\".equals(field.get(entry.getKey()))\n" ).append(
                "                    || \"char\".equals(field.get(entry.getKey()))\n" ).append(
                "                    ||\"text\".equals(field.get(entry.getKey()))\n" ).append(
                "                    ||\"longtext\".equals(field.get(entry.getKey()))){\n" ).append(
                "                sql = sql + \"\\n and `\" + entry.getKey()+ \"` = '\" + entry.getValue().toString() + \" ' \";\n" ).append(
                "            }else {\n" ).append(
                "                sql = sql + \"\\n and `\" + entry.getKey()+ \"` = \" + entry.getValue().toString() + \" \";\n" ).append(
                "            }\n" ).append(
                "        }\n" +
                "        return sql;\n" ).append(
                "    }\n" ).append(
                "\n" ).append(
                "    private Object insert(String tableName, Invocation invocation, MappedStatement ms, Map<String,Object> map,\n" ).append(
                "                          Map<String,String> field) throws Exception {\n" ).append(
                "        SqlSource sqlSource =ms.getSqlSource();\n" ).append(
                "        String sql;\n" ).append(
                "        if (map.get(\"value\") instanceof List){\n" ).append(
                "            List<Map> value = (List<Map>) map.get(\"value\");\n" ).append(
                "            if (value==null||value.size()==0){\n" ).append(
                "                throw new Exception(\"not found anyOne field about \" + tableName +\" on your input param\\n\" +\n" ).append(
                "                        \"you can set @Param(\\\"value\\\") Map value in your method params\");\n" ).append(
                "            }\n" ).append(
                "            sql =  getInsertListSql(value, tableName, field);\n" ).append(
                "        }else {\n" ).append(
                "            Map<String,Object> value = (Map<String, Object>) map.get(\"value\");\n" ).append(
                "            if (value==null||value.isEmpty()){\n" ).append(
                "                throw new Exception(\"not found anyOne field about \" + tableName +\" on your input param\\n\" +\n" ).append(
                "                        \"you can set @Param(\\\"value\\\") Map value in your method params\");\n" ).append(
                "            }\n" ).append(
                "            sql = getInsertSql(value, tableName, field);\n" ).append(
                "        }\n" ).append(
                "        SqlSource sqlSource1 = new StaticSqlSource(ms.getConfiguration(), sql, null);\n" ).append(
                "        System.out.println(\"\\n\"+sql+\"\\n\");\n" ).append(
                "        Field classField = MappedStatement.class.getDeclaredField(\"sqlSource\");\n" ).append(
                "        classField.setAccessible(true);\n" ).append(
                "        classField.set(ms, sqlSource1);\n" ).append(
                "        Object res =  invocation.proceed();\n" ).append(
                "        classField.set(ms,sqlSource);\n" ).append(
                "        return res;\n" ).append(
                "    }\n" ).append(
                "\n" ).append(
                "    private String getInsertListSql(List<Map> value, String tableName, Map<String, String> field) {\n" ).append(
                "        StringBuffer key = new StringBuffer();\n" ).append(
                "        StringBuffer valueOne = new StringBuffer();\n" ).append(
                "        String keyString = null;\n" ).append(
                "        for (Map<String,Object> map : value) {\n" ).append(
                "            StringBuffer valueTwo = new StringBuffer();\n" ).append(
                "            for (Map.Entry<String, Object> entry : map.entrySet()) {\n" ).append(
                "                if (field.containsKey(entry.getKey())){\n" ).append(
                "                    if (entry.getValue()==null||\"\".equals(entry.getValue().toString())){ continue; }\n" ).append(
                "\n" ).append(
                "                    if (null==keyString) { key.append(\"`\"+entry.getKey()+\"`\").append(\",\"); }\n" ).append(
                "                    if(\"varchar\".equals(field.get(entry.getKey()))\n" ).append(
                "                            ||\"date\".equals(field.get(entry.getKey()))\n" ).append(
                "                            || \"datetime\".equals(field.get(entry.getKey()))\n" ).append(
                "                            || \"char\".equals(field.get(entry.getKey()))\n" ).append(
                "                            ||\"text\".equals(field.get(entry.getKey()))\n" ).append(
                "                            ||\"longtext\".equals(field.get(entry.getKey()))){\n" ).append(
                "                        valueTwo.append(\"'\").append(entry.getValue().toString()).append(\"',\");\n" ).append(
                "                    }else { valueTwo.append(entry.getValue().toString()).append(\",\"); }\n" ).append(
                "                }\n" ).append(
                "            }\n" ).append(
                "\n" ).append(
                "            valueOne.append(\"(\").append(valueTwo.toString().substring(0,valueTwo.toString().length()-1)).append(\"),\\n\");\n" ).append(
                "            if (keyString==null) {\n" ).append(
                "                keyString = key.toString().substring(0, key.toString().length() - 1);\n" ).append(
                "            }\n" ).append(
                "        }\n" ).append(
                "\n" ).append(
                "        return \"INSERT INTO \" + tableName + \" (\"+ keyString + \" ) \\n\"\n" ).append(
                "                + \" VALUES \" + valueOne.toString().substring(0,valueOne.toString().length()-2);\n" ).append(
                "    }\n" ).append(
                "\n" ).append(
                "\n" ).append(
                "    private String getInsertSql(Map<String, Object> value,  String tableName, Map<String, String> field) {\n" ).append(
                "        List<Map> v = new ArrayList<>();\n" ).append(
                "        v.add(value);\n" ).append(
                "        return getInsertListSql(v,tableName,field);\n" ).append(
                "    }\n" ).append(
                "\n" ).append(
                "    private Object update(String tableName, Invocation invocation, MappedStatement ms, Map<String,Object> map,\n" ).append(
                "                          Map<String,String> field) throws Exception {\n" ).append(
                "        SqlSource sqlSource = ms.getSqlSource();\n" ).append(
                "        Map<String,Object> value = (Map<String, Object>) map.get(\"value\");\n" ).append(
                "        Map<String,Object> condition = null;\n" ).append(
                "        if (map.containsKey(\"condition\")){\n" ).append(
                "            condition = (Map<String, Object>) map.get(\"condition\");\n" ).append(
                "        }\n" ).append(
                "        if (value==null){\n" ).append(
                "            throw new Exception(\"not found update value\");\n" ).append(
                "        }\n" ).append(
                "        String updateSQL = getUpdateSql(value,condition,tableName,field);\n" ).append(
                "        SqlSource sqlSource1 = new StaticSqlSource(ms.getConfiguration(), updateSQL, null);\n" ).append(
                "        System.out.println(\"\\n\"+updateSQL+\"\\n\");\n" ).append(
                "        Field classField = MappedStatement.class.getDeclaredField(\"sqlSource\");\n" ).append(
                "        classField.setAccessible(true);\n" ).append(
                "        classField.set(ms, sqlSource1);\n" ).append(
                "        Object res =  invocation.proceed();\n" ).append(
                "        classField.set(ms,sqlSource);\n" ).append(
                "        return res;\n" ).append(
                "    }\n" ).append(
                "\n" ).append(
                "    private String getUpdateSql(Map<String, Object> value, Map<String, Object> condition, String tableName, Map<String, String> field) throws Exception {\n" ).append(
                "        StringBuffer buffer = new StringBuffer();\n" ).append(
                "\n" ).append(
                "        buffer.append(\"UPDATE \").append(tableName).append(\" SET \");\n" ).append(
                "\n" ).append(
                "        for (Map.Entry<String, Object> entry : value.entrySet()) {\n" ).append(
                "\n" ).append(
                "            if(null==entry.getValue()||\"\".equals(entry.getValue().toString())){\n" ).append(
                "                continue;\n" ).append(
                "            }\n" ).append(
                "           if (field.get(\"key\").equals(entry.getKey())){\n" ).append(
                "               continue;\n" ).append(
                "           }\n" ).append(
                "            if (field.containsKey(entry.getKey())){\n" ).append(
                "                if(\"varchar\".equals(field.get(entry.getKey()))\n" ).append(
                "                        ||\"date\".equals(field.get(entry.getKey()))\n" ).append(
                "                        || \"datetime\".equals(field.get(entry.getKey()))\n" ).append(
                "                        || \"char\".equals(field.get(entry.getKey()))\n" ).append(
                "                        ||\"text\".equals(field.get(entry.getKey()))\n" ).append(
                "                        || \"longtext\".equals(field.get(entry.getKey()))){\n" ).append(
                "                    buffer.append(\"`\"+entry.getKey()+\"`\").append(\"=\").append(\"'\").append(entry.getValue().toString()).append(\"',\");\n" ).append(
                "                }else {\n" ).append(
                "                    buffer.append(\"`\"+entry.getKey()+\"`\").append(\"=\").append(entry.getValue().toString()).append(\",\");\n" ).append(
                "                }\n" ).append(
                "            }\n" ).append(
                "        }\n" ).append(
                "        String one = buffer.toString().substring(0,buffer.toString().length()-1);\n" ).append(
                "        buffer = new StringBuffer();\n" ).append(
                "        buffer.append(\"\\nWHERE 1 = 1 \");\n" ).append(
                "        if (condition != null) {\n" ).append(
                "            for (Map.Entry<String, Object> entry : condition.entrySet()) {\n" ).append(
                "                if (field.containsKey(entry.getKey())) {\n" ).append(
                "                    if (\"varchar\".equals(field.get(entry.getKey()))\n" ).append(
                "                            || \"date\".equals(field.get(entry.getKey()))\n" ).append(
                "                            || \"datetime\".equals(field.get(entry.getKey()))\n" ).append(
                "                            || \"char\".equals(field.get(entry.getKey()))\n" ).append(
                "                            || \"text\".equals(field.get(entry.getKey()))\n" ).append(
                "                            || \"longtext\".equals(field.get(entry.getKey()))) {\n" ).append(
                "                        buffer.append(\"\\n and \").append(entry.getKey()).append(\"=\").append(\"'\").append(entry.getValue().toString()).append(\"'\\n\");\n" ).append(
                "                    } else {\n" ).append(
                "                        buffer.append(\"\\n and \").append(entry.getKey()).append(\"=\").append(entry.getValue().toString()).append(\"\\n\");\n" ).append(
                "                    }\n" ).append(
                "                }\n" ).append(
                "            }\n" ).append(
                "        }else {\n" ).append(
                "            if (!value.containsKey(field.get(\"key\"))){\n" ).append(
                "                throw new Exception(\"not found condition,you can set @Param(\\\"condition\\\") in your method params\\n \" +\n" ).append(
                "                        \"or set PRIMARY KEY in your methods params @Param(\\\"value\\\")\\n\" +\n" ).append(
                "                        \"or you can coding sql in your mapperXml\");\n" ).append(
                "            }\n" ).append(
                "            buffer.append(\" and \").append(field.get(\"key\")).append(\"=\").append(\"'\")\n" ).append(
                "                    .append(value.get(field.get(\"key\")).toString()).append(\"'\\n\");\n" ).append(
                "        }\n" ).append(
                "        return one + buffer.toString();\n" ).append(
                "    }\n" ).append(
                "\n" ).append(
                "\n" ).append(
                "    private Map<String, String> getTable( String tableName) throws Exception {\n" ).append(
                "        if (springProps==null){\n" ).append(
                "            synchronized (lock){\n" ).append(
                "                if (springProps==null){\n" ).append(
                "                    springProps = (SpringProps) Application.CONTEXT.getBean(\"springProps\");\n" ).append(
                "                }\n" ).append(
                "            }\n" ).append(
                "        }\n" ).append(
                "        Map<String,String> dataSource = springProps.getDatasource();\n" ).append(
                "\n" ).append(
                "        dataSource.put(\"dataBaseName\",dataSource.get(\"url\")\n" ).append(
                "                .replaceAll(\"/+\",\"/\")\n" ).append(
                "                .split(\"/\")[2]\n" ).append(
                "                .replaceAll(\"\\\\?.*\",\"\"));\n" ).append(
                "\n" ).append(
                "        String sql = \"select column_name,data_type,column_comment,column_key,column_default \" +\n" ).append(
                "                \"from information_schema.columns \" +\n" ).append(
                "                \"where table_name='\"+tableName+\"' \" +\n" ).append(
                "                \"and table_schema = '\"+dataSource.get(\"dataBaseName\")+\"' \" +\n" ).append(
                "                \"order by ORDINAL_POSITION\";\n" ).append(
                "        System.out.println(\"\\n\");\n" ).append(
                "        System.out.println(sql);\n" ).append(
                "        System.out.println(\"\\n\");\n" ).append(
                "        Connection conn = null;\n" ).append(
                "        Statement stmt = null;\n" ).append(
                "        Map<String,String> field = new HashMap<>();\n" ).append(
                "\n" ).append(
                "        try {\n" ).append(
                "            Class.forName(dataSource.get(\"driver-class-name\"));\n" ).append(
                "            conn = DriverManager.getConnection(dataSource.get(\"url\"),\n" ).append(
                "                    dataSource.get(\"username\"), dataSource.get(\"password\"));\n").append(
                "\n" ).append(
                "            // 执行查询\n" ).append(
                "            stmt = conn.createStatement();\n" ).append(
                "            ResultSet rs = stmt.executeQuery(sql);\n" ).append(
                "            while (rs.next()) {\n" ).append(
                "                if (\"PRI\".equals(rs.getString(\"column_key\"))){\n" ).append(
                "                    field.put(\"key\",rs.getString(\"column_name\"));\n" ).append(
                "                }\n" ).append(
                "                field.put(rs.getString(\"column_name\"),rs.getString(\"data_type\"));\n" ).append(
                "            }\n" ).append(
                "            rs.close();\n" ).append(
                "            stmt.close();\n" ).append(
                "            conn.close();\n" ).append(
                "        }catch (Exception e){\n" ).append(
                "            e.printStackTrace();\n" ).append(
                "        } finally {\n" ).append(
                "            try {\n" ).append(
                "                if (stmt != null) stmt.close();\n" ).append(
                "                if (conn != null) conn.close();\n" ).append(
                "            } catch (SQLException e) {\n" ).append(
                "                e.printStackTrace();\n" ).append(
                "            }\n" ).append(
                "        }\n" ).append(
                "        return field;\n" ).append(
                "    }\n" ).append(
                "\n" ).append(
                "}");
    }

    @Override
    protected void restEditCode(File file, String url, StringBuffer sb, String className, String page) {
        deflauteEditCode(file, url, sb, className, page);
    }
}
