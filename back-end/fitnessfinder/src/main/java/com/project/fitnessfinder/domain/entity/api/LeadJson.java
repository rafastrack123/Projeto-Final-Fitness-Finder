package com.project.fitnessfinder.domain.entity.api;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class LeadJson {
    public Long id;

    public Long customerId;

    public String customerFirstName;

    public String customerLastName;

    public String customerEmail;

    public String customerObjective;

    public String preferredContact;

    public ContactInfoJson customerContact = new ContactInfoJson();

    public boolean isStrongLead;

    public Long vendorOfferId;

    public String vendorOfferDescription;

    public String updatedDate;
}
