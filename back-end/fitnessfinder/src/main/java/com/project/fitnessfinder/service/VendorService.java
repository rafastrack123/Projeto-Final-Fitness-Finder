package com.project.fitnessfinder.service;

import com.project.fitnessfinder.converter.Converter;
import com.project.fitnessfinder.domain.entity.api.VendorJson;
import com.project.fitnessfinder.domain.entity.database.Vendor;
import com.project.fitnessfinder.exception.EntityNotFound;
import com.project.fitnessfinder.repository.VendorRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;
    private final Converter converter;

    public Vendor get(Long id) {
        return this.vendorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Vendor", id));
    }

    public Optional<Vendor> find(Long id) {
        return this.vendorRepository.findById(id);
    }

    public VendorJson getJson(Long id) {
        var vendor = get(id);

        return converter.convert(vendor);
    }

    public VendorJson save(VendorJson vendorJson) {

        var vendor = converter.convertSaved(new Vendor(), vendorJson);

        vendor = vendorRepository.save(vendor);

        vendorJson.id = vendor.getId();

        return vendorJson;
    }

    public VendorJson update(Long id, VendorJson updatedVendorJson) {
        var oldVendor = get(id);

        var vendor = converter.convertSaved(oldVendor, updatedVendorJson);

        vendorRepository.save(vendor);

        return updatedVendorJson;
    }

}
