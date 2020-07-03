package com.haishan.createclassv2.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBC {

    public static List<Map<String, String>> getTable(Map<String,String> dataSource) {
        String sql = "select a.table_name,a.`TABLE_COMMENT`,b.`COLUMN_NAME`,b.`COLUMN_COMMENT`,b.`DATA_TYPE`,b.`column_key`\n" +
                "from information_schema.tables a join information_schema.columns b on a.`TABLE_NAME` = b.`TABLE_NAME`\n" +
                "where a.table_schema='"+dataSource.get("database")+"' \n" +
                "and b.table_schema='"+dataSource.get("database")+"'" +
                "and a.table_type='base table'\n";
        System.out.println("\n");
        System.out.println(sql);
        System.out.println("\n");
        Connection conn = null;
        Statement stmt = null;
        List<Map<String,String>> res = new ArrayList<>();

        try {
            Class.forName(dataSource.get("driverclass"));
            conn = DriverManager.getConnection(dataSource.get("url")
                    , dataSource.get("username"), dataSource.get("password"));

            // 执行查询
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Map<String,String> resOne = new HashMap<>();
                if ("PRI".equals(rs.getString("column_key"))){
                    resOne.put("key",rs.getString("column_name"));
                }

                resOne.put("table_name",rs.getString("table_name"));
                resOne.put("TABLE_COMMENT",rs.getString("TABLE_COMMENT"));
                resOne.put("COLUMN_NAME",rs.getString("COLUMN_NAME"));
                resOne.put("COLUMN_COMMENT",rs.getString("COLUMN_COMMENT"));
                resOne.put("DATA_TYPE",rs.getString("DATA_TYPE"));
                res.add(resOne);
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
        return res;
    }
}
