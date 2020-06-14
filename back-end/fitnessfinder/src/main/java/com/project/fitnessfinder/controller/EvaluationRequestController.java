package com.project.fitnessfinder.controller;

import com.project.fitnessfinder.domain.entity.api.EvaluationRequestJson;
import com.project.fitnessfinder.service.EvaluationRequestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/evaluation-request")
@RequiredArgsConstructor
public class EvaluationRequestController {

    private final EvaluationRequestService evaluationRequestService;

    @PostMapping("/customers/{customerId}/vendor-offers/{vendorOfferId}/leads/{leadId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvaluationRequest(@PathVariable Long customerId,
                                        @PathVariable Long vendorOfferId,
                                        @PathVariable Long leadId) {
        evaluationRequestService.createEvaluationRequest(customerId, vendorOfferId, leadId);
    }

    @GetMapping("/customers/{customerId}")
    public List<EvaluationRequestJson> getByCustomerId(@PathVariable Long customerId) {
        return evaluationRequestService.getByCustomerId(customerId);
    }

    @DeleteMapping("/{evaluationRequestId}")
    public void deleteById(@PathVariable Long evaluationRequestId){
        evaluationRequestService.deleteById(evaluationRequestId);
    }

}
