package com.jb.CouponSystem3.services;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.exceptions.CouponSystemException;

import java.util.List;

public interface CompanyService {

    boolean login(String email, String password) throws CouponSystemException;

    int idCompanyService(String email, String password) throws CouponSystemException;

    void addCoupon(Coupon coupon) throws CouponSystemException;

    void updateCoupon(int couponId, Coupon coupon) throws CouponSystemException;

    void deleteCoupon(int couponId) throws CouponSystemException;

    //A company shouldn't get other companies coupons (therefor this ability was transferred to the admin only)
    //List<Coupon> getAllCoupons();

    List<Coupon> getAllCouponsByCompanyId();

    Coupon getOneCouponById(int couponId) throws CouponSystemException;

    Coupon getOneCouponByIdAndCouponId(int couponId) throws CouponSystemException;

    List<Coupon> getCompanyCoupons();

    List<Coupon> getCompanyCoupons(Category category);

    List<Coupon> getCompanyCoupons(double maxPrice);

    Company getCompanyDetails() throws CouponSystemException;

    //For CompanyService tests
    Company getCompanyWoDetails();

}
