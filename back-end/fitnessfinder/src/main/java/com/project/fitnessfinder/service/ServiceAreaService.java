package com.project.fitnessfinder.service;

import com.project.fitnessfinder.converter.Converter;
import com.project.fitnessfinder.domain.entity.api.ServiceAreaJson;
import com.project.fitnessfinder.domain.entity.database.ServiceArea;
import com.project.fitnessfinder.exception.EntityNotFound;
import com.project.fitnessfinder.repository.ServiceAreaRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceAreaService {

    private final ServiceAreaRepository serviceAreaRepository;
    private final Converter converter;

    public ServiceArea getById(Long id) {
        return serviceAreaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Service Area", id));
    }

    public List<ServiceAreaJson> findAll() {
        return serviceAreaRepository.findAll()
                .stream()
                .map(converter::convert)
                .collect(Collectors.toList());

    }

}
