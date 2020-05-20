package com.project.fitnessfinder.converter;

import com.project.fitnessfinder.domain.entity.api.AddressJson;
import com.project.fitnessfinder.domain.entity.api.ContactInfoJson;
import com.project.fitnessfinder.domain.entity.api.CustomerJson;
import com.project.fitnessfinder.domain.entity.api.LeadJson;
import com.project.fitnessfinder.domain.entity.api.ObjectiveJson;
import com.project.fitnessfinder.domain.entity.api.ServiceAreaJson;
import com.project.fitnessfinder.domain.entity.api.ServiceDetailJson;
import com.project.fitnessfinder.domain.entity.api.ServiceGroupJson;
import com.project.fitnessfinder.domain.entity.api.VendorJson;
import com.project.fitnessfinder.domain.entity.api.VendorOfferJson;
import com.project.fitnessfinder.domain.entity.database.Address;
import com.project.fitnessfinder.domain.entity.database.ContactInfo;
import com.project.fitnessfinder.domain.entity.database.Customer;
import com.project.fitnessfinder.domain.entity.database.Lead;
import com.project.fitnessfinder.domain.entity.database.Objective;
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

    public CustomerJson convert(Customer customer) {
        var customerJson = new CustomerJson();

        customerJson.id = customer.getId();

        customerJson.firstName = customer.getFirstName();
        customerJson.lastName = customer.getLastName();

        customerJson.username = customer.getUsername();

        customerJson.address = convert(customer.getAddress());

        customerJson.contactInfo = convert(customer.getContactInfo());

        customerJson.objective = convert(customer.getObjective());

        return customerJson;
    }

    public ObjectiveJson convert(Objective objective) {
        var objectiveJson = new ObjectiveJson();

        objectiveJson.id = objective.getId();
        objectiveJson.name = objective.getName();

        return objectiveJson;
    }

    public Customer convert(Customer customer, CustomerJson updatedCustomerJson) {
        customer.setFirstName(updatedCustomerJson.firstName);
        customer.setLastName(updatedCustomerJson.lastName);

        customer.setUsername(updatedCustomerJson.username);
        customer.setPassword(updatedCustomerJson.password);

        customer.setAddress(convert(updatedCustomerJson.address));
        customer.setContactInfo(convert(updatedCustomerJson.contactInfo));

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


        json.vendorOfferDescription = buildVendorOfferDescription(lead);

        json.customerContact = convert(lead.getCustomer().getContactInfo());

        return json;
    }

    private String buildVendorOfferDescription(Lead lead) {
        var detail = lead.getVendorOffer().getServiceDetail();
        var group = detail.getServiceGroup();
        var area = group.getServiceArea();

        return area.getName() + " - " + group.getName() + " - " + detail.getName();
    }

    public VendorOfferJson convert(VendorOffer vendorOffer) {
        var json = new VendorOfferJson();

        json.id = vendorOffer.getId();
        json.price = vendorOffer.getPrice();
        json.isHomeService = vendorOffer.isHomeService();
        json.firstClassFree = vendorOffer.isFirstClassFree();

        json.vendorFirstName = vendorOffer.getVendor().getFirstName();
        json.vendorLastName = vendorOffer.getVendor().getLastName();

        var detail = vendorOffer.getServiceDetail();
        var group = detail.getServiceGroup();
        json.groupName = group.getName();
        json.detailName = detail.getName();
        json.areaName = group.getServiceArea().getName();


        return json;
    }

    public ServiceAreaJson convert(ServiceArea serviceArea) {
        return ServiceAreaJson.builder()
                .id(serviceArea.getId())
                .name(serviceArea.getName())
                .build();
    }

    public ServiceGroupJson convert(ServiceGroup serviceGroup) {
        return ServiceGroupJson.builder()
                .id(serviceGroup.getId())
                .name(serviceGroup.getName())
                .build();
    }

    public ServiceDetailJson convert(ServiceDetail serviceDetail) {
        return ServiceDetailJson.builder()
                .id(serviceDetail.getId())
                .name(serviceDetail.getName())
                .build();
    }

    public VendorJson convert(Vendor vendor) {
        var vendorJson = new VendorJson();

        vendorJson.id = vendor.getId();

        vendorJson.firstName = vendor.getFirstName();
        vendorJson.lastName = vendor.getLastName();

        vendorJson.username = vendor.getUsername();

        vendorJson.address = convert(vendor.getAddress());

        vendorJson.contactInfo = convert(vendor.getContactInfo());

        return vendorJson;
    }

    public AddressJson convert(Address address) {
        var addressJson = new AddressJson();

        addressJson.fullAddress = address.getFullAddress();

        addressJson.latitude = address.getLatitude();
        addressJson.longitude = address.getLongitude();

        return addressJson;
    }

    public ContactInfoJson convert(ContactInfo contact) {
        var contactJson = new ContactInfoJson();

        contactJson.setCellphone(contact.getCellphone());
        contactJson.setEmail(contact.getEmail());
        contactJson.setLinkToFacebook(contact.getLinkToFacebook());

        return contactJson;
    }

    public Vendor convertSaved(Vendor vendor, VendorJson updatedVendorJson) {

        vendor.setFirstName(updatedVendorJson.firstName);
        vendor.setLastName(updatedVendorJson.lastName);

        vendor.setUsername(updatedVendorJson.username);
        vendor.setPassword(updatedVendorJson.password);

        vendor.setAddress(convert(updatedVendorJson.address));

        vendor.setContactInfo(convert(updatedVendorJson.contactInfo));

        return vendor;
    }

    public ContactInfo convert(ContactInfoJson contactInfoJson) {
        var contact = new ContactInfo();

        contact.setEmail(contactInfoJson.email);
        contact.setCellphone(contactInfoJson.cellphone);
        contact.setLinkToFacebook(contactInfoJson.linkToFacebook);

        return contact;
    }

    public Address convert(AddressJson addressJson) {
        var address = new Address();

        address.setFullAddress(addressJson.fullAddress);

        address.setLongitude(addressJson.longitude);
        address.setLatitude(addressJson.latitude);

        return address;
    }

}
