package com.project.fitnessfinder.controller;

import com.project.fitnessfinder.domain.entity.api.ServiceGroupJson;
import com.project.fitnessfinder.service.ServiceGroupService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-group")
@RequiredArgsConstructor
public class ServiceGroupController {

    private final ServiceGroupService serviceGroupService;

    @GetMapping("{serviceAreaId}")
    public List<ServiceGroupJson> findByServiceAreaId(@PathVariable Long serviceAreaId){
        return serviceGroupService.findByServiceAreaId(serviceAreaId);
    }
}
