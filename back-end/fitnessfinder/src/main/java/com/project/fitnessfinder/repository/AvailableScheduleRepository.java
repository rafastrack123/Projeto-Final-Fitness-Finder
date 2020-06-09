package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.AvailableSchedule;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AvailableScheduleRepository extends CrudRepository<AvailableSchedule, Long> {

    @Query("select availableSchedule" +
            " from AvailableSchedule as availableSchedule" +
            " join availableSchedule.vendorOffer as vendorOffer " +
            " where vendorOffer.id=?1 ")
    List<AvailableSchedule> findByVendorOfferId(Long vendorOfferId);
}
