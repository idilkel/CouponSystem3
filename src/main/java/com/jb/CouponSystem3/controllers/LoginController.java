package com.jb.CouponSystem3.controllers;

import com.jb.CouponSystem3.security.ClientType;
import com.jb.CouponSystem3.security.LoginManager;
import com.jb.CouponSystem3.security.LoginRequest;
import com.jb.CouponSystem3.security.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("api/login")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LoginController {
    private final LoginManager loginManager;

//    @PostMapping
//    public void login1(@Valid @RequestBody LoginRequest loginRequest) {
//        String email = loginRequest.getEmail();
//        String password = loginRequest.getPassword();
//        ClientType type = loginRequest.getType();
//        loginManager.login(email, password, type);
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        ClientType type = loginRequest.getType();
        UUID token = loginManager.loginUUID(email, password, type);
        return new LoginResponse(token);
    }
}
