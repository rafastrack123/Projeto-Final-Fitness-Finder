package com.project.fitnessfinder.service;

import com.project.fitnessfinder.domain.entity.api.AuthenticatedUser;
import com.project.fitnessfinder.domain.entity.api.UserLoginJson;
import com.project.fitnessfinder.domain.entity.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final CustomerService customerService;
    private final VendorService vendorService;


    public AuthenticatedUser authenticate(UserLoginJson userLogin) {
        var vendorOptional = vendorService.findByEmailAndPassword(userLogin.email, userLogin.password);

        if (vendorOptional.isPresent()) {

            return AuthenticatedUser.builder()
                    .userType(UserType.Vendor)
                    .userId(vendorOptional.get().getId())
                    .build();

        }

        var customerOptional = customerService.findByEmailAndPassword(userLogin.email, userLogin.password);

        if (customerOptional.isPresent()) {
            return AuthenticatedUser.builder()
                    .userType(UserType.Customer)
                    .userId(customerOptional.get().getId())
                    .build();
        }

        throw new RuntimeException();
    }
}
