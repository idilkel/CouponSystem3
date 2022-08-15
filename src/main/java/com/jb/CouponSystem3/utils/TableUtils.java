package com.jb.CouponSystem3.utils;

import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.beans.Customer;

import java.util.*;

public class TableUtils {
    public static StringBuffer str1 = new StringBuffer();
    public static Formatter form = new Formatter(str1);

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~With StringBuffer~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public static void drawCompaniesBuffer(List<Company> companies) {
        str1.setLength(0);
        str1.append("|   ID   |           Name           |          Email         |    Password    | \n" +
                "-------------------------------------------------------------------------------");
        System.out.println(str1);
        str1.setLength(0);
        for (Company company : companies) {
            form.format("|   %2d   |   %-20s   |  %-20s  |  %-13s |",
                    company.getId(), company.getName(), company.getEmail(), company.getPassword());
            System.out.println(form);
            str1.setLength(0);
        }
        System.out.println("-------------------------------------------------------------------------------");
    }

    public static void drawOneCompanyBuffer(Company company) {
        str1.setLength(0);
        str1.append("|   ID   |           Name           |          Email         |  Password  | \n" +
                "---------------------------------------------------------------------------");
        System.out.println(str1);
        str1.setLength(0);
        form.format("|   %2d   |   %-20s   |  %-20s  |  %-9s |",
                company.getId(), company.getName(), company.getEmail(), company.getPassword());
        System.out.println(form);
        System.out.println("---------------------------------------------------------------------------");
    }

    public static void drawCustomersBuffer(List<Customer> customers) {
        str1.setLength(0);
        str1.append("|   ID   |      First Name      |         Last Name        |          Email         |   Password  | \n" +
                "---------------------------------------------------------------------------------------------------");
        System.out.println(str1);
        str1.setLength(0);
        for (Customer customer : customers) {
            form.format("|  %2d    |   %-18s |   %-20s   |  %-20s  |  %-11s|",
                    customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword());
            System.out.println(form);
            str1.setLength(0);
        }
        System.out.println("---------------------------------------------------------------------------------------------------");
    }

    public static void drawOneCustomerBuffer(Customer customer) {
        str1.setLength(0);
        str1.append("|   ID   |      First Name      |         Last Name        |          Email         |   Password  | \n" +
                "---------------------------------------------------------------------------------------------------");
        System.out.println(str1);
        str1.setLength(0);
        form.format("|  %2d    |   %-18s |   %-20s   |  %-20s  |  %-11s|",
                customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword());
        System.out.println(form);
        System.out.println("---------------------------------------------------------------------------------------------------");
    }

    public static void drawCouponsBuffer(List<Coupon> coupons) {
        str1.setLength(0);
        str1.append("|   ID   | CompanyId |  Category   |            Title             |          Description         | startDate  |   endDate  | Amount |  Price  | Image  |\n" +
                "------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(str1);
        str1.setLength(0);
        for (Coupon coupon : coupons) {
            form.format("|   %2d   |   %3d     |%-13s|%-30s|%-30s| %-10s | %-10s |  %-5d | %7.2f  |%-7s|",
                    coupon.getId(), coupon.getCompany().getId(), coupon.getCategory(), coupon.getTitle(), coupon.getDescription(), DateUtils.beautifyDate(coupon.getStartDate()), DateUtils.beautifyDate(coupon.getEndDate()), coupon.getAmount(), coupon.getPrice(), coupon.getImage());
            System.out.println(form);
            str1.setLength(0);
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void drawCouponsBuffer(Set<Coupon> coupons) {
        str1.setLength(0);
        str1.append("|   ID   | CompanyId |  Category   |            Title             |          Description         | startDate  |   endDate  | Amount |  Price  | Image  |\n" +
                "------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(str1);
        str1.setLength(0);
        for (Coupon coupon : coupons) {
            form.format("|   %2d   |   %3d     |%-13s|%-30s|%-30s| %-10s | %-10s |  %-5d | %7.2f  |%-7s|",
                    coupon.getId(), coupon.getCompany().getId(), coupon.getCategory(), coupon.getTitle(), coupon.getDescription(), DateUtils.beautifyDate(coupon.getStartDate()), DateUtils.beautifyDate(coupon.getEndDate()), coupon.getAmount(), coupon.getPrice(), coupon.getImage());
            System.out.println(form);
            str1.setLength(0);
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void drawOneCouponBuffer(Coupon coupon) {
        str1.setLength(0);
        str1.append("|   ID   | CompanyId |  Category   |            Title             |          Description         | startDate  |   endDate  | Amount |  Price  | Image  |\n" +
                "------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(str1);
        str1.setLength(0);
        form.format("|   %2d   |   %3d     |%-13s|%-30s|%-30s| %-10s | %-10s |  %-5d | %7.2f  |%-7s|",
                coupon.getId(), coupon.getCompany().getId(), coupon.getCategory(), coupon.getTitle(), coupon.getDescription(), DateUtils.beautifyDate(coupon.getStartDate()), DateUtils.beautifyDate(coupon.getEndDate()), coupon.getAmount(), coupon.getPrice(), coupon.getImage());
        System.out.println(form);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void drawOneCompanyWithCouponsBuffer(Company company) {
        str1.setLength(0);
        str1.append("|    ID  |           Name           |          Email         |   Password   | Coupon Id List | \n" +
                "---------------------------------------------------------------------------------------------");
        System.out.println(str1);
        str1.setLength(0);
        form.format("|    %2d  |   %-20s   |  %-20s  | %-11s  |",
                company.getId(), company.getName(), company.getEmail(), company.getPassword());
        System.out.print(str1);
        str1.setLength(0);
        List<Coupon> coupons = company.getCoupons();
        Collections.sort(coupons, Comparator.comparingInt(Coupon::getId));
        for (Coupon coupon : coupons) {
            System.out.printf(" %2d ", coupon.getId());
        }
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------");
    }


    public static void drawOneCustomerWithCouponsBuffer(Customer customer) {
        str1.setLength(0);
        str1.append("|    ID  |      First Name      |         Last Name        |          Email         |   Password   | Coupon Id List | \n" +
                "--------------------------------------------------------------------------------------------------------------------");
        System.out.println(str1);
        str1.setLength(0);
        form.format("|    %2d  |   %-18s |   %-20s   |  %-20s  |  %-11s |",
                customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword());
        System.out.print(str1);
        str1.setLength(0);
        for (Coupon coupon : customer.getCoupons()) {
            System.out.printf(" %2d ", coupon.getId());
        }
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
    }
}

