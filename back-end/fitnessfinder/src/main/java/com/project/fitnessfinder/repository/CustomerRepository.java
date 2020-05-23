package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.Customer;
import com.project.fitnessfinder.domain.entity.database.Vendor;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query("select customer from Customer customer" +
            " where customer.email=?1 and customer.password=?2")
    Optional<Customer> findByEmailAndPassword(String email, String password);

    @Query("select customer from Customer customer" +
            " where customer.email=?1")
    Optional<Customer> findByEmail(String email);
}
