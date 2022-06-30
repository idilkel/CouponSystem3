package com.jb.CouponSystem3.controllers;

import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.security.LoginRequest;
import com.jb.CouponSystem3.security.LoginResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public abstract class ClientController {

    public abstract LoginResponse login(LoginRequest loginRequest) throws CouponSystemException, CouponSecurityException;
}
