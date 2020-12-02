package com.haishan.interceptor;

import com.haishan.SqlCreatorApplication;
import com.haishan.util.SpringProps;import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class TableCache {

    public final ConcurrentHashMap<String, Map<String, String>> TABLEPOOL = new ConcurrentHashMap<>();

    //单例接口
    private static final TableCache INSTANCE = new TableCache();


    /**
     * 获取实例  启动一分钟一次自动刷新任务 防止未用 redis 缓存问题。应用 redis 需更改。
     */
    public static TableCache getInstance(){
        return INSTANCE;
    }


    /**
     * 表未被缓存 或 手动更新
     */
    public void update( String tableName)  {

        Map<String,String> dataSource = SqlCreatorApplication.CONTEXT.getBean(SpringProps.class).getDatasource();

        dataSource.put("dataBaseName",dataSource.get("url")
                .replaceAll("/+","/")
                .split("/")[2]
                .replaceAll("\\?.*",""));

        String sql = "select column_name,data_type,column_comment,column_key,column_default " +
                "from information_schema.columns " +
                "where table_name='"+tableName+"' " +
                "and table_schema = '"+dataSource.get("dataBaseName")+"' " +
                "order by ORDINAL_POSITION";
        System.out.println("\n");
        System.out.println(sql);
        System.out.println("\n");
        Connection conn = null;
        Statement stmt = null;
        Map<String,String> field = new HashMap<>();

        try {
            Class.forName(dataSource.get("driver-class-name"));
            conn = DriverManager.getConnection(dataSource.get("url"),
                    dataSource.get("username"), dataSource.get("password"));

            // 执行查询
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if ("PRI".equals(rs.getString("column_key"))){
                    field.put("key",rs.getString("column_name"));
                }
                field.put(rs.getString("column_name"),rs.getString("data_type"));
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        TABLEPOOL.put(tableName,field);
    }
}      