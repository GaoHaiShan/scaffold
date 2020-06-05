package org.example.page.dao;

import org.example.page.PageFactoryBean;

public class DaoPageFactory extends PageFactoryBean {

    private static DaoPageFactory daoPageFactory = new DaoPageFactory();

    private DaoPageCreator creator;

    public static DaoPageFactory getInstance(){
        return daoPageFactory;
    }

    @Override
    protected Object createObject() {
        if(creator==null||!super.isStatus()) {
            creator = new DaoPageCreator(super.getBasePath());
            super.setStatus(false);
        }
        return creator;
    }
}
