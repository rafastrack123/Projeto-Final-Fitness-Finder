package com.project.fitnessfinder.service;

import com.project.fitnessfinder.converter.Converter;
import com.project.fitnessfinder.domain.entity.api.EvaluationJson;
import com.project.fitnessfinder.domain.entity.api.VendorEvaluationsJson;
import com.project.fitnessfinder.domain.entity.database.Evaluation;
import com.project.fitnessfinder.repository.EvaluationRepository;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final Converter converter;
    private final CustomerService customerService;
    private final VendorService vendorService;
    private final EvaluationRequestService evaluationRequestService;
    private final EvaluationRepository evaluationRepository;

    public void createEvaluation(EvaluationJson evaluationJson) {

        var customer = customerService.get(evaluationJson.customerId);
        var vendor = vendorService.get(evaluationJson.getVendorId());

        var evaluation = new Evaluation();

        evaluation.setFromCustomer(customer);
        evaluation.setToVendor(vendor);

        evaluation.setRating(evaluationJson.getRating());
        evaluation.setFeedback(evaluationJson.getFeedback());

        evaluation.setCreatedDate(new Date());

        evaluationRequestService.deleteById(evaluationJson.evaluationRequestId);

        evaluationRepository.save(evaluation);
    }

    public VendorEvaluationsJson getEvaluationsByVendorId(Long vendorId) {
        var evaluations = evaluationRepository.getByVendorId(vendorId);

        var vendorEvaluations = new VendorEvaluationsJson();

        vendorEvaluations.setVendorId(vendorId);

        if (!evaluations.isEmpty()) {
            var evaluationsJson = evaluations.stream()
                    .map(converter::convert)
                    .collect(Collectors.toList());

            vendorEvaluations.setEvaluations(evaluationsJson);

            var avgRating = evaluations.stream()
                    .mapToInt(Evaluation::getRating)
                    .average()
                    .getAsDouble();

            vendorEvaluations.setAverageRating(avgRating);
        }

        return vendorEvaluations;
    }
}
