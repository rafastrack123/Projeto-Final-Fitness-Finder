package com.project.fitnessfinder.service;

import com.project.fitnessfinder.domain.entity.database.VendorOffer;
import com.project.fitnessfinder.repository.VendorOfferRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendorOfferService {

    private final VendorOfferRepository vendorOfferRepository;


    public List<VendorOffer> searchVendorOffers(Long serviceAreaId,
                                                Long serviceGroupId,
                                                Long serviceDetailId,
                                                BigDecimal maxPrice,
                                                String vendorName,//TODO: Check if works on JPA
                                                boolean isHomeService,
                                                boolean firstClassFree){
        return  null;
    }
}
