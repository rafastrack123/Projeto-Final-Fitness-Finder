package com.project.fitnessfinder.service;

import com.project.fitnessfinder.domain.entity.database.ServiceArea;
import com.project.fitnessfinder.exception.EntityNotFound;
import com.project.fitnessfinder.repository.ServiceAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceAreaService {

    private final ServiceAreaRepository serviceAreaRepository;

    public ServiceArea getById(Long id) {
        return serviceAreaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Service Area", id));
    }

}
