package com.jb.CouponSystem3.clr.on;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.beans.Customer;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.security.ClientType;
import com.jb.CouponSystem3.security.LoginManager;
import com.jb.CouponSystem3.services.AdminService;
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
@Order(5)
@RequiredArgsConstructor
public class InitAdminCompanyService implements CommandLineRunner {

    private final AdminService adminService;

    private final LoginManager loginManager;

    @Override
    public void run(String... args) {
        HeadlineUtils.printHeadlineWithCount("Initializations on AdminService and on CompanyService");
        System.out.println(ArtUtils.initializing);
        System.out.println(ArtUtils.adminServiceTitle);
        System.out.println(ArtUtils.companiesTitle);
        try {
            System.out.println("------------Adding Companies and getting all companies------------");
            Company com1 = Company.builder().name("Nike").email("nike@gmail.com").password("nike1234").build();
            Company com2 = Company.builder().name("Pizza Fino").email("pizza@gmail.com").password("pizza1234").build();
            Company com3 = Company.builder().name("EasyJet").email("easyjet@gmail.com").password("easyjet1234").build();
            Company com4 = Company.builder().name("Cinema City").email("cinema@gmail.com").password("cinema1234").build();
            Company com5 = Company.builder().name("Apple").email("apple@gmail.com").password("apple1234").build();

            adminService.addCompany(com1);
            adminService.addCompany(com2);
            adminService.addCompany(com3);
            adminService.addCompany(com4);
            adminService.addCompany(com5);
            TableUtils.drawCompaniesBuffer(adminService.getAllCompanies());

            System.out.println("---------Get one company#5---------------");
            TableUtils.drawOneCompanyBuffer(adminService.getOneCompany(5));

            System.out.println(ArtUtils.customersTitle);
            System.out.println("------------Adding Customers and getting all customers------------");
            Customer cst1 = Customer.builder().firstName("Shula").lastName("Cohen").email("shula@gmail.com").password("shula1234").build();
            Customer cst2 = Customer.builder().firstName("Moshe").lastName("Katz").email("moshe@gmail.com").password("moshe1234").build();
            Customer cst3 = Customer.builder().firstName("Shalom").lastName("Levi").email("shalom@gmail.com").password("shalom1234").build();
            Customer cst4 = Customer.builder().firstName("Suzi").lastName("Mor").email("suzi@gmail.com").password("suzi1234").build();
            Customer cst5 = Customer.builder().firstName("Lior").lastName("Chen").email("lior@gmail.com").password("lior1234").build();

            adminService.addCustomer(cst1);
            adminService.addCustomer(cst2);
            adminService.addCustomer(cst3);
            adminService.addCustomer(cst4);
            adminService.addCustomer(cst5);
            TableUtils.drawCustomersBuffer(adminService.getAllCustomers());

            System.out.println("------------Getting only customer#2------------");
            TableUtils.drawOneCustomerBuffer(adminService.getOneCustomer(2));

            System.out.println(ArtUtils.companyServiceTitle);
            System.out.println(ArtUtils.couponsTitle);
            System.out.println("------------Adding Coupons ------------");
            Date startDate = Date.valueOf(LocalDate.now());
            Date datePlus1 = Date.valueOf(LocalDate.now().plusWeeks(1));
            Date datePlus2 = Date.valueOf(LocalDate.now().plusWeeks(2));
            Date datePlus3 = Date.valueOf(LocalDate.now().plusWeeks(3));
            Date datePlus4 = Date.valueOf(LocalDate.now().plusWeeks(4));
            Date expiredStartDate = Date.valueOf(LocalDate.now().minusWeeks(2));
            Date expiredEndDate = Date.valueOf(LocalDate.now().minusDays(2));

            Coupon coup1 = Coupon.builder().company(com1).category(Category.FASHION)
                    .title("50% discount on shoes").description("Pay 50NIS get 100NIS")
                    .startDate(startDate).endDate(datePlus1).amount(20).price(50).image("XXXX").build();

            Coupon coup2 = Coupon.builder().company(com2).category(Category.RESTAURANTS)
                    .title("50% discount on Pizza").description("Pay 30NIS get 60NIS")
                    .startDate(startDate).endDate(datePlus2).amount(100).price(30).image("YYYY").build();

            Coupon coup3 = Coupon.builder().company(com3).category(Category.TRAVEL)
                    .title("10% discount on Flights").description("Pay 450NIS get 500NIS")
                    .startDate(startDate).endDate(datePlus4).amount(1).price(450).image("ZZZZ").build();

            Coupon coup4 = Coupon.builder().company(com4).category(Category.ENTERTAINMENT)
                    .title("1+1 on movies").description("Pay 40NIS for 2 tickets")
                    .startDate(startDate).endDate(datePlus3).amount(200).price(40).image("AAAA").build();

            Coupon coup5 = Coupon.builder().company(com3).category(Category.TRAVEL)
                    .title("10NIS Flights").description("10NIS to Europe")
                    .startDate(startDate).endDate(datePlus3).amount(50).price(10).image("YESS").build();

            Coupon coup6 = Coupon.builder().company(com3).category(Category.ELECTRONICS)
                    .title("10NIS Earphones").description("10NIS SONY Earphones")
                    .startDate(startDate).endDate(datePlus3).amount(30).price(10).image("EARP").build();

            Coupon expCoup = Coupon.builder().company(com5).category(Category.ELECTRONICS)
                    .title("100NIS discount").description("100NIS discount at 20NIS")
                    .startDate(expiredStartDate).endDate(expiredEndDate).amount(10).price(20).image("EXPR").build();

            CompanyService companyService1 = (CompanyService) loginManager.login("nike@gmail.com", "nike1234", ClientType.COMPANY);
            companyService1.addCoupon(1, coup1);
            CompanyService companyService2 = (CompanyService) loginManager.login("pizza@gmail.com", "pizza1234", ClientType.COMPANY);
            companyService2.addCoupon(2, coup2);
            CompanyService companyService3 = (CompanyService) loginManager.login("easyjet@gmail.com", "easyjet1234", ClientType.COMPANY);
            companyService3.addCoupon(3, coup3);
            companyService3.addCoupon(3, coup5);
            companyService3.addCoupon(3, coup6);
            CompanyService companyService4 = (CompanyService) loginManager.login("cinema@gmail.com", "cinema1234", ClientType.COMPANY);
            companyService4.addCoupon(4, coup4);
            CompanyService companyService5 = (CompanyService) loginManager.login("apple@gmail.com", "apple1234", ClientType.COMPANY);
            companyService5.addCoupon(5, expCoup);
            TableUtils.drawCouponsBuffer(adminService.getAllCoupons());

        } catch (
                CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e.toString());
        }

    }
}
