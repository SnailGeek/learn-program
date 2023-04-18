package com.geek.design.springDemo;

import com.geek.design.originalDemo.FxNewsBean;
import com.geek.design.originalDemo.IFXNewsPersister;
import com.geek.design.originalDemo.IFxNewListener;

public class FXNewsProvider {
    private IFxNewListener djNewsListener;
    private IFXNewsPersister djNewsPersister;

    public FXNewsProvider(IFxNewListener djNewsListener, IFXNewsPersister djNewsPersister) {
        this.djNewsListener = djNewsListener;
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
}
