package com.project.fitnessfinder.service;

import com.project.fitnessfinder.domain.entity.api.UserLoginJson;
import com.project.fitnessfinder.domain.entity.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final CustomerService customerService;
    private final VendorService vendorService;


    public UserType authenticate(UserLoginJson userLogin) {
        var vendorOptional = vendorService.findByEmailAndPassword(userLogin.email, userLogin.password);

        if (vendorOptional.isPresent()) {
            return UserType.Vendor;
        }

        var customerOptional = customerService.findByEmailAndPassword(userLogin.email, userLogin.password);

        if (customerOptional.isPresent()) {
            return UserType.Customer;
        }

        throw new RuntimeException();
    }
}
