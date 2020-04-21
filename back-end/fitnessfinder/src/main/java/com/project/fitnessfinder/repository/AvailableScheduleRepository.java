package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.AvailableSchedule;
import org.springframework.data.repository.CrudRepository;

public interface AvailableScheduleRepository extends CrudRepository<AvailableSchedule, Long> {
}
