package com.project.fitnessfinder.service;

import static java.util.stream.Collectors.toList;

import com.project.fitnessfinder.converter.Converter;
import com.project.fitnessfinder.domain.entity.api.ServiceDetailJson;
import com.project.fitnessfinder.repository.ServiceDetailRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceDetailService {

    private final ServiceDetailRepository serviceDetailRepository;
    private final Converter converter;

    public List<ServiceDetailJson> findByServiceGroupId(Long serviceGroupId){
        return serviceDetailRepository.findByServiceGroupId(serviceGroupId)
                .stream()
                .map(converter::convert)
                .collect(toList());
    }

}
