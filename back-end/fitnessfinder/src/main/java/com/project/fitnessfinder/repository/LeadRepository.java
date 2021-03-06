package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.Customer;
import com.project.fitnessfinder.domain.entity.database.Lead;
import com.project.fitnessfinder.domain.entity.database.VendorOffer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LeadRepository extends CrudRepository<Lead, Long> {

    @Query("select lead" +
            " from Lead as lead" +
            " join lead.vendorOffer vendorOffer" +
            " join vendorOffer.vendor vendor" +
            " where vendor.id = ?1 order by lead.updateDate")
    List<Lead> getAllLeadsByVendorId(Long vendorId);

    @Query("select lead" +
            " from Lead as lead" +
            " where lead.customer= ?1 and lead.vendorOffer = ?2")
    Optional<Lead> getLeadByCustomerAndVendor(Customer customer, VendorOffer vendorOffer);

}
