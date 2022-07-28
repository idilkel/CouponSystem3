package com.jb.CouponSystem3.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SecMsg {
    EMAIL_ALREADY_EXISTS("Email already exists", HttpStatus.CONFLICT),
    EMAIL_OR_PASSWORD_INCORRECT("Incorrect email or password or user type", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("Invalid token. Please login again or wrong usertype", HttpStatus.UNAUTHORIZED);

    private String msg;
    private HttpStatus status;

    SecMsg(String msg, HttpStatus status) {
        this.msg = msg;
        this.status = status;
    }
}
