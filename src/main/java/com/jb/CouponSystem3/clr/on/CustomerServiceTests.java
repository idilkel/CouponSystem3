package com.jb.CouponSystem3.clr.on;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.security.ClientType;
import com.jb.CouponSystem3.security.LoginManager;
import com.jb.CouponSystem3.services.CustomerService;
import com.jb.CouponSystem3.utils.ArtUtils;
import com.jb.CouponSystem3.utils.HeadlineUtils;
import com.jb.CouponSystem3.utils.TableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Order(8)
@RequiredArgsConstructor
public class CustomerServiceTests implements CommandLineRunner {

    private final CustomerService customerService;

    private final LoginManager loginManager;


    @Override
    public void run(String... args) {

        HeadlineUtils.printHeadlineWithCount("CustomerService Tests - without deletion");
        System.out.println(ArtUtils.customersServiceTitle);

        System.out.println("-------------------Customer#1 Login Test----------------------");
        System.out.println("Should be true:");
        try {
            System.out.println(customerService.login("shula@gmail.com", "shula1234"));
        } catch (CouponSecurityException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        System.out.println("Wrong email:");
        try {
            System.out.println(customerService.login("wrong@gmail.com", "shula1234"));
        } catch (CouponSecurityException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        System.out.println("Wrong password:");
        try {
            System.out.println(customerService.login("shula@gmail.com", "wrong"));
        } catch (CouponSecurityException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        HeadlineUtils.printHeadline("Login as customer#2 with CustomerService");
        try {
            System.out.println("Login manager: (should be true)");
            CustomerService customerService2 = (CustomerService) loginManager.login("moshe@gmail.com", "moshe1234", ClientType.CUSTOMER);
            System.out.println("Customer Service for : " + customerService2);

            Coupon coupon1 = customerService2.getOneCouponById(2, 1);
            Coupon coupon2 = customerService2.getOneCouponById(2, 2);
            Coupon coupon3 = customerService2.getOneCouponById(2, 3);
            Coupon coupon4 = customerService2.getOneCouponById(2, 4);
            Coupon coupon5 = customerService2.getOneCouponById(2, 5);

            HeadlineUtils.printHeadline2("Customer#2 is purchasing coupons#1-5");

            System.out.println("Customer#2 coupons before purchasing the coupons");
            Set<Coupon> couponsOfCustomer2 = customerService2.getCustomerCoupons(2);
            TableUtils.drawCouponsBuffer(couponsOfCustomer2);
            System.out.println("Coupons' status before purchase:");
            TableUtils.drawCouponsBuffer(customerService2.getAllCoupons(2));

            customerService2.purchaseCoupon(2, coupon1);
            customerService2.purchaseCoupon(2, coupon2);
            customerService2.purchaseCoupon(2, coupon3);
            customerService2.purchaseCoupon(2, coupon4);
            customerService2.purchaseCoupon(2, coupon5);

            System.out.println("Customer#2 coupons after purchasing the coupons (get all coupons of the logged-in customer)");
            couponsOfCustomer2 = customerService2.getCustomerCoupons(2);
            TableUtils.drawCouponsBuffer(couponsOfCustomer2);

            //Adding additional coupon purchases by different customer#3 & 5 to check tests while additional purchases are present
            CustomerService customerService3 = (CustomerService) loginManager.login("shalom@gmail.com", "shalom1234", ClientType.CUSTOMER);

            customerService3.purchaseCoupon(3, coupon1);
            customerService3.purchaseCoupon(3, coupon2);
            customerService3.purchaseCoupon(3, coupon4);
            System.out.println("Customer#3 coupons after purchasing the coupons");
            TableUtils.drawCouponsBuffer(customerService3.getCustomerCoupons(3));

            CustomerService customerService5 = (CustomerService) loginManager.login("lior@gmail.com", "lior1234", ClientType.CUSTOMER);
            customerService5.purchaseCoupon(5, coupon1);
            customerService5.purchaseCoupon(5, coupon2);
            customerService5.purchaseCoupon(5, coupon4);
            System.out.println("Customer#5 coupons after purchasing the coupons");
            TableUtils.drawCouponsBuffer(customerService5.getCustomerCoupons(5));

            HeadlineUtils.printHeadline2("Get all coupons of the logged-in customer#2 from fashion category:");
            TableUtils.drawCouponsBuffer(customerService2.getCustomerCouponsByCategory(2, Category.FASHION));

            HeadlineUtils.printHeadline2("Getting all the Coupons of logged in customer#2 with maximum 50NIS");
            TableUtils.drawCouponsBuffer(customerService2.getCustomerCoupons(2, 50));

            HeadlineUtils.printHeadline2("The logged-in customer#2 Details");
            customerService2.getCustomerDetails(2);

            HeadlineUtils.printHeadline("Login as customer#2 with CustomerService and checking various customized exceptions");
            HeadlineUtils.printHeadline2("Trying to purchase coupon#2 again");
            customerService2.purchaseCoupon(2, coupon2);

        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        //Adding additional coupon purchases by different customers to check tests while additional purchases are present


        try {
            HeadlineUtils.printHeadline2("Trying to purchase coupon#3 with 0 coupons on stock");
            CustomerService customerService2 = (CustomerService) loginManager.login("moshe@gmail.com", "moshe1234", ClientType.CUSTOMER);
            Coupon couponZero = customerService2.getOneCouponById(2, 3);
            System.out.println("Trying to purchase the following coupon:");
            TableUtils.drawOneCouponBuffer(couponZero);
            System.out.println("Purchasing response:");
            customerService2.purchaseCoupon(2, couponZero);
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        try {
            HeadlineUtils.printHeadline2("Trying to purchase expired coupon#7");
            CustomerService customerService2 = (CustomerService) loginManager.login("moshe@gmail.com", "moshe1234", ClientType.CUSTOMER);
            Coupon couponExp = customerService2.getOneCouponById(2, 7);
            System.out.println("Trying to purchase the following coupon:");
            TableUtils.drawOneCouponBuffer(couponExp);
            System.out.println("Purchasing response:");
            customerService2.purchaseCoupon(2, couponExp);
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

    }
}
