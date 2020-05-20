package com.project.fitnessfinder.service;

import com.project.fitnessfinder.converter.Converter;
import com.project.fitnessfinder.domain.entity.api.ServiceGroupJson;
import com.project.fitnessfinder.repository.ServiceGroupRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceGroupService {

    private final ServiceGroupRepository serviceGroupRepository;
    private final Converter converter;

    public List<ServiceGroupJson> findByServiceAreaId(Long serviceAreaId) {
        return serviceGroupRepository.findByServiceAreaId(serviceAreaId)
                .stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}
