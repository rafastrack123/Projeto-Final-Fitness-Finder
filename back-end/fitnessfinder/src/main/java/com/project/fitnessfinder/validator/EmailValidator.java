package com.project.fitnessfinder.validator;

import com.project.fitnessfinder.service.CustomerService;
import com.project.fitnessfinder.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailValidator {

    private final CustomerService customerService;
    private final VendorService vendorService;

    public void emailIsUnique(String email) {
        customerService.findByEmail(email)
                .ifPresent(customer -> {
                    throw new RuntimeException("E-mail is already being used");
                });

        vendorService.findByEmail(email)
                .ifPresent(customer -> {
                    throw new RuntimeException("E-mail is already being used");
                });
    }

    public void isValidEmail(String email) {
        // TODO
    }

}
