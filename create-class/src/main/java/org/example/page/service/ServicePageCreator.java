package org.example.page.service;

import org.example.page.IPageCreator;

import java.io.File;

public class ServicePageCreator implements IPageCreator {
    private String basePath;

    public ServicePageCreator(String basePath){
        this.basePath = basePath;
    }

    @Override
    public void create(String... className) {
        File service = new File(basePath+"/service");
        if(!service.exists()){
            service.mkdirs();
        }
        File serviceImpl = new File(basePath+"/service/impl");
        if(!serviceImpl.exists()){
            serviceImpl.mkdirs();
        }
    }
}
