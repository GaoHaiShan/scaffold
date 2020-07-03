package org.example.page.defaultImpl;

import org.example.page.IPageCreator;
import org.example.page.controller.ControllerPageCreator;
import org.example.page.controller.ControllerPageFactory;
import org.example.page.dao.DaoPageCreator;
import org.example.page.dao.DaoPageFactory;
import org.example.page.service.ServicePageCreator;
import org.example.page.service.ServicePageFactory;


public class DefaultPageCreator implements IPageCreator {

    private String basePath;

    public DefaultPageCreator(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public void create(String... className) {
        ((ControllerPageCreator) ControllerPageFactory.getInstance()
                .getObject(basePath))
                .create(className);
        ((ServicePageCreator) ServicePageFactory.getInstance()
                .getObject(basePath))
                .create(className);
        ((DaoPageCreator) DaoPageFactory.getInstance()
                .getObject(basePath))
                .create(className);
    }
}
