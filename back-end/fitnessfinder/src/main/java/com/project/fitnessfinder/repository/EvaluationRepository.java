package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.Evaluation;
import org.springframework.data.repository.CrudRepository;

public interface EvaluationRepository extends CrudRepository<Evaluation, Long> {
}
