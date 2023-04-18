package com.geek.design.annotion;


import org.springframework.stereotype.Component;

@Component
public class DowJonesNewsListener implements IFxNewListener {
    @Override
    public String[] getAvailableNewIds() {
        return new String[0];
    }

    @Override
    public FxNewsBean getNewByPK(String newId) {
        return null;
    }

    @Override
    public void postProcessIfNecessary(String newId) {

    }
}
