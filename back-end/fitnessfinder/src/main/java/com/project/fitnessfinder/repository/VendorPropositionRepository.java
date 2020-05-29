package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.VendorProposition;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VendorPropositionRepository extends CrudRepository<VendorProposition, Long> {

    @Query("select vendorProposition " +
            " from VendorProposition  vendorProposition" +
            " join vendorProposition.customer customer" +
            " where customer.id= ?1 and vendorProposition.viewedByCustomer = false")
    List<VendorProposition> getByCustomerId(Long customerId);
}
