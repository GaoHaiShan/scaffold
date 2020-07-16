package org.example.clazz.create.creator.dao;

import org.example.clazz.create.AbstartClassCreator;

import java.io.File;
import java.io.IOException;

public class BaseDaoClassCreator extends AbstartClassCreator implements Runnable {


    @Override
    protected File createJavaFile(String basePath, String className) {
        File file = null;
        try {
            file = createJavaFile(basePath, "Base", "/dao/I", "Dao.java");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected String getPackage(String basePath) {
        return getPackage(basePath, "dao");
    }

    @Override
    protected void deflauteEditCode(File file, String url, StringBuffer sb, String className, String page) {
        sb.append("\n\n" +
                "import org.apache.ibatis.annotations.*;\n\n" +
                "import java.util.List;\n" +
                "import java.util.Map;\n\n");
        sb.append("@Mapper\n");
        sb.append("public interface IBaseDao {\n\n");

        sb.append("\t@Update(\"\")");
        sb.append("\tint updateByCondition(@Param(\"tableName\") String tableName,@Param(\"condition\") Map condition,@Param(\"value\") Map value);\n\n");
        sb.append("\t@Update(\"\")");
        sb.append("\tint updateByKey(@Param(\"tableName\") String tableName,@Param(\"value\") Map value);\n\n");
        sb.append("\t@Insert(\"\")");
        sb.append("\tint insert(@Param(\"tableName\") String tableName,@Param(\"value\") Map value);\n\n");
        sb.append("\t@Insert(\"\")");
        sb.append("\tint insertList(@Param(\"tableName\") String tableName,@Param(\"value\") List<Map> value);\n\n");
        sb.append("\t@Delete(\"\")");
        sb.append("\tint deleteByKey(@Param(\"tableName\") String tableName,@Param(\"key\") String key);\n\n");
        sb.append("\t@Delete(\"\")");
        sb.append("\tint deleteByCondition(@Param(\"tableName\") String tableName,@Param(\"condition\") String key);\n\n");
        sb.append("\n}");
    }

    @Override
    protected void restEditCode(File file, String url, StringBuffer sb, String className, String page) {
        deflauteEditCode(file, url, sb, className, page);
    }
}
