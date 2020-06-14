package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.EvaluationRequest;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EvaluationRequestRepository extends CrudRepository<EvaluationRequest, Long> {

    @Query(" select evaluationRequest " +
            " from EvaluationRequest as evaluationRequest" +
            " join evaluationRequest.customer customer" +
            " where customer.id=?1")
    List<EvaluationRequest> getByCustomerId(Long customerId);
}
