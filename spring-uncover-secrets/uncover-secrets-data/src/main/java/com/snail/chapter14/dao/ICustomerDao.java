package com.snail.chapter14.dao;

public interface ICustomerDao {
    Customer findCustomerByPK(String customerID);

    void updateCustomerStatus(Customer customer);
}
