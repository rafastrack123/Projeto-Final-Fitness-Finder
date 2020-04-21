package com.project.fitnessfinder.service;

import com.project.fitnessfinder.converter.CustomerConverter;
import com.project.fitnessfinder.domain.entity.api.CustomerJson;
import com.project.fitnessfinder.domain.entity.database.Customer;
import com.project.fitnessfinder.exception.EntityNotFound;
import com.project.fitnessfinder.repository.CustomerRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConverter converter;

    public Customer get(Long id) {
        return this.customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("customer", id));

    }

    public CustomerJson getJson(Long id) {
        var customer = get(id);
        return converter.convert(customer);
    }

    public Optional<Customer> find(Long id) {
        return this.customerRepository.findById(id);
    }

    public CustomerJson update(Long id, CustomerJson customerJson) {
        var customerToUpdate = get(id);

        var consumer = converter.convertSaved(customerToUpdate.getId(), customerJson);
        customerRepository.save(consumer);

        return customerJson;
    }

    public CustomerJson save(CustomerJson customerJson) {
        var customer = converter.convert(customerJson);

        var saved = customerRepository.save(customer);

        customerJson.setId(saved.getId());
        return customerJson;
    }
}
