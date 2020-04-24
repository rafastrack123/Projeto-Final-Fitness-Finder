package com.project.fitnessfinder.service;

import com.project.fitnessfinder.converter.Converter;
import com.project.fitnessfinder.domain.entity.api.VendorOfferJson;
import com.project.fitnessfinder.domain.entity.database.VendorOffer;
import com.project.fitnessfinder.repository.VendorOfferRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendorOfferService {

    private final VendorOfferRepository vendorOfferRepository;
    private final Converter converter;


    public List<VendorOfferJson> searchVendorOffers(Long serviceAreaId,
                                                    Long serviceGroupId,
                                                    Long serviceDetailId,
                                                    BigDecimal maxPrice,
                                                    String vendorName,
                                                    boolean isHomeService,
                                                    boolean firstClassFree) {

        var vendorOffers = vendorOfferRepository.searchVendorOffersByFilter(serviceAreaId,
                serviceGroupId,
                serviceDetailId,
                maxPrice,
                vendorName,
                isHomeService,
                firstClassFree);

        return vendorOffers.stream().map( converter::convert).collect(Collectors.toList());


    }
}
