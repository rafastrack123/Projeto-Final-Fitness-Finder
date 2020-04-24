package com.project.fitnessfinder.converter;

import com.project.fitnessfinder.domain.entity.api.ContactJson;
import com.project.fitnessfinder.domain.entity.api.CustomerJson;
import com.project.fitnessfinder.domain.entity.api.LeadJson;
import com.project.fitnessfinder.domain.entity.api.ServiceAreaJson;
import com.project.fitnessfinder.domain.entity.api.ServiceDetailJson;
import com.project.fitnessfinder.domain.entity.api.ServiceGroupJson;
import com.project.fitnessfinder.domain.entity.api.VendorJson;
import com.project.fitnessfinder.domain.entity.api.VendorOfferJson;
import com.project.fitnessfinder.domain.entity.database.ContactInfo;
import com.project.fitnessfinder.domain.entity.database.Customer;
import com.project.fitnessfinder.domain.entity.database.Lead;
import com.project.fitnessfinder.domain.entity.database.ServiceArea;
import com.project.fitnessfinder.domain.entity.database.ServiceDetail;
import com.project.fitnessfinder.domain.entity.database.ServiceGroup;
import com.project.fitnessfinder.domain.entity.database.Vendor;
import com.project.fitnessfinder.domain.entity.database.VendorOffer;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    public ContactJson convert(ContactInfo contact) {
        var contactJson = new ContactJson();

        contactJson.setCellphone(contact.getCellphone());
        contactJson.setEmail(contact.getEmail());
        contactJson.setLinkToFacebook(contact.getLinkToFacebook());

        return contactJson;
    }

    public CustomerJson convert(Customer customer) {
        return null;
    }

    public Customer convert(CustomerJson customer) {
        return null;
    }

    public Customer convertSaved(Long id, CustomerJson customerJson) {
        var customer = convert(customerJson);
        customer.setId(id);

        return customer;
    }

    public List<LeadJson> convert(List<Lead> leads) {


        return leads.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public LeadJson convert(Lead lead) {
        var json = new LeadJson();

        json.customerFirstName = lead.getCustomer().getFirstName();
        json.customerLastName = lead.getCustomer().getLastName();

        json.isStrongLead = lead.isStrongLead();

        json.customerContact = convert(lead.getCustomer().getContactInfo());

        return json;
    }

    public VendorOfferJson convert(VendorOffer vendorOffer) {
        var json = new VendorOfferJson();

        json.id = vendorOffer.getId();
        json.price = vendorOffer.getPrice();
        json.isHomeService = vendorOffer.isHomeService();
        json.firstClassFree = vendorOffer.isFirstClassFree();
        json.vendorName = vendorOffer.getVendor().getFirstName();

        var detail = vendorOffer.getServiceDetail();
        var group = detail.getServiceGroup();
        json.groupName = group.getName();
        json.detailName = detail.getName();
        json.areaName = group.getServiceArea().getName();


        return json;
    }

    public ServiceAreaJson convert(ServiceArea serviceArea) {
        var json = new ServiceAreaJson();

        json.id = serviceArea.getId();
        json.name = serviceArea.getName();

        return json;
    }

    public ServiceGroupJson convert(ServiceGroup serviceGroup) {
        var json = new ServiceGroupJson();

        json.id = serviceGroup.getId();
        json.name = serviceGroup.getName();

        return json;
    }

    public ServiceDetailJson convert(ServiceDetail serviceDetail) {
        var json = new ServiceDetailJson();

        json.id = serviceDetail.getId();
        json.name = serviceDetail.getName();

        return json;
    }

    public VendorJson convert(Vendor vendor) {
        return null;
    }

    public Vendor convert(VendorJson vendor)  {
        return null;
    }

    public Vendor convertSaved(Long id, VendorJson vendorJson) {
        var vendor = convert(vendorJson);
        vendor.setId(id);

        return vendor;
    }
}
