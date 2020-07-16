package org.example.quickStart;

import org.example.AbstartFactoryBean;

import java.io.File;
import java.io.FileOutputStream;

public class QuickStartFactory extends AbstartFactoryBean {
    @Override
    protected Object getObject(boolean first) {
        createClass();
        createPage();
        creatConfig();
        return null;
    }

    private void createClass() {
        String code = getCode2();
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(getBasePath() + "/CreateClassStart.java"))) {
            fileOutputStream.write(code.getBytes());
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createPage() {
        String code = getCode();
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(getBasePath() + "/CreatePageStart.java"))) {
            fileOutputStream.write(code.getBytes());
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getCode() {
        StringBuffer code = new StringBuffer();
        code.append("package ");
        code.append(getBasePath().split("/java/")[1].replaceAll("/", "\\."));
        code.append(";\n");
        code.append("\nimport org.example.page.custom.CustomPageCreator;\n" +
                "import org.example.page.custom.CustomPageFactory;\n" +
                "import org.example.page.defaultImpl.DefaultPageCreator;\n" +
                "import org.example.page.defaultImpl.DefaultPageFactory;\n" +
                "\n" +
                "public class CreatePageStart {\n" +
                "    public static void main(String[] args) {\n" +
                "        //创建文件夹\n" +
                "        DefaultPageCreator creator =  (DefaultPageCreator) DefaultPageFactory.getInstance().getObject(CreatePageStart.class);\n" +
                "        creator.create();\n" +
                "        CustomPageCreator interceptor = (CustomPageCreator) CustomPageFactory.getInstance().getObject(CreatePageStart.class);\n" +
                "        interceptor.create(\"interceptor\");\n" +
                "        interceptor.create(\"util\");\n" +
                "        interceptor.create(\"task\");\n" +
                "        CustomPageCreator mapper = (CustomPageCreator) CustomPageFactory\n" +
                "                .getInstance()\n" +
                "                .getObject(CreatePageStart.class.getClassLoader().getResource(\"\")\n" +
                "                        .getPath().replaceAll(\"target.*\",\"src/main/resources/\"));\n" +
                "        mapper.create(\"mapper\");\n" +
                "    }\n" +
                "}\n");
        return code.toString();
    }

    private String getCode2() {
        StringBuffer code = new StringBuffer();
        code.append("package ");
        code.append(getBasePath().replaceAll(".*/java/", "")
                .replaceAll("/+", "/")
                .replaceAll("/", "\\."));
        code.append(";\n");
        code.append("\nimport org.example.AbstartFactoryBean;\n" +
                "import org.example.clazz.create.DefaultClassFactoryBean;\n" +
                "\n" +
                "public class CreateClassStart {\n" +
                "    public static void main(String[] args) {\n" +
                "\n" +
                "        //创建类\n" +
                "        AbstartFactoryBean factoryBean = new DefaultClassFactoryBean();\n" +
                "        factoryBean.isFirst(true);\n" +
                "        factoryBean.getObject(\""+getFilePath()+"\",CreateClassStart.class.getClassLoader().getResource(\"\").getPath()\n" +
                "                .replaceAll(\"/java.*\",\"/resources\")+\"/createclass.properties\");\n" +
                "    }\n" +
                "}");
        return code.toString();
    }

    void creatConfig() {
        //创建配置文件
        String code = getCode1();
        try (
                FileOutputStream o = new FileOutputStream(
                        new File(
                                getBasePath()
                                        .replaceAll("/java.*", "/resources")
                                        + "/createclass.properties"
                        )
                )
        ) {
            o.write(code.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getCode1() {
        String code = "#类名称\n" +
                "package.className=\n" +
                "#应用名称\n" +
                "package.applicationName=\n" +
                "#数据库名称\n" +
                "package.dataBase=\n" +
                "#原始路径\n" +
                "package.basePath=" + getBasePath() + "\n" +
                "#start class 启动类\n" +
                "package.startClass=" +getFilePath()+"\n"+
                "#表名\n" +
                "package.tableName=\n" +
                "package.primaryKey=\n" +
                "package.dataBaseUserName=\n" +
                "package.dataBasePassWord=\n" +
                "package.redisHost= 127.0.0.1\n" +
                "package.redisPort=6379\n" +
                "#controller mapping\n" +
                "package.url=\n" +
                "#使用rest风格\n" +
                "package.rest=true\n" +
                "#注册rabbitmq\n" +
                "package.isHasRabbitMQ=false\n" +
                "#注册redis\n" +
                "package.isHasRedis=false\n" +
                "#注册dubbo\n" +
                "package.isHashDubbo=false\n" +
                "#打开事务\n" +
                "package.OpenTransaction=true\n" +
                "#结束标志\n" +
                "package.end=true";
        return code;
    }
}
