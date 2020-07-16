package org.example.clazz.create.creator.task;

import org.example.clazz.create.AbstartClassCreator;

import java.io.File;
import java.io.IOException;

public class TaskClassCreator extends AbstartClassCreator {
    @Override
    protected File createJavaFile(String basePath, String className) {
        File file = null;
        try {
            file = createJavaFile(basePath, "TaskStartManagement", "/task/", ".java");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected String getPackage(String basePath) {
        return getPackage(basePath, "task");
    }

    @Override
    protected void deflauteEditCode(File file, String url, StringBuffer sb, String className, String page) {
        sb.append("import \n" + page.replaceAll("\\.task.*",".interceptor.TableCache")
                .replaceAll("package ","") +";\n"+
                "import org.slf4j.Logger;\n" +
                "import org.slf4j.LoggerFactory;\n" +
                "import org.springframework.context.SmartLifecycle;\n" +
                "import org.springframework.stereotype.Component;\n" +
                "\n" +
                "import java.util.concurrent.Executors;\n" +
                "import java.util.concurrent.ScheduledFuture;\n" +
                "import java.util.concurrent.TimeUnit;\n" +
                "\n" +
                "@Component\n" +
                "public class TaskStartManagement implements SmartLifecycle {\n" +
                "   private volatile Boolean run = false;\n" +
                "   private static final Logger logger = LoggerFactory.getLogger(TaskStartManagement.class);\n" +
                "    @Override\n" +
                "    public void start() {\n" +
                "        run = true;\n" +
                "        //启动更新数据库字段\n" +
                "        startUpdateTableFieldTask();\n" +
                "\n" +
                "    }\n" +
                "    private void startUpdateTableFieldTask(){\n" +
                "        logger.debug(\"Update Table Field Task is start ;\");\n" +
                "        ScheduledFuture scheduledFuture = Executors.newScheduledThreadPool(1)\n" +
                "                .scheduleAtFixedRate(() -> {\n" +
                "                    for (String s : TableCache.getInstance().TABLEPOOL.keySet()) {\n" +
                "                        logger.debug(\"update table field : \" + s);\n" +
                "                        TableCache.getInstance().update(s);\n" +
                "                    }\n" +
                "                },0, 1, TimeUnit.SECONDS);\n" +
                "\n" +
                "    }\n" +
                "    @Override\n" +
                "    public void stop() {\n" +
                "        run=false;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public boolean isRunning() {\n" +
                "        return run;\n" +
                "    }\n" +
                "\n" +
                "}");
    }

    @Override
    protected void restEditCode(File file, String url, StringBuffer sb, String className, String page) {
        deflauteEditCode(file, url, sb, className, page);
    }
}
