package com.project.fitnessfinder.service;

import com.project.fitnessfinder.converter.Converter;
import com.project.fitnessfinder.domain.entity.api.LeadJson;
import com.project.fitnessfinder.domain.entity.database.Customer;
import com.project.fitnessfinder.domain.entity.database.Lead;
import com.project.fitnessfinder.domain.entity.database.Vendor;
import com.project.fitnessfinder.repository.LeadRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeadService {

    private final LeadRepository leadRepository;
    private final CustomerService customerService;
    private final VendorService vendorService;
    private final Converter converter;


    public void createLead(Long vendorId, Long customerId, boolean isStrongLead) {

        var customer = customerService.get(customerId);
        var vendor = vendorService.get(vendorId);


        var lead = leadRepository.getLeadByCustomerAndVendor(customer, vendor)
                .map(leadToUpdate -> updateIsStrongLead(leadToUpdate, isStrongLead))
                .orElse(buildNewLead(customer, vendor, isStrongLead));


        leadRepository.save(lead);
    }

    private Lead buildNewLead(Customer customer, Vendor vendor, boolean isStrongLead) {
        return Lead.builder()
                .customer(customer)
                .vendor(vendor)
                .strongLead(isStrongLead)
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
