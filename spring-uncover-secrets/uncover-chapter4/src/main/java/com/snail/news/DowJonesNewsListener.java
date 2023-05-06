package com.snail.news;


public class DowJonesNewsListener implements IFxNewListener, PasswordDecodable {
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

    @Override
    public String getEncodedPassword() {
        System.out.println("getEncodedPassword");
        return "encodedPassword";
    }

    @Override
    public void setDecodedPassword(String password) {
        System.out.println("setDecodePassword: " + password);
    }
}
