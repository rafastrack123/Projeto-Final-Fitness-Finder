package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.Vendor;
import org.springframework.data.repository.CrudRepository;

public interface VendorRepository extends CrudRepository<Vendor, Long> {
}
