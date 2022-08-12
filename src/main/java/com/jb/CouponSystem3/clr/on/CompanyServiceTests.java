package com.jb.CouponSystem3.clr.on;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.security.ClientType;
import com.jb.CouponSystem3.security.LoginManager;
import com.jb.CouponSystem3.services.CompanyService;
import com.jb.CouponSystem3.utils.ArtUtils;
import com.jb.CouponSystem3.utils.HeadlineUtils;
import com.jb.CouponSystem3.utils.TableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
@Order(7)
@RequiredArgsConstructor
public class CompanyServiceTests implements CommandLineRunner {

    private final CompanyService companyService;

    private final LoginManager loginManager;


    Date startDate = Date.valueOf(LocalDate.now());
    Date datePlus1 = Date.valueOf(LocalDate.now().plusWeeks(1));
    Date datePlus2 = Date.valueOf(LocalDate.now().plusWeeks(2));
    Date datePlus3 = Date.valueOf(LocalDate.now().plusWeeks(3));


    @Override
    public void run(String... args) {
        HeadlineUtils.printHeadlineWithCount("CompanyService Tests - without deletion");
        System.out.println(ArtUtils.companyServiceTitle);
        HeadlineUtils.printHeadline("Company#2 Login Test");
        System.out.println("Should be true:");
        try {

            System.out.println(companyService.login("pizza@gmail.com", "pizza1234"));
        } catch (CouponSecurityException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        System.out.println("Wrong email:");
        try {
            System.out.println(companyService.login("wrong@admin.com", "pizza1234"));
        } catch (CouponSecurityException e) {
            //e.printStackTrace();
            System.out.println(e);
        }
        System.out.println("Wrong password:");
        try {
            System.out.println(companyService.login("pizza@gmail.com", "wrong"));
        } catch (CouponSecurityException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        HeadlineUtils.printHeadline("Updating Coupon#2");
        try {
            Coupon coupToUpdate = companyService.getOneCouponByIdAndCouponId(2, 2);
            System.out.println("Coupon#2 before update:");
            TableUtils.drawOneCouponBuffer(coupToUpdate);
            coupToUpdate.setCategory(Category.FASHION);
            coupToUpdate.setTitle("100NIS discount");
            coupToUpdate.setDescription("Pay 100NIS for 200NIS value");
            coupToUpdate.setStartDate(datePlus1);
            coupToUpdate.setEndDate(datePlus3);
            coupToUpdate.setAmount(200);
            coupToUpdate.setPrice(100);
            coupToUpdate.setImage("https://i.imgur.com/8navmvP.jpg");
            companyService.updateCoupon(2, 2, coupToUpdate);
            System.out.println("Coupon#2 after update:");
            TableUtils.drawOneCouponBuffer(companyService.getOneCouponByIdAndCouponId(2, 2));

            System.out.println("Trying to update coupon id:");
            coupToUpdate.setId(4);
            companyService.updateCoupon(2, 2, coupToUpdate);
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        try {
            Coupon coupToUpdate = companyService.getOneCouponByIdAndCouponId(2, 2);
            System.out.println("Trying to update coupon company id from 2 to 5:");
            Company company = coupToUpdate.getCompany();
            company.setId(5);
            coupToUpdate.setCompany(company);
            companyService.updateCoupon(2, 2, coupToUpdate);
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        HeadlineUtils.printHeadline("Login as company#3 with CompanyService");

        System.out.println("Login manager: (should be true)");
        CompanyService companyService3 = (CompanyService) loginManager.login("easyjet@gmail.com", "easyjet1234", ClientType.COMPANY);
        System.out.println("Company Service for : " + companyService3);

        try {
            HeadlineUtils.printHeadline2("Getting coupon#4 of the logged-in company#3");
            TableUtils.drawOneCouponBuffer(companyService3.getOneCouponByIdAndCouponId(3, 4));

            HeadlineUtils.printHeadline2("Getting all the Coupons of logged-in company#3");
            TableUtils.drawCouponsBuffer(companyService3.getCompanyCoupons(3));

            HeadlineUtils.printHeadline2("Getting all the Coupons of logged-in company#3 from Travel Category");
            TableUtils.drawCouponsBuffer(companyService3.getCompanyCouponsByCategory(3, Category.TRAVEL));

            HeadlineUtils.printHeadline2("Getting all the Coupons of logged-in company#3 with maximum 100NIS");
            TableUtils.drawCouponsBuffer(companyService3.getCompanyCoupons(100));

            HeadlineUtils.printHeadline2("Getting logged-in company#3 details");
            System.out.println(companyService3.getCompanyDetails(3));
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        HeadlineUtils.printHeadline("Logged-in as company#3 with CompanyService and checking various customized exceptions");
        try {
            HeadlineUtils.printHeadline2("Trying to get coupon#6 of company 4 with logged-in company#3");
            TableUtils.drawOneCouponBuffer(companyService3.getOneCouponByIdAndCouponId(3, 6));
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }
        try {
            HeadlineUtils.printHeadline2("Adding a coupon with the logged-in company#3 without an exception");
            System.out.println("Company#3 before adding a coupon");
            companyService3.getCompanyDetails(3);
            Company com3 = companyService3.getCompanyWoDetails(3);
            Coupon coupon = Coupon.builder().company(com3).category(Category.TRAVEL)
                    .title("20NIS Venice").description("20NIS flight to Venice")
                    .startDate(startDate).endDate(datePlus2).amount(300).price(20).image("https://i.imgur.com/f5md403.jpg").build();
            companyService3.addCoupon(3, coupon);
            System.out.println("Company#3 after adding a coupon");
            companyService3.getCompanyDetails(3);

            HeadlineUtils.printHeadline2("Trying to add a coupon with an existing title (same as on last coupon#8)");
            com3 = companyService3.getCompanyWoDetails(3);
            Coupon coupExistTitle = Coupon.builder().company(com3).category(Category.FASHION)
                    .title("20NIS Venice").description("20NIS to Venice")
                    .startDate(datePlus1).endDate(datePlus2).amount(40).price(20).image("FASH").build();
            companyService3.addCoupon(3, coupExistTitle);
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        try {
            HeadlineUtils.printHeadline2("Adding a coupon with the logged-in company#3 with an existing coupon title of company#1 coupon#1 -Allowed ");
            System.out.println("Company#3 before adding a coupon");
            companyService3.getCompanyDetails(3);
            Company com3 = companyService3.getCompanyWoDetails(3);
            Coupon coupon = Coupon.builder().company(com3).category(Category.TRAVEL)
                    .title("50% discount on shoes").description("Pay 50NIS get 100NIS")
                    .startDate(startDate).endDate(datePlus2).amount(300).price(50).image("https://i.imgur.com/tv17Uj8.jpg").build();
            companyService3.addCoupon(3, coupon);
            System.out.println("Company#3 after adding a coupon");
            companyService3.getCompanyDetails(3);
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        try {
            HeadlineUtils.printHeadline2("Trying to update the coupon id of coupon#5");

            Coupon couponToUpdate = companyService3.getOneCouponByIdAndCouponId(3, 5);
            couponToUpdate.setId(20);
            companyService3.updateCoupon(3, 5, couponToUpdate);
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        try {
            HeadlineUtils.printHeadline2("Trying to update the company id of coupon#5 from 3 to 1");

            Coupon couponToUpdate = companyService3.getOneCouponByIdAndCouponId(3, 5);
            Company com3 = companyService3.getCompanyWoDetails(3);
            com3.setId(1);
            couponToUpdate.setCompany(com3);
            companyService3.updateCoupon(3, 5, couponToUpdate);
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }
    }
}