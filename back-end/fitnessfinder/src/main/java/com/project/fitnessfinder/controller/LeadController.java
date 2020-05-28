package com.project.fitnessfinder.controller;

import com.project.fitnessfinder.domain.entity.api.LeadJson;
import com.project.fitnessfinder.service.LeadService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lead")
@RequiredArgsConstructor
public class LeadController {

    private final LeadService leadService;

    @GetMapping("/{vendorId}")
    public List<LeadJson> getAllLeadsByVendor(@PathVariable Long vendorId) {
        return leadService.getAllVendorLeads(vendorId);
    }

    @PostMapping("/{vendorOfferId}/{customerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void generateLead(@PathVariable Long vendorOfferId,
                             @PathVariable Long customerId,
                             @RequestParam(value = "isStrongLead", defaultValue = "false") boolean isStrongLead) {

            leadService.createLead(vendorOfferId, customerId, isStrongLead);
    }

}
