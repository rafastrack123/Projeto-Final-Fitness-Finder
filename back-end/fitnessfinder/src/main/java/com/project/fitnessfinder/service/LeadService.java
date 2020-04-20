package com.project.fitnessfinder.service;

import com.project.fitnessfinder.converter.LeadConverter;
import com.project.fitnessfinder.domain.entity.api.LeadJson;
import com.project.fitnessfinder.domain.entity.database.Lead;
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
    private final LeadConverter converter;

    public void createLead(Long vendorId, Long customerId, boolean isStrongLead) {

        var customer = customerService.get(customerId);
        var vendor = vendorService.get(vendorId);

        var lead = Lead.builder()
                .customer(customer)
                .vendor(vendor)
                .strongLead(isStrongLead)
                .build();

        leadRepository.save(lead);
    }

    public List<LeadJson> getAllVendorLeads(Long vendorId) {
        var leads = leadRepository.getAllLeadsByVendorId(vendorId);

        return converter.convert(leads);
    }

}
