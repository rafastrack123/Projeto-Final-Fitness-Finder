package com.project.fitnessfinder.domain.entity.api;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class LeadJson {

    public String customerFirstName;

    public String customerLastName;

    public String customerEmail;

    public String customerObjective;

    public ContactInfoJson customerContact;

    public boolean isStrongLead;

    public String vendorOfferDescription;
}
