package com.snail.news;

public class SpecificFXNewsProvider extends FXNewsProvider {

    private IFxNewListener djNewsListener;

    private IFXNewsPersister djNewsPersister;

    @Override
    public void setDjNewsListener(IFxNewListener djNewsListener) {
        this.djNewsListener = djNewsListener;
    }

    @Override
    public void setDjNewsPersister(IFXNewsPersister djNewsPersister) {
        this.djNewsPersister = djNewsPersister;
    }

    @Override
    public void getAndPersistNews() {
        System.out.println("invoke SpecificFXNewsProvider getAndPersistNews");
        super.getAndPersistNews();
    }

    @Override
    public void printPropertyBean() {
        System.out.println("sub djNewsListener: " + djNewsListener);
        System.out.println("sub djNewsPersister: " + djNewsPersister);
    }
}
