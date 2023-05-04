package com.snail.news;

import org.springframework.beans.factory.ObjectFactory;

public class MockNewsPersisterByObjectCreating implements IFXNewsPersister {
    private ObjectFactory<FxNewsBean> newsBeanFactory;

    @Override
    public void persistNews(FxNewsBean newsBean) {
        persistNews();
    }

    public void persistNews() {
        System.out.println("persist bean:" + getNewsBean());
    }

    public FxNewsBean getNewsBean() {
        return newsBeanFactory.getObject();
    }

    public void setNewsBeanFactory(ObjectFactory newsBeanFactory) {
        this.newsBeanFactory = newsBeanFactory;
    }
}
