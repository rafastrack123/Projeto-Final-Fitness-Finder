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
public class EvaluationRequestJson {

    public Long id;

    public Long vendorOfferid;

    public Long vendorId;

    public Long customerId;

    public String vendorFirstName;
    public String vendorLastName;

    public String groupName;
    public String detailName;
}
