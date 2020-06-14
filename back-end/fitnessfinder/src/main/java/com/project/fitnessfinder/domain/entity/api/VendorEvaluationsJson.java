package com.project.fitnessfinder.domain.entity.api;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class VendorEvaluationsJson {

    public Long vendorId;
    public Double averageRating;

    public List<EvaluationJson> evaluations;
}
