package com.project.fitnessfinder.service;

import com.project.fitnessfinder.converter.Converter;
import com.project.fitnessfinder.domain.entity.api.VendorOfferJson;
import com.project.fitnessfinder.domain.entity.database.VendorOffer;
import com.project.fitnessfinder.exception.EntityNotFound;
import com.project.fitnessfinder.repository.VendorOfferRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendorOfferService {

    private final CustomerService customerService;
    private final VendorOfferRepository vendorOfferRepository;


    public List<VendorOfferJson> searchVendorOffers(Long customerId,
                                                    Long serviceAreaId,
                                                    Long serviceGroupId,
                                                    Long serviceDetailId,
                                                    String vendorFistName,
                                                    String vendorLastName,
                                                    BigDecimal maxPrice,
                                                    Boolean isHomeService,
                                                    Boolean firstClassFree,
                                                    Long maxDistance) {

        var customer = customerService.get(customerId);

        return vendorOfferRepository.searchVendorOffersByFilters(serviceAreaId,
                serviceGroupId,
                serviceDetailId,
                vendorFistName,
                vendorLastName,
                maxPrice,
                isHomeService,
                firstClassFree,
                maxDistance,
                customer.getAddress());
    }

    public VendorOffer get(Long id) {
        return vendorOfferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Vendor Offer", id));
    }
}
