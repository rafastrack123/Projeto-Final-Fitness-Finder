package com.project.fitnessfinder.controller;

import com.project.fitnessfinder.domain.entity.api.EvaluationJson;
import com.project.fitnessfinder.domain.entity.api.VendorEvaluationsJson;
import com.project.fitnessfinder.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/evaluation")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvaluation(@RequestBody EvaluationJson evaluationJson) {

        evaluationService.createEvaluation(evaluationJson);
    }

    @GetMapping("/vendor/{vendorId}")
    public VendorEvaluationsJson getByVendor(@PathVariable Long vendorId) {
        return evaluationService.getEvaluationsByVendorId(vendorId);
    }

}
