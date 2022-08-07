package com.jb.CouponSystem3.clr.on;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.security.ClientType;
import com.jb.CouponSystem3.security.LoginManager;
import com.jb.CouponSystem3.services.CompanyService;
import com.jb.CouponSystem3.utils.HeadlineUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
@Order(9)
@RequiredArgsConstructor
public class FillingMoreCouponsForFront implements CommandLineRunner {
    private final LoginManager loginManager;
//    private final CompanyService companyService;
//    private final CustomerService customerService;

    Date startDate = Date.valueOf(LocalDate.now());
    Date datePlus1 = Date.valueOf(LocalDate.now().plusWeeks(1));
    Date datePlus2 = Date.valueOf(LocalDate.now().plusWeeks(2));
    Date datePlus3 = Date.valueOf(LocalDate.now().plusWeeks(3));


    @Override
    public void run(String... args) throws Exception {
        HeadlineUtils.printHeadline("Login as company#3 with CompanyService for adding more coupons for the frontend tests");

        CompanyService companyService3 = (CompanyService) loginManager.login("easyjet@gmail.com", "easyjet1234", ClientType.COMPANY);
        System.out.println("Company Service for : " + companyService3);
        try {
            HeadlineUtils.printHeadline2("Adding a coupon with the logged-in company#3 without an exception");
            System.out.println("Company#3 before adding a coupon");
            companyService3.getCompanyDetails(3);
            Company com3 = companyService3.getCompanyWoDetails(3);
            Coupon coupon1 = Coupon.builder().company(com3).category(Category.RESTAURANTS)
                    .title("Flight Meals").description("20NIS snacks")
                    .startDate(startDate).endDate(datePlus2).amount(300).price(20).image("FOOD").build();
            companyService3.addCoupon(3, coupon1);
            System.out.println("Company#3 after adding a coupon");
            companyService3.getCompanyDetails(3);


        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }
    }
}
