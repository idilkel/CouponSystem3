package com.jb.CouponSystem3.services;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.CouponSystemException;

import java.util.List;

public interface CompanyService {

    boolean login(String email, String password) throws CouponSecurityException;

    int idCompanyService(String email, String password) throws CouponSystemException;

    Coupon addCoupon(int companyId, Coupon coupon) throws CouponSystemException;

    Coupon updateCoupon(int companyId, int couponId, Coupon coupon) throws CouponSystemException;

    void deleteCoupon(int companyId, int couponId) throws CouponSystemException;

    //A company shouldn't get other companies coupons (therefor this ability was transferred to the admin only)
    //List<Coupon> getAllCoupons();

    List<Coupon> getAllCouponsByCompanyId(int companyId);

    Coupon getOneCouponById(int companyId, int couponId) throws CouponSystemException;

    Coupon getOneCouponByIdAndCouponId(int companyId, int couponId) throws CouponSystemException;

    List<Coupon> getCompanyCoupons(int companyId);

    List<Coupon> getCompanyCouponsByCategory(int companyId, Category category);

    List<Coupon> getCompanyCouponsByMaxPrice(int companyId, double maxPrice);

    Company getCompanyDetails(int companyId) throws CouponSystemException;

    //For CompanyService tests
    Company getCompanyWoDetails(int companyId) throws CouponSystemException;

    //For updating store
    List<Company> getCompanyAsList(int companyId);

//    List<CouponPayLoad> getAllCouponPayloadsByCompanyId(int companyId);

//    String convertIdToName(int companyId) throws CouponSystemException;


}
