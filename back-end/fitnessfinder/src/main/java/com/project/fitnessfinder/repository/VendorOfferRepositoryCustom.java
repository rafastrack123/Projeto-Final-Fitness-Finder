package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.api.VendorOfferJson;
import com.project.fitnessfinder.domain.entity.database.Address;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface VendorOfferRepositoryCustom {

    List<VendorOfferJson> searchVendorOffersByFilters(@NotNull Long serviceAreaId,
                                                      Long serviceGroupId,
                                                      Long serviceDetailId,
                                                      String vendorFirstName,
                                                      String vendorLastName,
                                                      BigDecimal maxPrice,
                                                      Boolean isHomeService,
                                                      Boolean isFirstClassFree,
                                                      Long maxDistanceInKm,
                                                      Address customerAddress);

}
