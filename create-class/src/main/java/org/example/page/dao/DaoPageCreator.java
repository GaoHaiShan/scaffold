package org.example.page.dao;

import org.example.page.IPageCreator;

import java.io.File;

public class DaoPageCreator implements IPageCreator {
    private String basePath;

    public DaoPageCreator(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public void create(String... className) {
        File dao = new File(basePath+"/dao");
        if(!dao.exists()){
            dao.mkdirs();
        }
    }
}
