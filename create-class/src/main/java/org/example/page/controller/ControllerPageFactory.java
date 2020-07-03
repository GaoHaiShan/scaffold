package org.example.page.controller;

import org.example.page.PageFactoryBean;

public class ControllerPageFactory extends PageFactoryBean {
    private static ControllerPageFactory controllerPageFactory = new ControllerPageFactory();

    private ControllerPageCreator creator;

    public static ControllerPageFactory getInstance() {
        return controllerPageFactory;
    }

    @Override
    protected Object createObject() {
        if (creator == null || !super.isStatus()) {
            creator = new ControllerPageCreator(super.getBasePath());
            super.setStatus(false);
        }
        return creator;
    }
}
