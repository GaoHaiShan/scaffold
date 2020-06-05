package org.example.clazz.create.creator;

import org.example.clazz.create.AbstartClassCreator;

import java.io.File;
import java.io.IOException;

public class ServiceClassCreator  extends AbstartClassCreator implements Runnable {

    @Override
    protected File createJavaFile(String basePath, String className) {
        File file = null;
        try {
            file = createJavaFile(basePath,className,"/service/I","Service.java");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected String getPackage(String basePath) {
        return super.getPackage(basePath,"service");
    }

    @Override
    protected void deflauteEditCode(File file, String url, StringBuffer sb, String className, String page) {
        String p = page.replaceAll("package ","")
                .replaceAll("service","")
                .replaceAll(";","");
        sb.append("\n\nimport javax.servlet.http.HttpServletRequest;\n" +
                "import com.alibaba.fastjson.JSONObject;\n" +
                "\r\n");

        sb.append("public interface I" + className + "Service{\r\n\n");

        sb.append("\tString query" + className + "(HttpServletRequest request, JSONObject param);\r\n\n" );

        sb.append("\tString update" + className + "ByCondition(HttpServletRequest request, JSONObject param);\r\n\n" );

        sb.append("\tString update" + className + "ByKey(HttpServletRequest request, JSONObject param);\r\n\n" );

        sb.append("\tString add" + className + "One(HttpServletRequest request, JSONObject param);\r\n\n" );

        sb.append("\tString add" + className + "List(HttpServletRequest request, JSONObject param);\r\n\n" );

        sb.append("\tString delete" + className + "(HttpServletRequest request, JSONObject param);\r\n\n" );


        sb.append("}");
    }

    @Override
    protected void restEditCode(File file, String url, StringBuffer sb, String className, String page) {
        deflauteEditCode(file,url,sb,className,page);
    }

}
