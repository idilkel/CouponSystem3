package com.jb.CouponSystem3.controllers;

import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.SecMsg;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {
    private final LoginManager loginManager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) throws CouponSecurityException {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        ClientType type = loginRequest.getType();
        UUID token = loginManager.loginUUID(email, password, type);
        if (token == null) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return new LoginResponse(token, email, type);
    }
}
