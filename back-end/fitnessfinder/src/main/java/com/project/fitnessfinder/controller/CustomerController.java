package com.project.fitnessfinder.controller;

import com.project.fitnessfinder.domain.entity.api.CustomerJson;
import com.project.fitnessfinder.service.CustomerService;
import com.project.fitnessfinder.validator.EmailValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final EmailValidator emailValidator;

    @GetMapping("/{id}")
    public CustomerJson getCustomer(@PathVariable Long id) {
        return customerService.getJson(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerJson postCustomer(@RequestBody CustomerJson customerJson) {
        emailValidator.emailIsUnique(customerJson.email);

        return customerService.save(customerJson);
    }

    @PutMapping("/{id}")
    public CustomerJson putCustomer(@PathVariable Long id, @RequestBody CustomerJson customerJson) {
        return customerService.update(id, customerJson);
    }
}
