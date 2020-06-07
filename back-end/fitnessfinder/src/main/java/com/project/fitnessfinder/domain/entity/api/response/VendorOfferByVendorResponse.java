package com.project.fitnessfinder.domain.entity.api.response;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.fitnessfinder.domain.entity.api.VendorOfferJson;
import java.util.List;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class VendorOfferByVendorResponse {

    public Long vendorId;
    public List<VendorOfferJson> vendorOffers;
}
