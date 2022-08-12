package com.jb.CouponSystem3.clr.off;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.beans.Customer;
import com.jb.CouponSystem3.repos.CompanyRepository;
import com.jb.CouponSystem3.repos.CouponRepository;
import com.jb.CouponSystem3.repos.CustomerRepository;
import com.jb.CouponSystem3.utils.ArtUtils;
import com.jb.CouponSystem3.utils.TableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

@Component
@Order(4)
@RequiredArgsConstructor
public class TestCouponsRepos implements CommandLineRunner {

    private final CouponRepository couponRepository;

    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(ArtUtils.couponsTitle2);
        System.out.println("---Add coupon already tested---");
        System.out.println("----------Test update coupon and get one coupon---------");
        System.out.println("Before update:");
        Coupon coupon = couponRepository.getById(4);
        TableUtils.drawOneCouponBuffer(coupon);

        System.out.println("After update:");
        Company updatedCompany = companyRepository.getById(4);
        coupon.setCompany(updatedCompany);
        coupon.setCategory(Category.ELECTRONICS);
        coupon.setTitle("Apple watch discount");
        coupon.setDescription("Apple watch at 200NIS");
        coupon.setPrice(200);
        coupon.setStartDate(Date.valueOf(LocalDate.now().plusWeeks(1)));
        coupon.setEndDate(Date.valueOf(LocalDate.now().plusWeeks(2)));
        coupon.setAmount(10);
        coupon.setImage("WATCH");
        couponRepository.saveAndFlush(coupon);
        TableUtils.drawOneCouponBuffer(couponRepository.getById(4));


        System.out.println("----------Test delete coupon#4----------------");
        System.out.println("Before delete:");
        TableUtils.drawCouponsBuffer(couponRepository.findAll());
        System.out.println("After delete:");
        couponRepository.deleteById(4);
        TableUtils.drawCouponsBuffer(couponRepository.findAll());

        System.out.println("----------Test add coupon purchase----------------");
        System.out.println("---Test coupon purchase: customer#3 purchases coupons#1 &2-----");
        System.out.println("(The real coupon purchase and delete will be done with the customerService)");
        //Feasibility check with repositories
        //The real coupon purchase will be done with the customerService
        Coupon coupon1 = couponRepository.getById(1);
        coupon1.setAmount(coupon1.getAmount() - 1);
        Coupon coupon2 = couponRepository.getById(2);
        coupon2.setAmount(coupon2.getAmount() - 1);
        couponRepository.saveAndFlush(coupon1);
        couponRepository.saveAndFlush(coupon2);
        Customer customer3 = customerRepository.getById(3);
        System.out.println("Customer#3 before the coupon purchase");
        TableUtils.drawOneCustomerWithCouponsBuffer(customerRepository.getById(3));
        Set<Coupon> coupons = customer3.getCoupons();//in case there were already coupons
        coupons.addAll(Arrays.asList(coupon1, coupon2));
        customer3.setCoupons(coupons);
        customerRepository.saveAndFlush(customer3);

        System.out.println("Customer#3 after the coupon purchase");
        TableUtils.drawOneCustomerWithCouponsBuffer(customerRepository.getById(3));


        System.out.println("----------Test delete coupon purchase----------------");
        System.out.println("---Test delete coupon purchase: customer#3 purchase of coupons#1 to be deleted-----");
        customer3 = customerRepository.getById(3);
        coupons = customer3.getCoupons();
        coupons.remove(coupon1);
        customer3.setCoupons(coupons);
        customerRepository.saveAndFlush(customer3);
        TableUtils.drawOneCustomerWithCouponsBuffer(customerRepository.getById(3));
    }
}
