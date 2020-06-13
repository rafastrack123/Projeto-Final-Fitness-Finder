package com.project.fitnessfinder.service;

import com.project.fitnessfinder.converter.Converter;
import com.project.fitnessfinder.domain.entity.api.LeadJson;
import com.project.fitnessfinder.domain.entity.database.Customer;
import com.project.fitnessfinder.domain.entity.database.Lead;
import com.project.fitnessfinder.domain.entity.database.VendorOffer;
import com.project.fitnessfinder.repository.LeadRepository;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeadService {

    private final LeadRepository leadRepository;
    private final CustomerService customerService;
    private final VendorOfferService vendorOfferService;
    private final Converter converter;


    public void createLead(Long vendorOfferId, Long customerId, boolean isStrongLead) {

        var customer = customerService.get(customerId);
        var vendorOffer = vendorOfferService.get(vendorOfferId);


        var lead = leadRepository.getLeadByCustomerAndVendor(customer, vendorOffer)
                .map(leadToUpdate -> updateIsStrongLead(leadToUpdate, isStrongLead))
                .orElse(buildNewLead(customer, vendorOffer, isStrongLead));

        lead.setUpdateDate(new Date());

        leadRepository.save(lead);
    }

    private Lead buildNewLead(Customer customer, VendorOffer vendorOffer, boolean isStrongLead) {
         return Lead.builder()
                .customer(customer)
                .vendorOffer(vendorOffer)
                .isStrongLead(isStrongLead)
                .build();
    }

    private Lead updateIsStrongLead(Lead lead, boolean isStrongLead) {

        if (!lead.isStrongLead() && isStrongLead) {
            lead.setStrongLead(true);
        }
        return lead;
    }


    public List<LeadJson> getAllVendorLeads(Long vendorId) {
        var leads = leadRepository.getAllLeadsByVendorId(vendorId);

        return converter.convert(leads);
    }

}
