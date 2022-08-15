package com.jb.CouponSystem3.controllers;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.exceptions.SecMsg;
import com.jb.CouponSystem3.security.ClientType;
import com.jb.CouponSystem3.security.LoginManager;
import com.jb.CouponSystem3.security.TokenManager;
import com.jb.CouponSystem3.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/companies")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CompanyControllers {

    private final CompanyService companyService;
    private final TokenManager tokenManager;
    private final LoginManager loginManager;


    @PostMapping("coupons")
    @ResponseStatus(HttpStatus.CREATED)
    Coupon addCoupon(@RequestHeader("Authorization") UUID token, @Valid @RequestBody Coupon coupon) throws CouponSystemException, CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.COMPANY) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return companyService.addCoupon(companyId, coupon);

    }

    @PutMapping("coupons/{couponId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT) //returns coupon
    Coupon updateCoupon(@RequestHeader("Authorization") UUID token, @PathVariable int couponId, @Valid @RequestBody Coupon coupon) throws CouponSystemException, CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.COMPANY) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return companyService.updateCoupon(companyId, couponId, coupon);
    }

    @DeleteMapping("coupons/{couponId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCoupon(@RequestHeader("Authorization") UUID token, @PathVariable int couponId) throws CouponSystemException, CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.COMPANY) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        companyService.deleteCoupon(companyId, couponId);
    }

    @GetMapping("coupons")
    List<Coupon> getAllCouponsByCompanyId(@RequestHeader("Authorization") UUID token) throws CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.COMPANY) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return companyService.getAllCouponsByCompanyId(companyId);
    }

    @GetMapping("coupons/{couponId}")
    Coupon getOneCouponById(@RequestHeader("Authorization") UUID token, @PathVariable int couponId) throws CouponSystemException, CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.COMPANY) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return companyService.getOneCouponByIdAndCouponId(companyId, couponId);
    }

    @GetMapping("coupons/category")
    List<Coupon> getCompanyCouponsByCategory(@RequestHeader("Authorization") UUID token, @RequestParam Category category) throws CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.COMPANY) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return companyService.getCompanyCouponsByCategory(companyId, category);
    }

    @GetMapping("coupons/price/max")
    List<Coupon> getCompanyCouponsByMaxPrice(@RequestHeader("Authorization") UUID token, @RequestParam double value) throws CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.COMPANY) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return companyService.getCompanyCouponsByMaxPrice(companyId, value);
    }

    @GetMapping("details")
    Company getCompanyDetails(@RequestHeader("Authorization") UUID token) throws CouponSystemException, CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.COMPANY) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return companyService.getCompanyDetails(companyId);
    }

    //to get the company in the front as list for getting it's details for coupon adding if companyreducer is empty
    @GetMapping("currentAsList")
    List<Company> getCompanyAsList(@RequestHeader("Authorization") UUID token) throws CouponSystemException, CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.COMPANY) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return companyService.getCompanyAsList(companyId);
    }

//    Company getCompanyWoDetails() {
//        return companyService.getCompanyWoDetails();
//    }

}
