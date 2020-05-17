package com.project.fitnessfinder.controller;

import com.project.fitnessfinder.domain.entity.api.AuthenticatedUser;
import com.project.fitnessfinder.domain.entity.api.UserLoginJson;
import com.project.fitnessfinder.domain.entity.enums.UserType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public AuthenticatedUser login(@RequestBody UserLoginJson userLogin) {

        if (userLogin.username.equals("cliente")) {
            return AuthenticatedUser.builder().userType(UserType.Customer).build();
        } else if (userLogin.username.equals("fornecedor")) {
            return AuthenticatedUser.builder().userType(UserType.Vendor).build();
        } else {
            throw new RuntimeException();
        }
    }
}
