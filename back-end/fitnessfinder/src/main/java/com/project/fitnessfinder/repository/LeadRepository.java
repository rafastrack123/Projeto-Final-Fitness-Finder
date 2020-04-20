package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.Lead;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LeadRepository extends CrudRepository<Lead, Long> {

    @Query("select lead" +
            " from Lead as lead" +
            " join lead.customer customer" +
            " where customer.id = ?1")
    List<Lead> getAllLeadsByVendorId(Long vendorId);

}
