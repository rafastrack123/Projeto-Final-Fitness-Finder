package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.Objective;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ObjectiveRepository extends CrudRepository<Objective, Long> {

    @Override
    List<Objective> findAll();
}
