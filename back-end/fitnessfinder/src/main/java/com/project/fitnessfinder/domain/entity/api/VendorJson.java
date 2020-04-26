package com.project.fitnessfinder.domain.entity.api;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class VendorJson {

    public Long id;

    public String firstName;
    public String lastName;

    public String username;
    public String password;

    public ContactInfoJson contactInfo;

    public AddressJson address;

}
