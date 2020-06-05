package org.example.page.defaultImpl;

import org.example.page.PageFactoryBean;

public class DefaultPageFactory extends PageFactoryBean {

    private static DefaultPageFactory defaultPageFactory = new DefaultPageFactory();

    private DefaultPageCreator creator;

    public static DefaultPageFactory getInstance(){
        return defaultPageFactory;
    }

    @Override
    protected Object createObject() {
        if(creator==null||!super.isStatus()) {
            creator = new DefaultPageCreator(super.getBasePath());
            super.setStatus(false);
        }
        return creator;
    }

}
