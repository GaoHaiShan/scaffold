package org.example.clazz.create.creator;

import org.example.clazz.create.AbstartClassCreator;

import java.io.File;
import java.io.IOException;

public class SpringPropsClassCreator extends AbstartClassCreator {

    @Override
    protected File createJavaFile(String basePath, String className) {
        File file = null;
        try {
            file = createJavaFile(basePath,"SpringProps","/util/",".java");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected String getPackage(String basePath) {
        return super.getPackage(basePath,"util");
    }

    @Override
    protected void deflauteEditCode(File file, String url, StringBuffer sb, String className, String page) {
        sb.append("\n\n").append(
                "import org.springframework.boot.context.properties.ConfigurationProperties;\n" ).append(
                "import org.springframework.stereotype.Component;\n" ).append(
                "\n" ).append(
                "import java.util.Map;\n" ).append(
                "\n" ).append(
                "@Component\n" ).append(
                "@ConfigurationProperties(prefix = \"spring\")\n" ).append(
                "public class SpringProps {\n" ).append(
                "    private Map<String,String> application;\n" ).append(
                "    private Map<String,String> redis;\n" ).append(
                "    private Map<String,String> datasource;\n" ).append(
                "\n" ).append(
                "    public Map<String, String> getApplication() {\n" ).append(
                "        return application;\n" ).append(
                "    }\n" ).append(
                "\n" ).append(
                "    public void setApplication(Map<String, String> application) {\n" ).append(
                "        this.application = application;\n" ).append(
                "    }\n" ).append(
                "\n" ).append(
                "    public Map<String, String> getRedis() {\n" ).append(
                "        return redis;\n" ).append(
                "    }\n" ).append(
                "\n" +
                "    public void setRedis(Map<String, String> redis) {\n" ).append(
                "        this.redis = redis;\n" ).append(
                "    }\n" ).append(
                "\n" ).append(
                "    public Map<String, String> getDatasource() {\n" ).append(
                "        return datasource;\n" ).append(
                "    }\n" ).append(
                "\n" ).append(
                "    public void setDatasource(Map<String, String> datasource) {\n" ).append(
                "        this.datasource = datasource;\n" ).append(
                "    }\n" ).append(
                "}\n");
    }

    @Override
    protected void restEditCode(File file, String url, StringBuffer sb, String className, String page) {
        deflauteEditCode(file,url,sb,className,page);
    }
}
