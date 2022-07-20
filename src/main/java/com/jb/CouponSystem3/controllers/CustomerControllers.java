package com.jb.CouponSystem3.controllers;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.beans.Customer;
import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.exceptions.SecMsg;
import com.jb.CouponSystem3.security.*;
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
@CrossOrigin(origins = "*")
public class CustomerControllers extends ClientController {

    private final CustomerService customerService;
    private final TokenManager tokenManager;
    private final LoginManager loginManager;

    // TODO: 30/06/2022 Should it really extend ClientController?
    @Override
    @PostMapping("login")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        ClientType type = loginRequest.getType();
        UUID token = loginManager.loginUUID(email, password, type);
        return new LoginResponse(token, email);
    }

    @PostMapping("purchase")
    @ResponseStatus(HttpStatus.CREATED)
    public void purchaseCoupon(@RequestHeader("Authorization") UUID token, @RequestBody Coupon coupon) throws CouponSecurityException, CouponSystemException {
        int customerId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.CUSTOMER) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        customerService.purchaseCoupon(customerId, coupon);
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
        return customerService.getCustomerCoupons(customerId, category);
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

//    @GetMapping("coupons/all")
//    //To get all coupons for the customer purchases tests and other coupon related tests
//    public Set<Coupon> getAllCoupons(@RequestHeader("Authorization") UUID token) throws CouponSecurityException {
//        int customerId = tokenManager.getUserId(token);
//        return customerService.getAllCoupons(customerId);
//    }

    @GetMapping("coupons/{couponId}")
    //To get a coupon before purchasing on tests
    public Coupon getOneCouponById(@RequestHeader("Authorization") UUID token, @PathVariable int couponId) throws CouponSecurityException, CouponSystemException {
        int customerId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.CUSTOMER) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return customerService.getOneCouponById(customerId, couponId);
    }

    //To test with one customer
    @GetMapping("details2")
    public Customer getOneCustomer(@RequestHeader("Authorization") UUID token) throws CouponSecurityException, CouponSystemException {
        int customerId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.CUSTOMER) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return customerService.getOneCustomer(customerId);
    }


}
