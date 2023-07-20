package com.snail.chapter14.dao;

import com.snail.chapter14.enums.CampainStatus;

public class Customer {
    private CampainStatus campainStatus;

    public CampainStatus getCampainStatus() {
        return campainStatus;
    }

    public void setCampainStatus(CampainStatus campainStatus) {
        this.campainStatus = campainStatus;
    }
}
