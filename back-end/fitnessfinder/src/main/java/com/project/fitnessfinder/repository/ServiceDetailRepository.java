package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.ServiceDetail;
import org.springframework.data.repository.CrudRepository;

public interface ServiceDetailRepository extends CrudRepository<ServiceDetail, Long> {
}
