package com.jb.CouponSystem3.controllers;

import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.beans.Customer;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.security.*;
import com.jb.CouponSystem3.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController extends ClientController {
    private final AdminService adminService;
    private final LoginManager loginManager;
    private final TokenManager tokenManager;

    //todo: ask Kobi to do Login controller instead of clientcontroller - the login should return a uuid and not boolean
    // TODO: Should be deleted from ClientController too. It is in LoginController
    @Override
    @PostMapping("login")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        ClientType type = loginRequest.getType();
        UUID token = loginManager.loginUUID(email, password, type);
        return new LoginResponse(token);
    }

    @PostMapping("companies")
    @ResponseStatus(HttpStatus.CREATED)
    void addCompany(@RequestHeader("Authorization") UUID token, @RequestBody Company company) throws CouponSystemException {
        adminService.addCompany(company);
    }

    @PutMapping("company/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateCompany(@RequestHeader("Authorization") UUID token, @PathVariable int id, @RequestBody Company company) throws CouponSystemException {
        adminService.updateCompany(id, company);
    }

    @DeleteMapping("companies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCompany(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws CouponSystemException {
        adminService.deleteCompany(id);
    }

    @GetMapping("companies")
    List<Company> getAllCompanies(@RequestHeader("Authorization") UUID token) {
        return adminService.getAllCompanies();
    }

    @GetMapping("companies/{id}")
    Company getOneCompany(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws CouponSystemException {
        return adminService.getOneCompany(id);

    }

    @PostMapping("customers")
    @ResponseStatus(HttpStatus.CREATED)
    void addCustomer(@RequestHeader("Authorization") UUID token, @RequestBody Customer customer) throws CouponSystemException {
        adminService.addCustomer(customer);
    }

    @PutMapping("customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int id, @RequestBody Customer customer) throws CouponSystemException {
        adminService.updateCustomer(id, customer);
    }

    @DeleteMapping("customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws CouponSystemException {
        adminService.deleteCustomer(id);
    }

    @GetMapping("customers")
    List<Customer> getAllCustomers(@RequestHeader("Authorization") UUID token) {
        return adminService.getAllCustomers();
    }

    @GetMapping("customers/{id}")
    Customer getOneCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws CouponSystemException {
        return adminService.getOneCustomer(id);
    }

    @GetMapping("coupons")
    List<Coupon> getAllCoupons(@RequestHeader("Authorization") UUID token) {
        return adminService.getAllCoupons();
    }
}
