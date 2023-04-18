package com.geek.design.originalDemo;

public class FXNewsProvider {
    private IFxNewListener newsListener;
    private IFXNewsPersister newsPersister;

    public FXNewsProvider() {

        // 这种写法将FXnewsProvider和具体实现耦合在了一起
//        this.newsListener = new DowJonesNewsListener();
//        this.newsPersister = new DowJonesNewsPersister();
    }

    public FXNewsProvider(IFxNewListener newsListener, IFXNewsPersister newsPersister) {
        this.newsListener = newsListener;
        this.newsPersister = newsPersister;
    }

    public void getAndPersistNews() {
        final String[] newIds = newsListener.getAvailableNewIds();
        if (newIds == null) {
            return;
        }

        for (String newId : newIds) {
            final FxNewsBean newsBean = newsListener.getNewByPK(newId);
            newsPersister.persistNews(newsBean);
            newsListener.postProcessIfNecessary(newId);
        }

    }
}
