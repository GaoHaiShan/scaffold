package com.haishan;

import org.example.AbstartFactoryBean;
import org.example.clazz.create.DefaultClassFactoryBean;

public class CreateClassStart {
    public static void main(String[] args) {

        //创建类
        AbstartFactoryBean factoryBean = new DefaultClassFactoryBean();
        factoryBean.isFirst(true);
        factoryBean.getObject("com.haishan.SqlCreatorApplication",CreateClassStart.class.getClassLoader().getResource("").getPath()
                .replaceAll("/java.*","/resources")+"/createclass.properties");
    }
}