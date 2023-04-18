package com.geek.design.annotion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FXNewsProvider {
    @Autowired
    private IFxNewListener djNewsListener;
    @Autowired
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