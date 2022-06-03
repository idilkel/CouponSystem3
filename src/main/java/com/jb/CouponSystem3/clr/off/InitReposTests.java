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

@Component
@Order(1)
@RequiredArgsConstructor
public class InitReposTests implements CommandLineRunner {

    private final CompanyRepository companyRepository;

    private final CustomerRepository customerRepository;

    private final CouponRepository couponRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(ArtUtils.repositoryTests);
        System.out.println(ArtUtils.initializing);
        System.out.println(ArtUtils.companiesTitle);

        System.out.println("------------Adding Companies and getting all companies------------");
        Company com1 = Company.builder().name("Nike").email("nike@gmail.com").password("nike1234").build();
        Company com2 = Company.builder().name("Pizza Fino").email("pizza@gmail.com").password("pizza1234").build();
        Company com3 = Company.builder().name("EasyJet").email("easyjet@gmail.com").password("easyjet1234").build();
        Company com4 = Company.builder().name("Cinema City").email("cinema@gmail.com").password("cinema1234").build();
        Company com5 = Company.builder().name("Apple").email("apple@gmail.com").password("apple1234").build();

        companyRepository.saveAll(Arrays.asList(com1, com2, com3, com4));
        TableUtils.drawCompaniesBuffer(companyRepository.findAll());

        System.out.println("---------Adding one company---------------");
        companyRepository.save(com5);
        TableUtils.drawCompaniesBuffer(companyRepository.findAll());

        System.out.println(ArtUtils.customersTitle);
        System.out.println("------------Adding Customers and getting all customers------------");
        Customer cst1 = Customer.builder().firstName("Shula").lastName("Cohen").email("shula@gmail.com").password("shula1234").build();
        Customer cst2 = Customer.builder().firstName("Moshe").lastName("Katz").email("moshe@gmail.com").password("moshe1234").build();
        Customer cst3 = Customer.builder().firstName("Shalom").lastName("Levi").email("shalom@gmail.com").password("shalom1234").build();
        Customer cst4 = Customer.builder().firstName("Suzi").lastName("Mor").email("suzi@gmail.com").password("suzi1234").build();
        Customer cst5 = Customer.builder().firstName("Lior").lastName("Chen").email("lior@gmail.com").password("lior1234").build();

        customerRepository.saveAll(Arrays.asList(cst1, cst2, cst3, cst4));
        TableUtils.drawCustomersBuffer(customerRepository.findAll());

        System.out.println("---------Adding one customer---------------");
        customerRepository.save(cst5);
        TableUtils.drawCustomersBuffer(customerRepository.findAll());

        System.out.println(ArtUtils.couponsTitle);
        System.out.println("------------Adding Coupons and getting all coupons------------");
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
                .startDate(startDate).endDate(datePlus4).amount(30).price(450).image("ZZZZ").build();

        Coupon coup4 = Coupon.builder().company(com4).category(Category.ENTERTAINMENT)
                .title("1+1 on movies").description("Pay 40NIS for 2 tickets")
                .startDate(startDate).endDate(datePlus3).amount(200).price(40).image("AAAA").build();

        Coupon expCoup = Coupon.builder().company(com5).category(Category.ELECTRONICS)
                .title("100NIS discount").description("100NIS discount at 20NIS")
                .startDate(expiredStartDate).endDate(expiredEndDate).amount(10).price(20).image("EXPR").build();


        couponRepository.saveAll(Arrays.asList(coup1, coup2, coup3, coup4));
        TableUtils.drawCouponsBuffer(couponRepository.findAll());
        System.out.println("---------Adding one coupon---------------");
        couponRepository.save(expCoup);
        TableUtils.drawCouponsBuffer(couponRepository.findAll());
    }
}
