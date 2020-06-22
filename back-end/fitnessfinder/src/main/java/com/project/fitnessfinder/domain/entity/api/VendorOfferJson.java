package com.project.fitnessfinder.domain.entity.api;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class VendorOfferJson {

    public Long id;

    public BigDecimal price;

    public boolean homeService;

    public boolean remoteService;

    public boolean firstClassFree;

    public String serviceDescription;

    public Long vendorId;

    public String vendorFirstName;
    public String vendorLastName;

    public String resume;

    public Long serviceAreaId;
    public String areaName;

    public Long serviceGroupId;
    public String groupName;

    public Long serviceDetailId;
    public String detailName;

    public Double distance;

    public String imageUrl;

    public List<AvailableScheduleJson> availableSchedule;

}
