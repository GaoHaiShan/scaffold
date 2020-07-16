package org.example.clazz.create.creator.config;

import org.example.clazz.create.AbstartClassCreator;

import java.io.File;
import java.io.IOException;

public class MyBatisConfingClassCreator extends AbstartClassCreator {
    private String path;

    @Override
    protected File createJavaFile(String basePath, String className) {
        this.path = basePath;
        basePath = basePath.replaceAll("/java.*", "");
        basePath += "/resources/";
        File file = null;
        try {
            file = createJavaFile(basePath, "", "mybatis", ".xml");
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
        String interRcptor = path.split("/java/")[1];
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n")
                .append("<!DOCTYPE configuration PUBLIC \"-//mybatis.org//DTD Config 3.0//EN\"\n")
                .append("        \"http://mybatis.org/dtd/mybatis-3-config.dtd\">\n")
                .append("<configuration>\n")
                .append("    <plugins>\n")
                .append("        <plugin interceptor=\"").append(interRcptor.replaceAll("/", "."))
                .append(".interceptor.InsertAndUpdateInterceptor\">\n")
                .append("        </plugin>\n" +
                        "         <plugin interceptor=\"com.github.pagehelper.PageInterceptor\"></plugin>\n")
                .append("    </plugins>\n")
                .append("</configuration>");
    }

    @Override
    protected void restEditCode(File file, String url, StringBuffer sb, String className, String page) {
        deflauteEditCode(file, url, sb, className, page);
    }
}
