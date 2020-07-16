package org.example.clazz.create.creator.until;

import org.example.clazz.create.AbstartClassCreator;

import java.io.File;
import java.io.IOException;

public class FilePropsClassCreator extends AbstartClassCreator {
    @Override
    protected File createJavaFile(String basePath, String className) {
        File file = null;
        try {
            file = createJavaFile(basePath, "FileProps", "/util/", ".java");
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
        sb.append("import org.springframework.boot.context.properties.ConfigurationProperties;\n").append(
                "import org.springframework.stereotype.Component;\n").append(
                "\n").append(
                "import java.util.Map;\n").append(
                "\n").append(
                "@Component\n").append(
                "@ConfigurationProperties(prefix = \"upload\")\n").append(
                "public class FileProps {\n").append(
                "\n").append(
                "    // 上传文件存放目录\n").append(
                "    private Map<String,String> file;\n").append(
                "\n").append(
                "    public Map<String, String> getFile() {\n").append(
                "        return file;\n").append(
                "    }\n").append(
                "\n").append(
                "    public void setFile(Map<String, String> file) {\n").append(
                "        this.file = file;\n").append(
                "    }\n").append(
                "}");
    }

    @Override
    protected void restEditCode(File file, String url, StringBuffer sb, String className, String page) {
        deflauteEditCode(file,url,sb,className,page);
    }
}
