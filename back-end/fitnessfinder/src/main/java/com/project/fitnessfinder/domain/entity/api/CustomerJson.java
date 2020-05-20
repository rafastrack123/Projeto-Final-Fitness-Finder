package com.project.fitnessfinder.domain.entity.api;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class CustomerJson {

    public Long id;

    public String firstName;
    public String lastName;

    public String username;
    public String password;

    public ContactInfoJson contactInfo;

    public AddressJson address;

    public ObjectiveJson objective;

}
