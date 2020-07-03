package org.example.clazz.create.creator;

import org.example.clazz.create.AbstartClassCreator;

import java.io.File;
import java.io.IOException;

public class GeneratorIDUtilClassCreator extends AbstartClassCreator {
    @Override
    protected File createJavaFile(String basePath, String className) {
        File file = null;
        try {
            file = createJavaFile(basePath, "GeneratorIDUtil", "/util/", ".java");
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
        sb.append("\n\nimport java.text.NumberFormat;\n").append(
                "import java.text.SimpleDateFormat;\n").append(
                "import java.util.Date;\n").append(
                "\n").append(
                "public class GeneratorIDUtil {\n").append(
                "    public static volatile int idnum = 1;\n").append(
                "\n").append(
                "    /*\n").append(
                "     * 生成常用ID编码\n").append(
                "     */\n").append(
                "    public synchronized static String generatorId() {\n").append(
                "        if (idnum >= 999) {\n").append(
                "            idnum = 1;\n").append(
                "        }\n").append(
                "        java.text.DateFormat format = new java.text.SimpleDateFormat(\"yyMMddHHmmss\");\n").append(
                "        NumberFormat nf = NumberFormat.getInstance();\n").append(
                "        nf.setGroupingUsed(false);\n").append(
                "        nf.setMaximumIntegerDigits(4);\n").append(
                "        nf.setMinimumIntegerDigits(4);\n").append(
                "        String datestr = format.format(new Date());\n").append(
                "        return (datestr + nf.format(idnum++));\n").append(
                "    }\n").append(
                "\n").append(
                "    public static String getNowDateStr() {\n").append(
                "        Date currentTime = new Date();\n").append(
                "        SimpleDateFormat formatter = new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\");\n").append(
                "        return formatter.format(currentTime);\n").append(
                "    }\n").append(
                "}");
    }

    @Override
    protected void restEditCode(File file, String url, StringBuffer sb, String className, String page) {
        deflauteEditCode(file, url, sb, className, page);
    }
}
