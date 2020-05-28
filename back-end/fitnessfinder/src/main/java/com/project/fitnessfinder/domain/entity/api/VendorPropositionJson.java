package com.project.fitnessfinder.domain.entity.api;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class VendorPropositionJson {

    public Long id;

    public Long vendorOfferId;

    public String serviceDescription;

    public String vendorFirstName;

    public String vendorLastName;

    public Long customerId;

    public String message;

    public boolean viewedByCustomer;
}
