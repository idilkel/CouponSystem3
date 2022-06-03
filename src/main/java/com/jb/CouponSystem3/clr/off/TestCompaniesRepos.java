package com.jb.CouponSystem3.clr.off;

import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.repos.CompanyRepository;
import com.jb.CouponSystem3.repos.CouponRepository;
import com.jb.CouponSystem3.utils.ArtUtils;
import com.jb.CouponSystem3.utils.TableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@RequiredArgsConstructor
public class TestCompaniesRepos implements CommandLineRunner {

    private final CompanyRepository companyRepository;

    private final CouponRepository couponRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(ArtUtils.postInitializing);
        System.out.println(ArtUtils.companiesTitle2);

        System.out.println("---------Test if company exists by email and password -------");
        System.out.println("By company 4 details: should be true");
        System.out.println(companyRepository.existsByEmailAndPassword("cinema@gmail.com", "cinema1234"));
        System.out.println("By company 4 details: wrong password, should be false");
        System.out.println(companyRepository.existsByEmailAndPassword("cinema@gmail.com", "Wrong"));
        System.out.println("By company 4 details: wrong email, should be false");
        System.out.println(companyRepository.existsByEmailAndPassword("wrong@gmail.com", "cinema1234"));
        System.out.println("-------Get Id of company 4 by email-------");
        System.out.println(companyRepository.getIdByEmail("cinema@gmail.com"));


        System.out.println("---------Add companies was already tested-------");
        System.out.println("-------------Test update company and get one company by Id-------------");
        Company comp = companyRepository.getById(1);
        System.out.println("Before update");
        TableUtils.drawOneCompanyBuffer(comp);

        comp.setName("Nike Company");
        comp.setEmail("nike.comp@gmail.com");
        comp.setPassword("nikeC123");
        System.out.println("After update");
        companyRepository.saveAndFlush(comp);
        TableUtils.drawOneCompanyBuffer(companyRepository.getById(1));

        System.out.println("----------Test delete company #3----------");
        System.out.println("Before delete:");
        TableUtils.drawCompaniesBuffer(companyRepository.findAll());
        TableUtils.drawCouponsBuffer(couponRepository.findAll());

        Company companyToDelete = companyRepository.getById(3);
        System.out.println("After deleting company#3 (company and it's coupons are deleted):");
        companyRepository.delete(companyToDelete);

        TableUtils.drawCompaniesBuffer(companyRepository.findAll());
        TableUtils.drawCouponsBuffer(couponRepository.findAll());
    }
}
