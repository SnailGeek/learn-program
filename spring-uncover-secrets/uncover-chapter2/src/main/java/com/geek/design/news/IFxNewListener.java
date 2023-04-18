package com.geek.design.news;

public interface IFxNewListener {
    String[] getAvailableNewIds();

    FxNewsBean getNewByPK(String newId);

    void postProcessIfNecessary(String newId);
}
