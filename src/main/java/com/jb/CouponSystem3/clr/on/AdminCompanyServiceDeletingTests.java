package com.jb.CouponSystem3.clr.on;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.security.ClientType;
import com.jb.CouponSystem3.security.LoginManager;
import com.jb.CouponSystem3.services.AdminService;
import com.jb.CouponSystem3.services.CompanyService;
import com.jb.CouponSystem3.services.CustomerService;
import com.jb.CouponSystem3.utils.HeadlineUtils;
import com.jb.CouponSystem3.utils.TableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
@Order(9)
@RequiredArgsConstructor
public class AdminCompanyServiceDeletingTests implements CommandLineRunner {

    private final LoginManager loginManager;


    Date startDate = Date.valueOf(LocalDate.now());
    Date datePlus1 = Date.valueOf(LocalDate.now().plusWeeks(1));

    @Override
    public void run(String... args) {
        HeadlineUtils.printHeadlineWithCount("Deletion Tests - AdminService and CompanyService");
        HeadlineUtils.printHeadline("Deleting a company");

        try {
            AdminService adminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
            HeadlineUtils.printHeadline2("Adding company#7 and coupon#8 for the delete test:");
            Company compToDelete = Company.builder().name("To Delete").email("del@gmail.com").password("del1234").build();
            adminService.addCompany(compToDelete);
            Coupon couponToDelete = Coupon.builder().company(compToDelete).category(Category.TRAVEL).title("To delete").description("To delete").startDate(startDate).endDate(datePlus1).amount(100).price(100).image("DEL").build();
            CompanyService companyService7 = (CompanyService) loginManager.login("del@gmail.com", "del1234", ClientType.COMPANY);
            companyService7.addCoupon(7, couponToDelete);
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        try {
            CompanyService companyService7 = (CompanyService) loginManager.login("del@gmail.com", "del1234", ClientType.COMPANY);
            System.out.println("Company#7 which has coupon#10 details before deleting the company:");
            companyService7.getCompanyDetails(7);

            //login of customer2 and purchase coupon#10 from company#6
            CustomerService customerService2 = (CustomerService) loginManager.login("moshe@gmail.com", "moshe1234", ClientType.CUSTOMER);
            Coupon coupon = customerService2.getOneCouponById(2, 10);
            customerService2.purchaseCoupon(2, coupon);

            HeadlineUtils.printHeadline2("Deleting company#7 which has coupon#10 (coupon purchased by customer#2)");
            System.out.println("Customer#2 details before deleting Company#7 which has coupon#10:");
            customerService2.getCustomerDetails(2);
            AdminService adminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
            System.out.println("All companies before deletion of company#7");
            TableUtils.drawCompaniesBuffer(adminService.getAllCompanies());
            adminService.deleteCompany(7);
            System.out.println("All companies after deletion of company#7:");
            TableUtils.drawCompaniesBuffer(adminService.getAllCompanies());
            //To print the coupons of customer#2 who purchased coupon#10 from company#7
            System.out.println("The coupons of customer#2 who purchased coupon#10 from company#7");
            customerService2 = (CustomerService) loginManager.login("moshe@gmail.com", "moshe1234", ClientType.CUSTOMER);
            customerService2.getCustomerDetails(2);
            System.out.println("All the coupons list: to show that coupon#10 was deleted");
            TableUtils.drawCouponsBuffer(adminService.getAllCoupons());

            System.out.println("Trying to delete non-existing company#20");
            adminService.deleteCompany(20);
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        try {//Checking Deletion of a customer
            HeadlineUtils.printHeadline("Deleting a customer");
            HeadlineUtils.printHeadline2("Deleting customer#3 which has coupon#1,2,4");
            AdminService adminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
            CustomerService customerService3 = (CustomerService) loginManager.login("shalom@gmail.com", "shalom1234", ClientType.CUSTOMER);
            System.out.println("Customers list before deletion");
            TableUtils.drawCustomersBuffer(adminService.getAllCustomers());
            System.out.println("Coupons list before deletion(to check impact of cascade types)");//cascade.remove removed the entire coupons
            TableUtils.drawCouponsBuffer(customerService3.getAllCoupons(3));
            adminService.deleteCustomer(3);
            System.out.println("Customers list after deletion");
            TableUtils.drawCustomersBuffer(adminService.getAllCustomers());
            System.out.println("Coupons list after deletion(to check impact of cascade types)");//cascade.remove removed the entire coupons
            TableUtils.drawCouponsBuffer(customerService3.getAllCoupons(3));
            System.out.println("Checked: Customer#3 is not present in the coupon purchase table on MySQl\n");

            System.out.println("Trying to delete non-existing customer#20");
            adminService.deleteCustomer(20);
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }

        try {//Checking Deletion of a coupon
            HeadlineUtils.printHeadline("Deleting a coupon");
            HeadlineUtils.printHeadline2("Deleting a coupon#3 (purchased by customer#2)");
            CompanyService companyService3 = (CompanyService) loginManager.login("easyjet@gmail.com", "easyjet1234", ClientType.COMPANY);
            CustomerService customerService2 = (CustomerService) loginManager.login("moshe@gmail.com", "moshe1234", ClientType.CUSTOMER);
            System.out.println("All coupons before deleting coupon#3");
            TableUtils.drawCouponsBuffer(customerService2.getAllCoupons(2));
            System.out.println("Customer#2 details before deleting coupon#3 (purchased coupon#3)");
            customerService2.getCustomerDetails(2);
            companyService3.deleteCoupon(3, 3);
            System.out.println("All coupons after delete of coupon#3");
            TableUtils.drawCouponsBuffer(customerService2.getAllCoupons(2));
            System.out.println("Customer#2 details after deleting coupon#3 (purchased coupon#3)");
            customerService2.getCustomerDetails(2);
            System.out.println("Checked: Coupon#3 is not present in the coupon purchase table on MySQl\n");

            System.out.println("Trying to delete non-existing coupon#20");
            companyService3.deleteCoupon(3, 20);
        } catch (CouponSystemException e) {
            //e.printStackTrace();
            System.out.println(e);
        }


        StringBuffer str1 = new StringBuffer();
        str1.append("\n-------------------------------------------------------------------------------------\n" +
                "In-order to check the expired coupon deletion: \n" +
                "Set the hour and minute in the if on line 25  on jobs.ExpiredCouponRemoval \n" +
                "to the current hour and minute; rerun; and wait half a minute\n" +
                "for example at 13:31 enter LocalTime.now().getHour() == 13 & LocalTime.now().getMinute() == 31\n" +
                "Coupon#7 is expired and will be deleted.\n" +
                "-------------------------------------------------------------------------------------\n");
        System.out.println(str1);
    }

}
