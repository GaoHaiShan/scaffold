package org.example.clazz.create.creator;

import org.example.clazz.create.AbstartClassCreator;

import java.io.File;
import java.io.IOException;

public class DaoClassCreator extends AbstartClassCreator implements Runnable {


    @Override
    protected File createJavaFile(String basePath, String className) {
        File file = null;
        try {
            file = createJavaFile(basePath,className,"/dao/I","Dao.java");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected String getPackage(String basePath) {
        return getPackage(basePath,"dao");
    }

    @Override
    protected void deflauteEditCode(File file, String url, StringBuffer sb, String className, String page) {
        sb.append("\n\n" +
                "import org.apache.ibatis.annotations.Mapper;\n" +
                "import org.apache.ibatis.annotations.Param;\n\n" +
                "import java.util.List;\n" +
                "import java.util.Map;\n\n");
        sb.append("@Mapper\n");
        sb.append("public interface I"+className+"Dao {\n\n");
        sb.append("\tList<Map> query"+className+"(@Param(\"param\") Map param);\n\n");
        sb.append("\tint update"+className+"ByCondition(@Param(\"tableName\") String tableName,@Param(\"condition\") Map condition,@Param(\"value\") Map value);\n\n");
        sb.append("\tint update"+className+"ByKey(@Param(\"tableName\") String tableName,@Param(\"value\") Map value);\n\n");
        sb.append("\tint add"+className+"One(@Param(\"tableName\") String tableName,@Param(\"value\") Map value);\n\n");
        sb.append("\tint add"+className+"List(@Param(\"tableName\") String tableName,@Param(\"value\") List<Map> value);\n\n");
        sb.append("\tint delete"+className+"(@Param(\"param\") Map param);\n\n");
        sb.append("}");
    }

    @Override
    protected void restEditCode(File file, String url, StringBuffer sb, String className, String page) {
        deflauteEditCode(file,url,sb,className,page);
    }
}
