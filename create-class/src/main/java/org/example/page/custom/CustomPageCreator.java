package org.example.page.custom;

import org.example.page.IPageCreator;

import java.io.File;

public class CustomPageCreator implements IPageCreator {

    private String basePath;

    public CustomPageCreator(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public void create(String... className) {
        for (String name : className) {
            File file = new File((basePath + "/" + name).replaceAll("/+", "/"));
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }
}
