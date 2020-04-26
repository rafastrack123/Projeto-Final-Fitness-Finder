package com.project.fitnessfinder.service;

import com.project.fitnessfinder.converter.Converter;
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

    private final ObjectiveService objectiveService;
    private final CustomerRepository customerRepository;
    private final Converter converter;


    public Customer get(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new EntityNotFound("customer", id));

    }

    public CustomerJson getJson(Long id) {
        var customer = get(id);
        return converter.convert(customer);
    }

    public Optional<Customer> findById(Long id) {
        return this.customerRepository.findById(id);
    }

    public CustomerJson update(Long id, CustomerJson customerJson) {
        var customer = get(id);

        customer = converter.convert(customer, customerJson);

        this.updateObjective(customer, customerJson);

        customerRepository.save(customer);

        return customerJson;
    }

    public CustomerJson save(CustomerJson customerJson) {
        var customer = converter.convert(new Customer(), customerJson);

        this.updateObjective(customer, customerJson);

        customer = customerRepository.save(customer);

        customerJson.setId(customer.getId());
        return customerJson;
    }

    private void updateObjective(Customer customer, CustomerJson customerJson) {

        if (shouldUpdateObjective(customer, customerJson)) {

            var newObject = objectiveService.getObjectById(customerJson.objective.id);

            customer.setObjective(newObject);

        }

    }

    private boolean shouldUpdateObjective(Customer customer, CustomerJson customerJson) {
        return customer.getObjective() == null
                || !customer.getObjective().getId().equals(customerJson.objective.id);
    }
}
