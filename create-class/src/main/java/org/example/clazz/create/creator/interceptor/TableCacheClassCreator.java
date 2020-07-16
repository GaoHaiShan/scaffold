package org.example.clazz.create.creator.interceptor;

import org.example.clazz.create.AbstartClassCreator;

import java.io.File;
import java.io.IOException;

public class TableCacheClassCreator  extends AbstartClassCreator {

    @Override
    protected File createJavaFile(String basePath, String className) {
        File file = null;
        try {
            file = createJavaFile(basePath, "TableCache", "/interceptor/", ".java");
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
        String[] startClass = getThisClassDefinition().getStartClass().split("\\.");
        sb.append("\n").append(
                "\n" ).append(
                        "import " + super.getThisClassDefinition().getStartClass()+";\n"+
                        "import " + page.replaceAll("package ","")
                                .replaceAll(";","")
                                .replaceAll("\\.interceptor.*",".util.SpringProps;")+
                        "import java.sql.*;\n" +
                        "import java.util.HashMap;\n" +
                        "import java.util.Map;\n" +
                        "import java.util.concurrent.*;\n" +
                        "\n" +
                        "public class TableCache {\n" +
                        "\n" +
                        "    public final ConcurrentHashMap<String, Map<String, String>> TABLEPOOL = new ConcurrentHashMap<>();\n" +
                        "\n" +
                        "    //单例接口\n" +
                        "    private static final TableCache INSTANCE = new TableCache();\n" +
                        "\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 获取实例  启动一分钟一次自动刷新任务 防止未用 redis 缓存问题。应用 redis 需更改。\n" +
                        "     */\n" +
                        "    public static TableCache getInstance(){\n" +
                        "        return INSTANCE;\n" +
                        "    }\n" +
                        "\n" +
                        "\n" +
                        "    /**\n" +
                        "     * 表未被缓存 或 手动更新\n" +
                        "     */\n" +
                        "    public void update( String tableName)  {\n" +
                        "\n" +
                        "        Map<String,String> dataSource = "+startClass[startClass.length-1]+".CONTEXT.getBean(SpringProps.class).getDatasource();\n" +
                        "\n" +
                        "        dataSource.put(\"dataBaseName\",dataSource.get(\"url\")\n" +
                        "                .replaceAll(\"/+\",\"/\")\n" +
                        "                .split(\"/\")[2]\n" +
                        "                .replaceAll(\"\\\\?.*\",\"\"));\n" +
                        "\n" +
                        "        String sql = \"select column_name,data_type,column_comment,column_key,column_default \" +\n" +
                        "                \"from information_schema.columns \" +\n" +
                        "                \"where table_name='\"+tableName+\"' \" +\n" +
                        "                \"and table_schema = '\"+dataSource.get(\"dataBaseName\")+\"' \" +\n" +
                        "                \"order by ORDINAL_POSITION\";\n" +
                        "        System.out.println(\"\\n\");\n" +
                        "        System.out.println(sql);\n" +
                        "        System.out.println(\"\\n\");\n" +
                        "        Connection conn = null;\n" +
                        "        Statement stmt = null;\n" +
                        "        Map<String,String> field = new HashMap<>();\n" +
                        "\n" +
                        "        try {\n" +
                        "            Class.forName(dataSource.get(\"driver-class-name\"));\n" +
                        "            conn = DriverManager.getConnection(dataSource.get(\"url\"),\n" +
                        "                    dataSource.get(\"username\"), dataSource.get(\"password\"));\n" +
                        "\n" +
                        "            // 执行查询\n" +
                        "            stmt = conn.createStatement();\n" +
                        "            ResultSet rs = stmt.executeQuery(sql);\n" +
                        "            while (rs.next()) {\n" +
                        "                if (\"PRI\".equals(rs.getString(\"column_key\"))){\n" +
                        "                    field.put(\"key\",rs.getString(\"column_name\"));\n" +
                        "                }\n" +
                        "                field.put(rs.getString(\"column_name\"),rs.getString(\"data_type\"));\n" +
                        "            }\n" +
                        "            rs.close();\n" +
                        "            stmt.close();\n" +
                        "            conn.close();\n" +
                        "        }catch (Exception e){\n" +
                        "            e.printStackTrace();\n" +
                        "        } finally {\n" +
                        "            try {\n" +
                        "                if (stmt != null) stmt.close();\n" +
                        "                if (conn != null) conn.close();\n" +
                        "            } catch (SQLException e) {\n" +
                        "                e.printStackTrace();\n" +
                        "            }\n" +
                        "        }\n" +
                        "        TABLEPOOL.put(tableName,field);\n" +
                        "    }\n" +
                        "}      ");
    }

    @Override
    protected void restEditCode(File file, String url, StringBuffer sb, String className, String page) {
        deflauteEditCode(file, url, sb, className, page);
    }
}
