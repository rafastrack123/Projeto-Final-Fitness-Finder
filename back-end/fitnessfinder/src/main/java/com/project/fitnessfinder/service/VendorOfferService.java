package com.project.fitnessfinder.service;

import com.project.fitnessfinder.converter.Converter;
import com.project.fitnessfinder.domain.entity.api.VendorOfferJson;
import com.project.fitnessfinder.domain.entity.database.VendorOffer;
import com.project.fitnessfinder.exception.EntityNotFound;
import com.project.fitnessfinder.repository.VendorOfferRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendorOfferService {

    private final Converter converter;
    private final CustomerService customerService;
    private final ServiceDetailService serviceDetailService;
    private final VendorService vendorService;
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
                                                    Boolean isRemoteService,
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
                isRemoteService,
                maxDistance,
                customer.getAddress());
    }

    public VendorOffer get(Long id) {
        return vendorOfferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Vendor Offer", id));
    }

    public List<VendorOfferJson> findByVendorId(Long vendorId) {
        return vendorOfferRepository.findByVendorId(vendorId)
                .stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public void createVendorOffer(VendorOfferJson vendorOfferJson) {

        var serviceDetail = serviceDetailService.getById(vendorOfferJson.serviceDetailId);

        var vendor = vendorService.get(vendorOfferJson.vendorId);

        var vendorOffer = converter.convert(vendorOfferJson, vendor, serviceDetail);

        vendorOfferRepository.save(vendorOffer);
    }

    public void deleteById(Long id){
        vendorOfferRepository.deleteById(id);
    }

}
