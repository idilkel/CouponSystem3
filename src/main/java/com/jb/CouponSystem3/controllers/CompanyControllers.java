package com.jb.CouponSystem3.controllers;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.security.TokenManager;
import com.jb.CouponSystem3.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/companies")
@RequiredArgsConstructor
public class CompanyControllers {

    private final CompanyService companyService;
    private final TokenManager tokenManager;

    // Not used from here but from LoginController
//    boolean login(@Valid @RequestBody LoginRequest loginRequest) throws CouponSecurityException {
//        String email = loginRequest.getEmail();
//        String password = loginRequest.getPassword();
//        return companyService.login(email, password);
//    }

    @PostMapping("coupons")
    @ResponseStatus(HttpStatus.CREATED)
    void addCoupon(@RequestHeader("Authorization") UUID token, @RequestBody Coupon coupon) throws CouponSystemException, CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        companyService.addCoupon(companyId, coupon);
    }

    @PutMapping("coupons/{couponId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateCoupon(@RequestHeader("Authorization") UUID token, @PathVariable int couponId, @RequestBody Coupon coupon) throws CouponSystemException, CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        companyService.updateCoupon(companyId, couponId, coupon);
    }

    @DeleteMapping("coupons/{couponId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCoupon(@RequestHeader("Authorization") UUID token, @PathVariable int couponId) throws CouponSystemException, CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        companyService.deleteCoupon(companyId, couponId);
    }

    @GetMapping("coupons")
    List<Coupon> getAllCouponsByCompanyId(@RequestHeader("Authorization") UUID token) throws CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        return companyService.getAllCouponsByCompanyId(companyId);
    }

    @GetMapping("coupons/{couponId}")
    Coupon getOneCouponById(@RequestHeader("Authorization") UUID token, @PathVariable int couponId) throws CouponSystemException, CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        return companyService.getOneCouponByIdAndCouponId(companyId, couponId);
    }

    @GetMapping("coupons2/{couponId}")
    Coupon getOneCouponByIdAndCouponId(@RequestHeader("Authorization") UUID token, @PathVariable int couponId) throws CouponSystemException, CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        return companyService.getOneCouponByIdAndCouponId(companyId, couponId);
    }

    @GetMapping("coupons2")
    List<Coupon> getCompanyCoupons(@RequestHeader("Authorization") UUID token) throws CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        return companyService.getCompanyCoupons(companyId);
    }

    @GetMapping("coupons/category")
    List<Coupon> getCompanyCoupons(@RequestHeader("Authorization") UUID token, @RequestParam Category category) throws CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        return companyService.getCompanyCoupons(companyId, category);
    }

    @GetMapping("coupons/maxPrice")
    List<Coupon> getCompanyCoupons(@RequestHeader("Authorization") UUID token, @RequestParam double maxPrice) throws CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        return companyService.getCompanyCoupons(companyId, maxPrice);
    }

    @GetMapping("coupons2/details")
    Company getCompanyDetails(@RequestHeader("Authorization") UUID token) throws CouponSystemException, CouponSecurityException {
        int companyId = tokenManager.getUserId(token);
        return companyService.getCompanyDetails(companyId);
    }


//    Company getCompanyWoDetails() {
//        return companyService.getCompanyWoDetails();
//    }

}