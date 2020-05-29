package com.project.fitnessfinder.service;

import com.project.fitnessfinder.converter.Converter;
import com.project.fitnessfinder.domain.entity.api.CreateVendorPropositionJson;
import com.project.fitnessfinder.domain.entity.api.VendorPropositionJson;
import com.project.fitnessfinder.domain.entity.database.VendorProposition;
import com.project.fitnessfinder.exception.EntityNotFound;
import com.project.fitnessfinder.repository.VendorPropositionRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendorPropositionService {

    private final Converter converter;
    private final VendorOfferService vendorOfferService;
    private final CustomerService customerService;
    private final VendorPropositionRepository vendorPropositionRepository;


    public List<VendorPropositionJson> getByCustomerId(Long customerId) {

        return vendorPropositionRepository.getByCustomerId(customerId)
                .stream()
                .map(converter::convert)
                .collect(Collectors.toList());


    }


    public void createVendorProposition(CreateVendorPropositionJson vendorPropositionJson) {
        var vendorOffer = vendorOfferService.get(vendorPropositionJson.vendorOfferId);

        var customer = customerService.get(vendorPropositionJson.customerId);

        var vendorProposition = new VendorProposition();

        vendorProposition.setVendorOffer(vendorOffer);

        vendorProposition.setCustomer(customer);

        vendorProposition.setMessage(vendorPropositionJson.message);

        vendorPropositionRepository.save(vendorProposition);

    }

    public void markAsViewed(Long id) {
        var vendorProposition = vendorPropositionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("vendor proposition", id));

        vendorProposition.setViewedByCustomer(true);

        vendorPropositionRepository.save(vendorProposition);

    }
}
