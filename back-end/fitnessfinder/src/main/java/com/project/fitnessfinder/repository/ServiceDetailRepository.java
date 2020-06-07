package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.ServiceDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ServiceDetailRepository extends CrudRepository<ServiceDetail, Long> {

    @Query("select serviceDetail" +
            " from ServiceDetail as serviceDetail" +
            " join serviceDetail.serviceGroup as serviceGroup" +
            " where serviceGroup.id= ?1")
    List<ServiceDetail> findByServiceGroupId(Long serviceGroupId);

    @Query("select serviceDetail" +
            " from ServiceDetail as serviceDetail" +
            " where serviceDetail.id=?1")
    Optional<ServiceDetail> findById(Long id);
}
