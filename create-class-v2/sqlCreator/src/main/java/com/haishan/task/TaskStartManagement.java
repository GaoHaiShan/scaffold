package com.haishan.task;import 
com.haishan.interceptor.TableCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
public class TaskStartManagement implements SmartLifecycle {
   private volatile Boolean run = false;
   private static final Logger logger = LoggerFactory.getLogger(TaskStartManagement.class);
    @Override
    public void start() {
        run = true;
        //启动更新数据库字段
        startUpdateTableFieldTask();

    }
    private void startUpdateTableFieldTask(){
        logger.debug("Update Table Field Task is start ;");
        ScheduledFuture scheduledFuture = Executors.newScheduledThreadPool(1)
                .scheduleAtFixedRate(() -> {
                    for (String s : TableCache.getInstance().TABLEPOOL.keySet()) {
                        logger.debug("update table field : " + s);
                        TableCache.getInstance().update(s);
                    }
                },0, 1, TimeUnit.SECONDS);

    }
    @Override
    public void stop() {
        run=false;
    }

    @Override
    public boolean isRunning() {
        return run;
    }

}