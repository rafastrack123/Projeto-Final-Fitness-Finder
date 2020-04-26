package com.project.fitnessfinder.service;

import com.project.fitnessfinder.domain.entity.database.Objective;
import com.project.fitnessfinder.exception.EntityNotFound;
import com.project.fitnessfinder.repository.ObjectiveRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ObjectiveService {

    private final ObjectiveRepository objectiveRepository;


    public List<Objective> findAllObjectives(){
        return objectiveRepository.findAll();
    }

    public Objective getObjectById(Long id){
        return objectiveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Objective", id));
    }
}
