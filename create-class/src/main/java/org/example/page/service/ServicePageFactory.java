package org.example.page.service;

import org.example.page.PageFactoryBean;

public class ServicePageFactory extends PageFactoryBean {
    private static ServicePageFactory servicePageFactory = new ServicePageFactory();

    private ServicePageCreator creator;

    public static ServicePageFactory getInstance() {
        return servicePageFactory;
    }

    @Override
    protected Object createObject() {
        if (creator == null || !super.isStatus()) {
            creator = new ServicePageCreator(super.getBasePath());
            super.setStatus(false);
        }
        return creator;
    }
}
