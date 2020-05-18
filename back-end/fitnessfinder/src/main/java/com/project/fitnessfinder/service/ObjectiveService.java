package com.project.fitnessfinder.service;

import com.project.fitnessfinder.converter.Converter;
import com.project.fitnessfinder.domain.entity.api.ObjectiveJson;
import com.project.fitnessfinder.domain.entity.database.Objective;
import com.project.fitnessfinder.exception.EntityNotFound;
import com.project.fitnessfinder.repository.ObjectiveRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ObjectiveService {

    private final ObjectiveRepository objectiveRepository;
    private final Converter converter;


    public List<ObjectiveJson> findAll() {
        return objectiveRepository.findAll()
                .stream()
                .map(converter::convert)
                .collect(Collectors.toList());

    }

    public Objective getObjectById(Long id) {
        return objectiveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Objective", id));
    }
}
