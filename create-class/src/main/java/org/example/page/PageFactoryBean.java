package org.example.page;

import org.example.AbstartFactoryBean;

public abstract class PageFactoryBean extends AbstartFactoryBean {
    private String basePath;
    private Boolean status = false;

    @Override
    protected Object getObject(boolean first) {
        if (getBasePath().equals(basePath)) {
            status = true;
        }
        return createObject();
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    protected abstract Object createObject();
}
