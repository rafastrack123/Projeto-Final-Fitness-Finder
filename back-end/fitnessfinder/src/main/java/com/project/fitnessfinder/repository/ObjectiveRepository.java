package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.Objective;
import org.springframework.data.repository.CrudRepository;

public interface ObjectiveRepository extends CrudRepository<Objective, Long> {
}
