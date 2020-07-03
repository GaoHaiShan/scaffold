package com.haishan.createclassv2;

import org.example.page.custom.CustomPageCreator;
import org.example.page.custom.CustomPageFactory;
import org.example.page.defaultImpl.DefaultPageCreator;
import org.example.page.defaultImpl.DefaultPageFactory;

public class CreatePageStart {
    public static void main(String[] args) {
        //创建文件夹
        DefaultPageCreator creator =  (DefaultPageCreator) DefaultPageFactory.getInstance().getObject(CreatePageStart.class);
        creator.create();
        CustomPageCreator interceptor = (CustomPageCreator) CustomPageFactory.getInstance().getObject(CreatePageStart.class);
        interceptor.create("interceptor");
        interceptor.create("util");
        CustomPageCreator mapper = (CustomPageCreator) CustomPageFactory
                .getInstance()
                .getObject(CreatePageStart.class.getClassLoader().getResource("")
                        .getPath().replaceAll("target.*","src/main/resources/"));
        mapper.create("mapper");
    }
}
