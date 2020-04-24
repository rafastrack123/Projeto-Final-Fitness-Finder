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

    public VendorJson update(Long id, VendorJson vendorJson) {
        var vendorToUpdate = get(id);

        var consumer = converter.convertSaved(vendorToUpdate.getId(), vendorJson);
        vendorRepository.save(consumer);

        return vendorJson;
    }

    public VendorJson save(VendorJson vendorJson) {
        var vendor = converter.convert(vendorJson);

        var saved = vendorRepository.save(vendor);

        vendorJson.setId(saved.getId());
        return vendorJson;
    }
}
