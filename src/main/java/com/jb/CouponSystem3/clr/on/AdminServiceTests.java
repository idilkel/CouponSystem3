package com.jb.CouponSystem3.clr.on;

import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Customer;
import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.security.ClientType;
import com.jb.CouponSystem3.security.LoginManager;
import com.jb.CouponSystem3.services.AdminService;
import com.jb.CouponSystem3.utils.ArtUtils;
import com.jb.CouponSystem3.utils.HeadlineUtils;
import com.jb.CouponSystem3.utils.TableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(6)
@RequiredArgsConstructor
public class AdminServiceTests implements CommandLineRunner {

    private final AdminService adminService;

    private final LoginManager loginManager;

    @Override
    public void run(String... args) {

        HeadlineUtils.printHeadlineWithCount("AdminService Tests - without deletion tests");
        System.out.println(ArtUtils.postInitializing);
        System.out.println(ArtUtils.adminServiceTitle);
        HeadlineUtils.printHeadline("Admin Login Test");
        System.out.println("Should be true");
        try {
            System.out.println(adminService.login("admin@admin.com", "admin"));
        } catch (CouponSecurityException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        System.out.println("Wrong email:");
        try {
            System.out.println(adminService.login("wrong@admin.com", "admin"));
        } catch (CouponSecurityException e) {
            //e.printStackTrace();
            System.out.println(e);
        }
        System.out.println("Wrong password:");
        try {
            System.out.println(adminService.login("admin@admin.com", "wrong"));
        } catch (CouponSecurityException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        HeadlineUtils.printHeadline("Tests with a logged-in AdminService");
        AdminService loggedInAdminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);


        HeadlineUtils.printHeadline("Updating one company#4 and getting one company #4");
        try {
            Company compToUpdate = loggedInAdminService.getOneCompany(4);
            System.out.println("Before update");
            TableUtils.drawOneCompanyBuffer(compToUpdate);
            compToUpdate.setEmail("cc@gmail.com");
            compToUpdate.setPassword("cc1234");
            loggedInAdminService.updateCompany(4, compToUpdate);
            compToUpdate = loggedInAdminService.getOneCompany(4);

            System.out.println("After update");
            TableUtils.drawOneCompanyBuffer(compToUpdate);

            System.out.println("Trying to update company#4 name:");
            compToUpdate.setName("New cinema");
            loggedInAdminService.updateCompany(4, compToUpdate);
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        try {
            System.out.println("Trying to update company#4 id to 20 (trying to update company id:");
            Company compToUpdate = loggedInAdminService.getOneCompany(4);
            compToUpdate.setId(20);
            loggedInAdminService.updateCompany(4, compToUpdate);
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        HeadlineUtils.printHeadline("Updating one customer#4 and getting one customer#4");
        try {
            Customer custToUpdate = loggedInAdminService.getOneCustomer(4);
            System.out.println("Customer#4 before update:");
            TableUtils.drawOneCustomerBuffer(custToUpdate);
            custToUpdate.setFirstName("Suzika");
            custToUpdate.setLastName("Mor Cohen");
            custToUpdate.setEmail("smc@gmail.com");
            custToUpdate.setPassword("smc1234");
            adminService.updateCustomer(4, custToUpdate);
            System.out.println("Customer#4 after update:");
            TableUtils.drawOneCustomerBuffer(loggedInAdminService.getOneCustomer(4));

            System.out.println("Trying to update customer#4 id");
            custToUpdate.setId(1);
            loggedInAdminService.updateCustomer(4, custToUpdate);
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        HeadlineUtils.printHeadline2("Adding a new company and getting all companies");
        try {
            System.out.println("All companies before adding the company");
            TableUtils.drawCompaniesBuffer(loggedInAdminService.getAllCompanies());
            Company com1 = Company.builder().name("Fox").email("fox@gmail.com").password("fox1234").build();
            loggedInAdminService.addCompany(com1);
            System.out.println("All companies after adding the company");
            TableUtils.drawCompaniesBuffer(loggedInAdminService.getAllCompanies());
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        HeadlineUtils.printHeadline2("Trying to add a new company with an existing name as company#1: Nike: ");
        try {
            Company com1 = Company.builder().name("Nike").email("nike@hotmail.com").password("nike6789").build();
            loggedInAdminService.addCompany(com1);
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        HeadlineUtils.printHeadline2("Trying to add a new company with an existing email as company#1: nike@gmail.com: ");
        try {
            Company com1 = Company.builder().name("Adidas").email("nike@gmail.com").password("nike6789").build();
            loggedInAdminService.addCompany(com1);
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        HeadlineUtils.printHeadline2("Adding a new customer and getting all customers");
        try {
            System.out.println("All customers before adding the customer");
            TableUtils.drawCustomersBuffer(loggedInAdminService.getAllCustomers());
            Customer cst1 = Customer.builder().firstName("Gila").lastName("Naor").email("gila@gmail.com").password("gila1234").build();
            loggedInAdminService.addCustomer(cst1);
            System.out.println("All customers after adding the customer");
            TableUtils.drawCustomersBuffer(loggedInAdminService.getAllCustomers());
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        HeadlineUtils.printHeadline2("Trying to add a new customer with an existing email as customer#1: shula@gmail.com: ");
        try {
            Customer cst1 = Customer.builder().firstName("Shulamit").lastName("Cohen Katz").email("shula@gmail.com").password("sk1234").build();
            loggedInAdminService.addCustomer(cst1);
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }
    }
}
