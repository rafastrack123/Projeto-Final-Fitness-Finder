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
    private final ServiceAreaService serviceAreaService;


    @GetMapping
    public SearchVendorOfferResponse searchVendorOffers(@RequestParam Long customerId,
                                                        @RequestParam Long serviceAreaId,
                                                        @RequestParam(required = false) Long serviceGroupId,
                                                        @RequestParam(required = false) Long serviceDetailId,
                                                        @RequestParam(required = false) String vendorFirstName,
                                                        @RequestParam(required = false) String vendorLastName,
                                                        @RequestParam(required = false) BigDecimal maxPrice,
                                                        @RequestParam(required = false) Boolean isHomeService,
                                                        @RequestParam(required = false) Boolean isFirstClassFree,
                                                        @RequestParam(required = false) Boolean isRemoteService,
                                                        @RequestParam(required = false) Long maxDistanceInKm) {


        var serviceArea = serviceAreaService.getById(serviceAreaId);

        var vendorOffersJsonList = vendorOfferService.searchVendorOffers(customerId, serviceAreaId, serviceGroupId,
                serviceDetailId, vendorFirstName, vendorLastName, maxPrice, isHomeService, isFirstClassFree, isRemoteService,
                maxDistanceInKm);

        return buildResponse(serviceArea.getName(), vendorOffersJsonList);

    }

    private SearchVendorOfferResponse buildResponse(String serviceAreaName, List<VendorOfferJson> vendorOfferJsonList) {
        var response = new SearchVendorOfferResponse();

        response.serviceAreaName = serviceAreaName;
        response.offers = vendorOfferJsonList;

        return response;

    }


}
