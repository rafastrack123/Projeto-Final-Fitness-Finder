package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.ServiceArea;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ServiceAreaRepository extends CrudRepository<ServiceArea, Long> {

    @Override
    List<ServiceArea> findAll();
}
