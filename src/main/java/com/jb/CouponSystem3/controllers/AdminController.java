package com.jb.CouponSystem3.controllers;

import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.beans.Customer;
import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.security.LoginRequest;
import com.jb.CouponSystem3.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController extends ClientController {
    private final AdminService adminService;

    // TODO: 04/06/2022 Do we need it? If yes, which verb?
    @Override
    public boolean login(@Valid @RequestBody LoginRequest loginRequest) throws CouponSecurityException {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        return adminService.login(email, password);
    }

    @PostMapping("companies")
    @ResponseStatus(HttpStatus.CREATED)
    void addCompany(@RequestBody Company company) throws CouponSystemException {
        adminService.addCompany(company);
    }

    @PutMapping("company/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateCompany(@PathVariable int id, @RequestBody Company company) throws CouponSystemException {
        adminService.updateCompany(id, company);
    }

    @DeleteMapping("companies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCompany(@PathVariable int id) throws CouponSystemException {
        adminService.deleteCompany(id);
    }

    @GetMapping("companies")
    List<Company> getAllCompanies() {
        return adminService.getAllCompanies();
    }

    @GetMapping("companies/{id}")
    Company getOneCompany(@PathVariable int id) throws CouponSystemException {
        return adminService.getOneCompany(id);

    }

    @PostMapping("customers")
    @ResponseStatus(HttpStatus.CREATED)
    void addCustomer(@RequestBody Customer customer) throws CouponSystemException {
        adminService.addCustomer(customer);
    }

    @PutMapping("customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateCustomer(@PathVariable int id, @RequestBody Customer customer) throws CouponSystemException {
        adminService.updateCustomer(id, customer);
    }

    @DeleteMapping("customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCustomer(@PathVariable int id) throws CouponSystemException {
        adminService.deleteCustomer(id);
    }

    @GetMapping("customers")
    List<Customer> getAllCustomers() {
        return adminService.getAllCustomers();
    }

    @GetMapping("customers/{id}")
    Customer getOneCustomer(@PathVariable int id) throws CouponSystemException {
        return adminService.getOneCustomer(id);
    }

    @GetMapping("coupons/{id}")
    List<Coupon> getAllCoupons() {
        return adminService.getAllCoupons();
    }
}
