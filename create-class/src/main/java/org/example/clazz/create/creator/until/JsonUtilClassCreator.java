package org.example.clazz.create.creator.until;

import org.example.clazz.create.AbstartClassCreator;

import java.io.File;
import java.io.IOException;

public class JsonUtilClassCreator extends AbstartClassCreator {
    @Override
    protected File createJavaFile(String basePath, String className) {
        File file = null;
        try {
            file = createJavaFile(basePath, "JsonUtil", "/util/", ".java");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected String getPackage(String basePath) {
        return super.getPackage(basePath, "util");
    }

    @Override
    protected void deflauteEditCode(File file, String url, StringBuffer sb, String className, String page) {
        sb.append("\n\nimport com.alibaba.fastjson.JSON;\n" +
                "import com.alibaba.fastjson.serializer.SerializerFeature;\n").append(
                "\n").append(
                "public class JsonUtil {\n")
                .append("    public static String toJSON(Object jsonText) {\n" +
                "        return JSON.toJSONString(jsonText,\n" +
                "                SerializerFeature.WriteDateUseDateFormat);\n" +
                "    }").append(
                "}");
    }

    @Override
    protected void restEditCode(File file, String url, StringBuffer sb, String className, String page) {
        deflauteEditCode(file, url, sb, className, page);
    }
}
