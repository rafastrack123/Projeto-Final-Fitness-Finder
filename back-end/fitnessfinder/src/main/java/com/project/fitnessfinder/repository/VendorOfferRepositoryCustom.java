package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.VendorOffer;
import java.math.BigDecimal;
import java.util.List;

public interface VendorOfferRepositoryCustom {

    List<VendorOffer> searchVendorOffersByFilter(Long serviceAreaId,
                                                 Long serviceGroupId,
                                                 Long serviceDetailId,
                                                 BigDecimal maxPrice,
                                                 String vendorName,
                                                 boolean isHomeService,
                                                 boolean firstClassFree);

}
