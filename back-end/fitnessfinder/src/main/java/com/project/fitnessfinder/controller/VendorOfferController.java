package com.project.fitnessfinder.controller;

import com.project.fitnessfinder.domain.entity.api.VendorOfferJson;
import com.project.fitnessfinder.domain.entity.api.response.SearchVendorOfferResponse;
import com.project.fitnessfinder.service.ServiceAreaService;
import com.project.fitnessfinder.service.VendorOfferService;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vendor-offer")
@RequiredArgsConstructor
public class VendorOfferController {

    private final VendorOfferService vendorOfferService;
    private final ServiceAreaService ServiceAreaService;


    @GetMapping
    public SearchVendorOfferResponse searchVendorOffers(@RequestParam Long serviceAreaId,
                                                        @RequestParam(required = false) Long serviceGroupId,
                                                        @RequestParam(required = false) Long serviceDetailId,
                                                        @RequestParam(required = false) BigDecimal maxPrice,
                                                        @RequestParam(required = false) String vendorName,
                                                        @RequestParam(required = false) boolean isHomeService,
                                                        @RequestParam(required = false) boolean firstClassFree) {


        var serviceArea = ServiceAreaService.getById(serviceAreaId);

        var vendorOffersJsonList = vendorOfferService.searchVendorOffers(serviceAreaId, serviceGroupId, serviceDetailId,
                maxPrice, vendorName, isHomeService, firstClassFree);


        return buildResponse("Name", vendorOffersJsonList);

    }

    private SearchVendorOfferResponse buildResponse(String serviceAreaName, List<VendorOfferJson> vendorOfferJsonList) {
        var response = new SearchVendorOfferResponse();

        response.serviceAreaName = serviceAreaName;
        response.offers = vendorOfferJsonList;

        return response;

    }


}
