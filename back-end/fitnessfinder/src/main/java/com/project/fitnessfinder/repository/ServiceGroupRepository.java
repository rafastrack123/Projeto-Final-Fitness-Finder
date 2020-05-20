package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.ServiceGroup;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ServiceGroupRepository extends CrudRepository<ServiceGroup, Long> {


    @Query("select serviceGroup" +
            " from ServiceGroup as serviceGroup" +
            " join serviceGroup.serviceArea as serviceArea" +
            " where serviceArea.id= ?1")
    List<ServiceGroup> findByServiceAreaId(Long serviceAreaId);
}
