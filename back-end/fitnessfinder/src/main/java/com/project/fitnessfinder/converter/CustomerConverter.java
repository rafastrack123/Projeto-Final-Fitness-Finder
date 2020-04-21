package com.project.fitnessfinder.converter;

import com.project.fitnessfinder.domain.entity.api.CustomerJson;
import com.project.fitnessfinder.domain.entity.database.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {

    public CustomerJson convert(Customer customer) {
        return null;
    }

    public Customer convert(CustomerJson customer) {
        return null;
    }

    public Customer convertSaved(Long id, CustomerJson customerJson) {
        var customer = convert(customerJson);
        customer.setId(id);

        return customer;
    }
}
