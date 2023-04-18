package com.geek.design.annotion;

public interface IFxNewListener {
    String[] getAvailableNewIds();

    FxNewsBean getNewByPK(String newId);

    void postProcessIfNecessary(String newId);
}
