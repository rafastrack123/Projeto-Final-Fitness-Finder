package com.project.fitnessfinder.service;

import com.project.fitnessfinder.domain.entity.api.VendorOfferDetailJson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendorOfferDetailService {

    private final AvailableScheduleService availableScheduleService;
    private final EvaluationService evaluationService;
    private final VendorOfferService vendorOfferService;

    public VendorOfferDetailJson getByVendorOfferId(Long vendorOfferId) {
        var vendorOffer = vendorOfferService.get(vendorOfferId);

        var vendorEvaluation = evaluationService.getEvaluationsByVendorId(vendorOffer.getVendor().getId());

        var availableSchedule= availableScheduleService.findByVendorOfferId(vendorOfferId);

        return VendorOfferDetailJson
                .builder()
                .vendorOfferId(vendorOfferId)
                .vendorEvaluations(vendorEvaluation)
                .availableSchedules(availableSchedule)
                .build();


    }
}
