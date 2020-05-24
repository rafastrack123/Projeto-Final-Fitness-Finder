package com.project.fitnessfinder.domain.entity.api;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class VendorOfferJson {

    public Long id;

    public boolean firstClassFree;

    public boolean isHomeService;

    public boolean isRemoteService;

    public BigDecimal price;

    public String serviceDescription;

    public String vendorFirstName;

    public String vendorLastName;

    public String areaName;

    public String groupName;

    public String detailName;

    public Double distance;


}
