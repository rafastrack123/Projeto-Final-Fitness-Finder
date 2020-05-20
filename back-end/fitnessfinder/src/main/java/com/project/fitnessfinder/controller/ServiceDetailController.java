package com.project.fitnessfinder.controller;

import com.project.fitnessfinder.domain.entity.api.ServiceDetailJson;
import com.project.fitnessfinder.service.ServiceDetailService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-detail")
@RequiredArgsConstructor
public class ServiceDetailController {

    private final ServiceDetailService serviceDetailService;

    @GetMapping("/{serviceGroupId}")
    public List<ServiceDetailJson> findByServiceGroupId(@PathVariable Long serviceGroupId) {
        return serviceDetailService.findByServiceGroupId(serviceGroupId);
    }

}
