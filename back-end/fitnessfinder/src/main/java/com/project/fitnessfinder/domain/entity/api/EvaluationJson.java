package com.project.fitnessfinder.domain.entity.api;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
@Builder
public class EvaluationJson {

    public Long id;

    public Long vendorId;

    public Long customerId;

    public Long evaluationRequestId;

    public String customerFirstName;

    public String customerLastName;

    public Integer rating;

    public String feedback;
}
