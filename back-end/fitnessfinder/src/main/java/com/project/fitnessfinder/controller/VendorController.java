package com.project.fitnessfinder.controller;

import com.project.fitnessfinder.domain.entity.api.VendorJson;
import com.project.fitnessfinder.service.VendorService;
import com.project.fitnessfinder.validator.EmailValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vendor")
@RequiredArgsConstructor
public class VendorController {

    private final EmailValidator emailValidator;
    private final VendorService vendorService;

    @GetMapping("/{id}")
    public VendorJson getVendor(@PathVariable Long id) {
        return vendorService.getJson(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorJson postVendor(@RequestBody VendorJson vendorJson) {
        emailValidator.emailIsUnique(vendorJson.email);
        return vendorService.save(vendorJson);
    }

    @PutMapping("/{id}")
    public VendorJson putVendor(@PathVariable Long id,
                                @RequestBody VendorJson vendorJson) {
        return vendorService.update(id, vendorJson);
    }

}
