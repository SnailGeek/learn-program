package com.snail.chapter14.service;

import com.snail.chapter14.dao.Customer;
import com.snail.chapter14.dao.ICustomerDao;
import com.snail.chapter14.enums.CampainStatus;

public class CustomerService {
    private ICustomerDao customerDao;

    private void disableCustomerCampain(String customerId) {
        final Customer customer = getCustomerDao().findCustomerByPK(customerId);
        customer.setCampainStatus(CampainStatus.DISABLE);
        getCustomerDao().updateCustomerStatus(customer);
    }

    public ICustomerDao getCustomerDao() {
        return customerDao;
    }

    public void setCustomerDao(ICustomerDao customerDao) {
        this.customerDao = customerDao;
    }
}
