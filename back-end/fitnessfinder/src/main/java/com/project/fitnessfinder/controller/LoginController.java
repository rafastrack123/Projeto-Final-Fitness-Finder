package com.project.fitnessfinder.controller;

import com.project.fitnessfinder.domain.entity.api.AuthenticatedUser;
import com.project.fitnessfinder.domain.entity.api.UserLoginJson;
import com.project.fitnessfinder.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public AuthenticatedUser login(@RequestBody UserLoginJson userLogin) {

        return loginService.authenticate(userLogin);

    }
}
