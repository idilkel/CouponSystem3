package com.jb.CouponSystem3.controllers;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.beans.Customer;
import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.exceptions.SecMsg;
import com.jb.CouponSystem3.security.ClientType;
import com.jb.CouponSystem3.security.LoginManager;
import com.jb.CouponSystem3.security.TokenManager;
import com.jb.CouponSystem3.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("api/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerControllers {

    private final CustomerService customerService;
    private final TokenManager tokenManager;
    private final LoginManager loginManager;


    @PostMapping("purchase")
    @ResponseStatus(HttpStatus.CREATED) //returning coupon for redux frontend
    public Coupon purchaseCoupon(@RequestHeader("Authorization") UUID token, @Valid @RequestBody Coupon coupon) throws CouponSecurityException, CouponSystemException {
        int customerId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.CUSTOMER) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return customerService.purchaseCoupon(customerId, coupon);
    }

    @GetMapping("coupons")
    public Set<Coupon> getCustomerCoupons(@RequestHeader("Authorization") UUID token) throws CouponSecurityException {
        int customerId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.CUSTOMER) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return customerService.getCustomerCoupons(customerId);
    }

    @GetMapping("coupons/category")
    public Set<Coupon> getCustomerCouponsByCategory(@RequestHeader("Authorization") UUID token, @RequestParam Category category) throws CouponSecurityException {
        int customerId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.CUSTOMER) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return customerService.getCustomerCouponsByCategory(customerId, category);
    }

    @GetMapping("coupons/price/max")
    public Set<Coupon> getCustomerCouponsByMaxPrice(@RequestHeader("Authorization") UUID token, @RequestParam double value) throws CouponSecurityException {
        int customerId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.CUSTOMER) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return customerService.getCustomerCoupons(customerId, value);
    }

    @GetMapping("details")
    public Customer getCustomerDetails(@RequestHeader("Authorization") UUID token) throws CouponSecurityException, CouponSystemException {
        int customerId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.CUSTOMER) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return customerService.getCustomerDetails(customerId);
    }


    @GetMapping("coupons/{couponId}")
    //To get a coupon before purchasing on tests
    public Coupon getOneCouponById(@RequestHeader("Authorization") UUID token, @PathVariable int couponId) throws CouponSecurityException, CouponSystemException {
        int customerId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.CUSTOMER) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return customerService.getOneCouponById(customerId, couponId);
    }


}
