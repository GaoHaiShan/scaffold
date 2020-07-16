package org.example.clazz.create.creator.mapper;

import org.example.clazz.create.AbstartClassCreator;

import java.io.File;
import java.io.IOException;

public class MyBatisMapperClassCreator extends AbstartClassCreator {
    private String path;

    @Override
    protected File createJavaFile(String basePath, String className) {
        this.path = basePath;
        basePath = basePath.replaceAll("/java.*", "");
        basePath += "/resources/mapper/";
        File file = null;
        try {
            file = createJavaFile(basePath, className, "", "Mapper.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected String getPackage(String basePath) {
        return "";
    }

    @Override
    protected void deflauteEditCode(File file, String url, StringBuffer sb, String className, String page) {
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
                "<mapper namespace=\"" + getPackage(path, "dao").replaceAll(";", "")
                .replaceAll("package ", "") + ".I" + className + "Dao\">\n" +
                "    <!--查询条件  like 用 $ 其他用 # 例如下面模板-->\n"+
                "       <select id=\"query" + className + "\" resultType=\"map\">\n" +
                "           select * from " + getThisClassDefinition().getTableName() + "\n" +
                "           where flag = 1\n" +
                "           <if test=\"param.condition!=null\">\n" +
                "               and " + getThisClassDefinition().getPrimaryKey() + " = '#{param.condition}'\n" +
                "           </if>\n" +
                "       </select>\n" +
                "</mapper>");
    }

    @Override
    protected void restEditCode(File file, String url, StringBuffer sb, String className, String page) {
        deflauteEditCode(file, url, sb, className, page);
    }
}
