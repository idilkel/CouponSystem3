package com.jb.CouponSystem3.services;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.beans.Customer;
import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.exceptions.ErrMsg;
import com.jb.CouponSystem3.exceptions.SecMsg;
import com.jb.CouponSystem3.security.TokenManager;
import com.jb.CouponSystem3.utils.TableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends ClientService implements CustomerService {


    private final TokenManager tokenManager;

    @Override
    public boolean login(String email, String password) throws CouponSecurityException {
        if (!customerRepository.existsByEmailAndPassword(email, password)) {
            throw new CouponSecurityException(SecMsg.EMAIL_OR_PASSWORD_INCORRECT);
        }
        return true;
    }

    @Override
    public Coupon purchaseCoupon(int customerId, Coupon coupon) throws CouponSystemException {
        if (coupon.getAmount() == 0) {
            throw new CouponSystemException(ErrMsg.NO_COUPONS_LEFT_EXCEPTION);
        }
//        if (coupon.getEndDate().after(Date.valueOf(LocalDate.now()))) {
//            throw new CouponSystemException(ErrMsg.COUPON_EXPIRED_EXCEPTION);
//        }
        if ((coupon.getEndDate().compareTo(Date.valueOf(LocalDate.now()))) < 0) {
            throw new CouponSystemException(ErrMsg.COUPON_EXPIRED_EXCEPTION);
        }
        if (couponRepository.existsByCustomerIdAndCouponId(customerId, coupon.getId()) != 0) {
            throw new CouponSystemException(ErrMsg.COUPON_ALREADY_PURCHASED_EXCEPTION);
        }

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
        Set<Coupon> coupons = customer.getCoupons();
        coupons.add(coupon);
        customer.setCoupons(coupons);
        customerRepository.saveAndFlush(customer);
        coupon.setAmount(coupon.getAmount() - 1);
        couponRepository.saveAndFlush(coupon);
        return coupon;
    }


    @Override
    public Set<Coupon> getCustomerCoupons(int customerId) {
        return couponRepository.findByCustomerId(customerId);
    }

    @Override
    public Set<Coupon> getCustomerCouponsByCategory(int customerId, Category category) {
        return couponRepository.findAllByCustomerIdAndCategory(customerId, category.name().toString());
    }

    @Override
    public Set<Coupon> getCustomerCoupons(int customerId, double maxPrice) {
        return couponRepository.findAllByCustomerIdAndMaxPrice(customerId, maxPrice);
    }

    @Override
    public Customer getCustomerDetails(int customerId) throws CouponSystemException {
        Set<Coupon> coupons = couponRepository.findByCustomerId(customerId);
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
        customer.setCoupons(coupons);
        TableUtils.drawOneCustomerWithCouponsBuffer(customer);
        return customer;
    }

    @Override
    public Set<Coupon> getAllCoupons(int customerId) {
        List<Coupon> coupons = couponRepository.findAll();
        Set<Coupon> couponsSet = new HashSet<>();
        couponsSet.addAll(coupons);
        return couponsSet;
    }

    @Override
    public Coupon getOneCouponById(int customerId, int couponId) throws CouponSystemException {
        return couponRepository.findById(couponId).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
    }

    @Override
    public Customer getOneCustomer(int customerId) throws CouponSystemException {
        return customerRepository.findById(customerId).orElseThrow((() -> new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION)));
    }

}

