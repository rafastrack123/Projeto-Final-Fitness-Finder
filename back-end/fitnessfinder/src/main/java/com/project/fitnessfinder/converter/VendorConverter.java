package com.project.fitnessfinder.converter;

import com.project.fitnessfinder.domain.entity.api.VendorJson;
import com.project.fitnessfinder.domain.entity.database.Vendor;
import org.springframework.stereotype.Component;

@Component
public class VendorConverter {

    public VendorJson convert(Vendor vendor) {
        return null;
    }

    public Vendor convert(VendorJson vendor) {
        return null;
    }

    public Vendor convertSaved(Long id, VendorJson vendorJson) {
        var vendor = convert(vendorJson);
        vendor.setId(id);

        return vendor;
    }

}
