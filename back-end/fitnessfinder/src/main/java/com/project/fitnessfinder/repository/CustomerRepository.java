package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
