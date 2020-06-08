package com.project.fitnessfinder.converter;

import com.project.fitnessfinder.domain.entity.api.AddressJson;
import com.project.fitnessfinder.domain.entity.api.AvailableScheduleJson;
import com.project.fitnessfinder.domain.entity.api.ContactInfoJson;
import com.project.fitnessfinder.domain.entity.api.CustomerJson;
import com.project.fitnessfinder.domain.entity.api.LeadJson;
import com.project.fitnessfinder.domain.entity.api.ObjectiveJson;
import com.project.fitnessfinder.domain.entity.api.ServiceAreaJson;
import com.project.fitnessfinder.domain.entity.api.ServiceDetailJson;
import com.project.fitnessfinder.domain.entity.api.ServiceGroupJson;
import com.project.fitnessfinder.domain.entity.api.VendorJson;
import com.project.fitnessfinder.domain.entity.api.VendorOfferJson;
import com.project.fitnessfinder.domain.entity.api.VendorPropositionJson;
import com.project.fitnessfinder.domain.entity.api.VendorResumeJson;
import com.project.fitnessfinder.domain.entity.database.Address;
import com.project.fitnessfinder.domain.entity.database.AvailableSchedule;
import com.project.fitnessfinder.domain.entity.database.ContactInfo;
import com.project.fitnessfinder.domain.entity.database.Customer;
import com.project.fitnessfinder.domain.entity.database.Lead;
import com.project.fitnessfinder.domain.entity.database.Objective;
import com.project.fitnessfinder.domain.entity.database.ServiceArea;
import com.project.fitnessfinder.domain.entity.database.ServiceDetail;
import com.project.fitnessfinder.domain.entity.database.ServiceGroup;
import com.project.fitnessfinder.domain.entity.database.Vendor;
import com.project.fitnessfinder.domain.entity.database.VendorOffer;
import com.project.fitnessfinder.domain.entity.database.VendorProposition;
import java.sql.Time;
import java.time.DayOfWeek;
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

        customerJson.email = customer.getEmail();

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

        customer.setEmail(updatedCustomerJson.email);
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
        json.id = lead.getId();

        json.customerId = lead.getCustomer().getId();
        json.customerFirstName = lead.getCustomer().getFirstName();

        json.vendorOfferId = lead.getVendorOffer().getId();
        json.vendorOfferDescription = buildVendorOfferDescription(lead);

        json.customerObjective = lead.getCustomer().getObjective().getName();

        json.isStrongLead = lead.isStrongLead();

        json.date = lead.getUpdateDate();

        if (lead.isStrongLead()) {
            json.customerLastName = lead.getCustomer().getLastName();
            json.customerEmail = lead.getCustomer().getEmail();
            json.customerContact = convert(lead.getCustomer().getContactInfo());
        }

        return json;
    }

    private String buildVendorOfferDescription(Lead lead) {
        var detail = lead.getVendorOffer().getServiceDetail();
        var group = detail.getServiceGroup();
//        var area = group.getServiceArea();

//        return area.getName() + " - " + group.getName() + " - " + detail.getName();
        return group.getName() + " " + detail.getName();
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

        vendorJson.email = vendor.getEmail();

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

        contactJson.setLinkToFacebook(contact.getLinkToFacebook());

        return contactJson;
    }

    public Vendor convertSaved(Vendor vendor, VendorJson updatedVendorJson) {

        vendor.setFirstName(updatedVendorJson.firstName);
        vendor.setLastName(updatedVendorJson.lastName);

        vendor.setEmail(updatedVendorJson.email);
        vendor.setPassword(updatedVendorJson.password);

        vendor.setAddress(convert(updatedVendorJson.address));

        vendor.setContactInfo(convert(updatedVendorJson.contactInfo));

        return vendor;
    }

    public ContactInfo convert(ContactInfoJson contactInfoJson) {
        var contact = new ContactInfo();

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

    public VendorPropositionJson convert(VendorProposition vendorProposition) {
        var serviceDetail = vendorProposition.getVendorOffer().getServiceDetail();

        var serviceGroup = serviceDetail.getServiceGroup();

        var serviceDescription = serviceGroup.getName().concat(" ")
                .concat(serviceDetail.getName());

        var vendor = vendorProposition.getVendorOffer().getVendor();

        return VendorPropositionJson.builder()
                .id(vendorProposition.getId())
                .customerId(vendorProposition.getCustomer().getId())
                .serviceDescription(serviceDescription)
                .message(vendorProposition.getMessage())
                .viewedByCustomer(vendorProposition.isViewedByCustomer())
                .vendorOfferId(vendorProposition.getVendorOffer().getId())
                .vendorFirstName(vendor.getFirstName())
                .vendorLastName(vendor.getLastName())
                .build();

    }

    public VendorResumeJson convertVendorToResume(Vendor vendor) {
        return VendorResumeJson.builder()
                .id(vendor.getId())
                .resume(vendor.getResume())
                .build();
    }

    public String convert(DayOfWeek dayOfWeek) {

        switch (dayOfWeek) {
            case MONDAY:
                return "Segunda-Feira";
            case TUESDAY:
                return "Terça-Feira";
            case WEDNESDAY:
                return "Quarta-Feira";
            case THURSDAY:
                return "Quinta-Feira";
            case FRIDAY:
                return "Sexta-Feira";
            case SATURDAY:
                return "Sábado";
            case SUNDAY:
                return "Domingo";
            default:
                throw new RuntimeException();
        }
    }

    public DayOfWeek convert(String dayOfWeekPtBr) {
        switch (dayOfWeekPtBr) {
            case "Segunda-Feira":
                return DayOfWeek.MONDAY;
            case "Terça-Feira":
                return DayOfWeek.TUESDAY;
            case "Quarta-Feira":
                return DayOfWeek.WEDNESDAY;
            case "Quinta-Feira":
                return DayOfWeek.THURSDAY;
            case "Sexta-Feira":
                return DayOfWeek.FRIDAY;
            case "Sábado":
                return DayOfWeek.SATURDAY;
            case "Domingo":
                return DayOfWeek.SUNDAY;
            default:
                throw new RuntimeException();
        }
    }

    public int convertDayInPtBrToInt(String dayOfWeekPtBr) {
        switch (dayOfWeekPtBr) {
            case "Segunda-Feira":
                return 1;
            case "Terça-Feira":
                return 2;
            case "Quarta-Feira":
                return 3;
            case "Quinta-Feira":
                return 4;
            case "Sexta-Feira":
                return 5;
            case "Sábado":
                return 6;
            case "Domingo":
                return 7;
            default:
                throw new RuntimeException();
        }
    }

    public AvailableScheduleJson convert(AvailableSchedule availableSchedule) {

        return AvailableScheduleJson.builder()
                .id(availableSchedule.getId())
                .dayOfWeek(convert(availableSchedule.getDayOfWeek()))
                .startTime(availableSchedule.getStartTime().toString())
                .endTime(availableSchedule.getEndTime().toString())
                .build();
    }

    public AvailableSchedule convert(AvailableScheduleJson availableScheduleJson) {
        var availableSchedule = new AvailableSchedule();

        availableSchedule.setDayOfWeek(convert(availableScheduleJson.dayOfWeek));


        var startTime = addSeconds(availableScheduleJson.startTime);
        var endTime = addSeconds(availableScheduleJson.endTime);

        availableSchedule.setStartTime(Time.valueOf(startTime));
        availableSchedule.setEndTime(Time.valueOf(endTime));

        return availableSchedule;
    }

    public String addSeconds(String time) {
        if (time.length() == 5) {
            return time.concat(":00");
        }
        return time;
    }

    public VendorOfferJson convert(VendorOffer vendorOffer) {
        var vendorOfferJson = new VendorOfferJson();

        var vendor = vendorOffer.getVendor();

        vendorOfferJson.id = vendorOffer.getId();

        // Vendor data
        vendorOfferJson.vendorId = vendor.getId();
        vendorOfferJson.vendorFirstName = vendor.getFirstName();
        vendorOfferJson.vendorLastName = vendor.getLastName();


        // Offer Data
        vendorOfferJson.isHomeService = vendorOffer.isHomeService();
        vendorOfferJson.firstClassFree = vendorOffer.isFirstClassFree();
        vendorOfferJson.isRemoteService = vendorOffer.isRemoteService();

        vendorOfferJson.price = vendorOffer.getPrice();

        vendorOfferJson.serviceDescription = vendorOffer.getServiceDescription();

        //Services detail
        var serviceDetail = vendorOffer.getServiceDetail();

        vendorOfferJson.serviceDetailId = serviceDetail.getId();
        vendorOfferJson.detailName = serviceDetail.getName();

        //Services group
        var serviceGroup = serviceDetail.getServiceGroup();

        vendorOfferJson.serviceGroupId = serviceGroup.getId();
        vendorOfferJson.groupName = serviceGroup.getName();

        //Services area
        var serviceArea = serviceGroup.getServiceArea();

        vendorOfferJson.serviceAreaId = serviceArea.getId();
        vendorOfferJson.areaName = serviceArea.getName();


        // Available schedule
        vendorOfferJson.setAvailableSchedule(vendorOffer.getAvailableSchedule().stream().map(this::convert)
                .collect(Collectors.toList()));


        return vendorOfferJson;
    }

    public VendorOffer convert(VendorOfferJson json, Vendor vendor, ServiceDetail serviceDetail) {
        var vendorOffer = new VendorOffer();


        vendorOffer.setPrice(json.price);
        vendorOffer.setServiceDescription(json.serviceDescription);

        vendorOffer.setFirstClassFree(json.firstClassFree);
        vendorOffer.setRemoteService(json.isRemoteService);
        vendorOffer.setHomeService(json.isHomeService);

        // Entities
        vendorOffer.setAvailableSchedule(json.availableSchedule.stream().map(this::convert).collect(Collectors.toList()));
        vendorOffer.setVendor(vendor);
        vendorOffer.setServiceDetail(serviceDetail);

        return vendorOffer;
    }

}
