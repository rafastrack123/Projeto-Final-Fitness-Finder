package com.project.fitnessfinder.controller;

import com.project.fitnessfinder.domain.entity.api.VendorOfferJson;
import com.project.fitnessfinder.domain.entity.api.response.SearchVendorOfferResponse;
import com.project.fitnessfinder.domain.entity.api.response.VendorOfferByVendorResponse;
import com.project.fitnessfinder.service.ServiceAreaService;
import com.project.fitnessfinder.service.VendorOfferService;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
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
                                                        @RequestParam(required = false) Long maxDistanceInKm,
                                                        @RequestParam(required = false) String dayOfWeek,
                                                        @RequestParam(required = false) String startTime,
                                                        @RequestParam(required = false) String endTime) {


        //var serviceArea = serviceAreaService.getById(serviceAreaId);

        var vendorOffersJsonList = vendorOfferService.searchVendorOffers(customerId, serviceAreaId, serviceGroupId,
                serviceDetailId, vendorFirstName, vendorLastName, maxPrice, isHomeService, isFirstClassFree, isRemoteService,
                maxDistanceInKm,dayOfWeek,startTime,endTime);

        return buildResponse(
                // serviceArea.getName(),
                vendorOffersJsonList);

    }

    private SearchVendorOfferResponse buildResponse(
            // String serviceAreaName,
            List<VendorOfferJson> vendorOfferJsonList) {
        var response = new SearchVendorOfferResponse();

        // response.serviceAreaName = serviceAreaName;
        response.offers = vendorOfferJsonList;

        return response;

    }

    @GetMapping("{vendorId}")
    public VendorOfferByVendorResponse getByVendorId(@PathVariable Long vendorId) {
        var vendorOffers = vendorOfferService.findByVendorId(vendorId);

        return VendorOfferByVendorResponse.builder()
                .vendorId(vendorId)
                .vendorOffers(vendorOffers)
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createVendorOffer(@RequestBody VendorOfferJson vendorOfferJson) {

        vendorOfferService.createVendorOffer(vendorOfferJson);
    }

    @DeleteMapping("{vendorOfferId}")
    public void deleteVendorOffer(@PathVariable("vendorOfferId") Long vendorOfferId) {
        vendorOfferService.deleteById(vendorOfferId);
    }


}
