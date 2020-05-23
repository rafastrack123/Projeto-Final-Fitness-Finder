package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.Vendor;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VendorRepository extends CrudRepository<Vendor, Long> {

    @Query("select vendor from Vendor vendor" +
            " where vendor.email=?1 and vendor.password=?2")
    Optional<Vendor> findByEmailAndPassword(String email, String password);

    @Query("select vendor from Vendor vendor" +
            " where vendor.email=?1")
    Optional<Vendor> findByEmail(String email);
}
