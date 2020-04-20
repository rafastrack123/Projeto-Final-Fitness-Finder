package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.ServiceGroup;
import org.springframework.data.repository.CrudRepository;

public interface ServiceGroupRepository extends CrudRepository<ServiceGroup, Long> {
}
