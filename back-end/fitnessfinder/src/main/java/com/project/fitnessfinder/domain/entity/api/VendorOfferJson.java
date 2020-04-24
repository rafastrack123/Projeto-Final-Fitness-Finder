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

    public BigDecimal price;

    public boolean isHomeService;

    public boolean firstClassFree;

    public String vendorName;

    public String areaName;

    public String groupName;

    public String detailName;


}
