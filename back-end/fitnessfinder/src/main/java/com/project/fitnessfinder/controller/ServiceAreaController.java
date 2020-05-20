package com.project.fitnessfinder.controller;

import com.project.fitnessfinder.domain.entity.api.ServiceAreaJson;
import com.project.fitnessfinder.service.ServiceAreaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-area")
@RequiredArgsConstructor
public class ServiceAreaController {

    private final ServiceAreaService serviceAreaService;


    @GetMapping("find-all")
    public List<ServiceAreaJson> findAll(){
        return serviceAreaService.findAll();
    }

}
