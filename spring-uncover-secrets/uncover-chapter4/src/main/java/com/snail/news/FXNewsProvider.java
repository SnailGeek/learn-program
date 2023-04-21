package com.snail.news;

public class FXNewsProvider {
    private IFxNewListener djNewsListener;
    private IFXNewsPersister djNewsPersister;

    public void setDjNewsListener(IFxNewListener djNewsListener) {
        this.djNewsListener = djNewsListener;
    }

    public void setDjNewsPersister(IFXNewsPersister djNewsPersister) {
        this.djNewsPersister = djNewsPersister;
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