package com.project.fitnessfinder.controller;

import com.project.fitnessfinder.domain.entity.api.ObjectiveJson;
import com.project.fitnessfinder.service.ObjectiveService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/objective")
public class ObjectiveController {

    private final ObjectiveService objectiveService;

    @GetMapping("/findAll")
    public List<ObjectiveJson> findAll(){

         var x = objectiveService.findAll();

         return x;
    }


}
