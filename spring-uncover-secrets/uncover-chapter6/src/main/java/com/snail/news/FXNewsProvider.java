package com.snail.news;

import org.springframework.beans.factory.annotation.Autowired;

public class FXNewsProvider {
    private IFxNewListener djNewsListener;
    private IFXNewsPersister djNewsPersister;

    @Autowired
    public FXNewsProvider(IFxNewListener djNewsListener, IFXNewsPersister djNewsPersister) {
        this.djNewsListener = djNewsListener;
        this.djNewsPersister = djNewsPersister;
    }


    @Autowired
    public void setUp(IFxNewListener djNewsListener, IFXNewsPersister djNewsPersister) {
        this.djNewsListener = djNewsListener;
        this.djNewsPersister = djNewsPersister;
    }


    public void setDjNewsListener(IFxNewListener djNewsListener) {
        this.djNewsListener = djNewsListener;
    }

    public void setDjNewsPersister(IFXNewsPersister djNewsPersister) {
        this.djNewsPersister = djNewsPersister;
    }

    public IFxNewListener getDjNewsListener() {
        return djNewsListener;
    }

    public IFXNewsPersister getDjNewsPersister() {
        return djNewsPersister;
    }

    public void getAndPersistNews() {

        System.out.println("invoke getAndPersistNews");
        final String[] newIds = djNewsListener.getAvailableNewIds();
        if (newIds == null) {
            return;
        }

        for (String newId : newIds) {
            final FxNewsBean newsBean = djNewsListener.getNewByPK(newId);
            djNewsPersister.persistNews(newsBean);
            djNewsListener.postProcessIfNecessary(newId);
        }
    }

    public void printPropertyBean() {
        System.out.println("super djNewsListener: " + djNewsListener);
        System.out.println("super djNewsPersister: " + djNewsPersister);
    }
}