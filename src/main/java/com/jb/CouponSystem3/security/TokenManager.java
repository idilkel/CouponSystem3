package com.jb.CouponSystem3.security;

import com.jb.CouponSystem3.exceptions.CouponSecurityException;

import java.util.UUID;

public interface TokenManager {
    UUID add(String email, String password, ClientType type) throws CouponSecurityException;

    void removePreviousInstances(int userId, ClientType type);

    int getUserId(UUID token) throws CouponSecurityException;

    ClientType getType(UUID token) throws CouponSecurityException;

    void deleteExpiredTokenOver30Minutes();
}
