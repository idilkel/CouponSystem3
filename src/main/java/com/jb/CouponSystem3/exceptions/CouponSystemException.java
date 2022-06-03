package com.jb.CouponSystem3.exceptions;

public class CouponSystemException extends Exception {
    public CouponSystemException(ErrMsg errMsg) {
        super(errMsg.getMessage());
    }
}