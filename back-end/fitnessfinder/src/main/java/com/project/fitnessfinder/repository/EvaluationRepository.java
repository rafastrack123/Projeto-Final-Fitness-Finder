package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.Evaluation;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EvaluationRepository extends CrudRepository<Evaluation, Long> {

    @Query("select evaluation" +
            " from Evaluation evaluation" +
            " join evaluation.toVendor vendor " +
            " where vendor.id=?1")
    List<Evaluation> getByVendorId(Long vendorId);

}
