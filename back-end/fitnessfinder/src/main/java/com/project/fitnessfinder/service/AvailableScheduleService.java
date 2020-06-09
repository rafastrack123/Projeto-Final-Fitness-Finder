package com.project.fitnessfinder.service;

import com.project.fitnessfinder.converter.Converter;
import com.project.fitnessfinder.domain.entity.api.AvailableScheduleJson;
import com.project.fitnessfinder.domain.entity.database.AvailableSchedule;
import com.project.fitnessfinder.repository.AvailableScheduleRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvailableScheduleService {

    private final AvailableScheduleRepository availableScheduleRepository;
    private final Converter converter;

    public List<AvailableScheduleJson> findByVendorOfferId(Long vendorOfferId) {
        return availableScheduleRepository.findByVendorOfferId(vendorOfferId)
                .stream()
                .map(converter::convert)
                .collect(Collectors.toList());

    }
}
