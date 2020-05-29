package com.project.fitnessfinder.controller;

import com.project.fitnessfinder.domain.entity.api.CreateVendorPropositionJson;
import com.project.fitnessfinder.domain.entity.api.VendorPropositionJson;
import com.project.fitnessfinder.service.VendorPropositionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/vendor-proposition")
public class VendorPropositionController {

    private final VendorPropositionService vendorPropositionService;

    @GetMapping("/{customerId}")
    public List<VendorPropositionJson> getByCustomerId(@PathVariable Long customerId) {
        return vendorPropositionService.getByCustomerId(customerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createVendorProposition(@RequestBody CreateVendorPropositionJson vendorPropositionJson) {
        vendorPropositionService.createVendorProposition(vendorPropositionJson);
    }

    @PutMapping("/{vendorPropositionId}")
    public void markAsViewed(@PathVariable Long vendorPropositionId) {
        vendorPropositionService.markAsViewed(vendorPropositionId);
    }
}
