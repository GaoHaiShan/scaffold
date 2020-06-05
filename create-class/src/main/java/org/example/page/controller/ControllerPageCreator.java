package org.example.page.controller;

import org.example.page.IPageCreator;

import java.io.File;

public class ControllerPageCreator implements IPageCreator {
    private String basePath;

    public ControllerPageCreator(String basePath){
        this.basePath = basePath;
    }

    @Override
    public void create(String... className) {
        File controller = new File(basePath+"/controller");
        if(!controller.exists()){
            controller.mkdirs();
        }
    }
}
