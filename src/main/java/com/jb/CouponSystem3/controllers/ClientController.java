package com.jb.CouponSystem3.controllers;

import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.security.LoginRequest;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class ClientController {

    public abstract boolean login(LoginRequest loginRequest) throws CouponSystemException, CouponSecurityException;
}
