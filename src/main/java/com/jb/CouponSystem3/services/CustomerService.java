package com.jb.CouponSystem3.services;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.beans.Customer;
import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.CouponSystemException;

import java.util.Set;

public interface CustomerService {
    boolean login(String email, String password) throws CouponSecurityException;

    void purchaseCoupon(int customerId, Coupon coupon) throws CouponSystemException;

    Set<Coupon> getCustomerCoupons(int customerId);

    Set<Coupon> getCustomerCoupons(int customerId, Category category);

    Set<Coupon> getCustomerCoupons(int customerId, double maxPrice);

    Customer getCustomerDetails(int customerId) throws CouponSystemException;

    //To get all coupons for the customer purchases tests and other coupon related tests
    Set<Coupon> getAllCoupons(int customerId);

    //To get a coupon before purchasing on tests
    Coupon getOneCouponById(int customerId, int couponId) throws CouponSystemException;

    //To test wih one customer
    Customer getOneCustomer(int customerId) throws CouponSystemException;
}
