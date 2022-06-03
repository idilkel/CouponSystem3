package com.jb.CouponSystem3.services;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.beans.Customer;
import com.jb.CouponSystem3.exceptions.CouponSystemException;

import java.util.Set;

public interface CustomerService {
    boolean login(String email, String password) throws CouponSystemException;

    void purchaseCoupon(Coupon coupon) throws CouponSystemException;

    Set<Coupon> getCustomerCoupons();

    Set<Coupon> getCustomerCoupons(Category category);

    Set<Coupon> getCustomerCoupons(double maxPrice);

    Customer getCustomerDetails() throws CouponSystemException;

    //To get all coupons for the customer purchases tests and other coupon related tests
    Set<Coupon> getAllCoupons();

    //To get a coupon before purchasing on tests
    Coupon getOneCouponById(int couponId) throws CouponSystemException;

    //To test wih one customer
    Customer getOneCustomer(int customerId) throws CouponSystemException;
}
