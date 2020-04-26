package com.project.fitnessfinder.domain.entity.api.response;

import com.project.fitnessfinder.domain.entity.api.VendorOfferJson;
import java.util.List;

public class SearchVendorOfferResponse {

    public String serviceAreaName;

    public List<VendorOfferJson> offers;
}
