package com.snail.news;

public class MockNewsPersister implements IFXNewsPersister {
    private FxNewsBean newsBean;

    @Override
    public void persistNews(FxNewsBean newsBean) {
        persistNews();
    }

    public void persistNews() {
        System.out.println("persist bean:" + getNewsBean());
    }

    public FxNewsBean getNewsBean() {
        return newsBean;
    }

    public void setNewsBean(FxNewsBean newsBean) {
        this.newsBean = newsBean;
    }
}
