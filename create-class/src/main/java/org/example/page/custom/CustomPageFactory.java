package org.example.page.custom;

import org.example.page.PageFactoryBean;

public class CustomPageFactory extends PageFactoryBean {

    private static CustomPageFactory customPageFactory = new CustomPageFactory();

    private CustomPageCreator creator;

    public static CustomPageFactory getInstance() {
        return customPageFactory;
    }

    @Override
    protected Object createObject() {
        if (creator == null || !super.isStatus()) {
            creator = new CustomPageCreator(super.getBasePath());
            super.setStatus(false);
        }
        return creator;
    }
}
