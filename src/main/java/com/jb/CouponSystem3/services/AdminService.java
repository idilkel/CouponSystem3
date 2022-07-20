package com.jb.CouponSystem3.services;

import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.beans.Customer;
import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.CouponSystemException;

import java.util.List;

public interface AdminService {

    boolean login(String email, String password) throws CouponSecurityException;

    Company addCompany(Company company) throws CouponSystemException;

    Company updateCompany(int companyId, Company company) throws CouponSystemException;

    void deleteCompany(int companyId) throws CouponSystemException;

    List<Company> getAllCompanies();

    Company getOneCompany(int companyId) throws CouponSystemException;

    Customer addCustomer(Customer customer) throws CouponSystemException;

    Customer updateCustomer(int customerId, Customer customer) throws CouponSystemException;

    void deleteCustomer(int customerId) throws CouponSystemException;

    List<Customer> getAllCustomers();

    Customer getOneCustomer(int customerId) throws CouponSystemException;

    //  A company shouldn't get other companies coupons (therefore this ability was transferred to the admin only)
    List<Coupon> getAllCoupons();
}
