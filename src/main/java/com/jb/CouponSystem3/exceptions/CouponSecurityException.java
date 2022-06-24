package com.jb.CouponSystem3.exceptions;

import lombok.Data;

@Data
public class CouponSecurityException extends Exception {

    private SecMsg secMsg;

    public CouponSecurityException(SecMsg secMsg) {
        super(secMsg.getMsg());
        this.secMsg = secMsg;
    }
}
