package com.project.fitnessfinder.controller;

import com.project.fitnessfinder.domain.entity.api.AvailableScheduleJson;
import com.project.fitnessfinder.service.AvailableScheduleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/available-schedule")
public class AvailableScheduleController {

    private final AvailableScheduleService availableScheduleService;

    @GetMapping("/vendor-offers/{vendorOfferId}")
    public List<AvailableScheduleJson> findByVendorOfferId(@PathVariable Long vendorOfferId) {
        return availableScheduleService.findByVendorOfferId(vendorOfferId);
    }
}
