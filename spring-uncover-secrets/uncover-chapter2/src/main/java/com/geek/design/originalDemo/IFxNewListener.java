package com.geek.design.originalDemo;

public interface IFxNewListener {
    String[] getAvailableNewIds();

    FxNewsBean getNewByPK(String newId);

    void postProcessIfNecessary(String newId);
}
