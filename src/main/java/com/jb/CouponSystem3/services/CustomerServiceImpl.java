package com.jb.CouponSystem3.services;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.beans.Customer;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.exceptions.ErrMsg;
import com.jb.CouponSystem3.utils.TableUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Data
@Scope(BeanDefinition.SCOPE_PROTOTYPE)//Intentional Error - will be fixed on phase3
public class CustomerServiceImpl extends ClientService implements CustomerService {

    private int customerId;

    @Override
    public boolean login(String email, String password) throws CouponSystemException {
        if (!customerRepository.existsByEmailAndPassword(email, password)) {
            throw new CouponSystemException(ErrMsg.LOGIN_EXCEPTION);
        }
        this.customerId = customerRepository.getIdByEmail(email);
        return true;
    }

    @Override
    public void purchaseCoupon(Coupon coupon) throws CouponSystemException {
        if (coupon.getAmount() == 0) {
            throw new CouponSystemException(ErrMsg.NO_COUPONS_LEFT_EXCEPTION);
        }
//        if (coupon.getEndDate().after(Date.valueOf(LocalDate.now()))) {
//            throw new CouponSystemException(ErrMsg.COUPON_EXPIRED_EXCEPTION);
//        }
        if ((coupon.getEndDate().compareTo(Date.valueOf(LocalDate.now()))) < 0) {
            throw new CouponSystemException(ErrMsg.COUPON_EXPIRED_EXCEPTION);
        }
        if (couponRepository.existsByCustomerIdAndCouponId(this.customerId, coupon.getId()) != 0) {
            throw new CouponSystemException(ErrMsg.COUPON_ALREADY_PURCHASED_EXCEPTION);
        }

        Customer customer = customerRepository.getById(this.customerId);
        Set<Coupon> coupons = customer.getCoupons();
        coupons.add(coupon);
        customer.setCoupons(coupons);
        customerRepository.saveAndFlush(customer);
        coupon.setAmount(coupon.getAmount() - 1);
        couponRepository.saveAndFlush(coupon);
    }


    @Override
    public Set<Coupon> getCustomerCoupons() {
        return couponRepository.findByCustomerId(this.customerId);
    }

    @Override
    public Set<Coupon> getCustomerCoupons(Category category) {
        return couponRepository.findAllByCustomerIdAndCategory(this.customerId, category.name().toString());
    }

    @Override
    public Set<Coupon> getCustomerCoupons(double maxPrice) {
        return couponRepository.findAllByCustomerIdAndMaxPrice(this.customerId, maxPrice);
    }

    @Override
    public Customer getCustomerDetails() throws CouponSystemException {
        Set<Coupon> coupons = couponRepository.findByCustomerId(this.customerId);
        Customer customer = customerRepository.findById(this.customerId).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
        customer.setCoupons(coupons);
        TableUtils.drawOneCustomerWithCouponsBuffer(customer);
        return customer;
    }

    @Override
    public Set<Coupon> getAllCoupons() {
        List<Coupon> coupons = couponRepository.findAll();
        Set<Coupon> couponsSet = new HashSet<>();
        couponsSet.addAll(coupons);
        return couponsSet;
    }

    @Override
    public Coupon getOneCouponById(int couponId) throws CouponSystemException {
        return couponRepository.findById(couponId).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
    }

    @Override
    public Customer getOneCustomer(int customerId) throws CouponSystemException {
        return customerRepository.findById(customerId).orElseThrow((() -> new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION)));
    }
}

