package com.jb.CouponSystem3.controllers;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.beans.Customer;
import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.exceptions.SecMsg;
import com.jb.CouponSystem3.models.NotCouponPayLoad;
import com.jb.CouponSystem3.security.ClientType;
import com.jb.CouponSystem3.security.LoginManager;
import com.jb.CouponSystem3.security.TokenManager;
import com.jb.CouponSystem3.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {
    private final AdminService adminService;
    private final LoginManager loginManager;
    private final TokenManager tokenManager;


    @PostMapping("companies")
    @ResponseStatus(HttpStatus.CREATED)
    Company addCompany(@RequestHeader("Authorization") UUID token, @RequestBody Company company) throws CouponSystemException, CouponSecurityException {
        int adminId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.ADMINISTRATOR) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return adminService.addCompany(company);
    }

    @PutMapping("company/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT) //returns company
    Company updateCompany(@RequestHeader("Authorization") UUID token, @PathVariable int id, @RequestBody Company company) throws CouponSystemException, CouponSecurityException {
        int adminId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.ADMINISTRATOR) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return adminService.updateCompany(id, company);
    }

    @DeleteMapping("companies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCompany(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws CouponSystemException, CouponSecurityException {
        int adminId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.ADMINISTRATOR) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        adminService.deleteCompany(id);
    }

    @GetMapping("companies")
    List<Company> getAllCompanies(@RequestHeader("Authorization") UUID token) throws CouponSecurityException {
        int adminId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.ADMINISTRATOR) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return adminService.getAllCompanies();
    }

    @GetMapping("companies/{id}")
    Company getOneCompany(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws CouponSystemException, CouponSecurityException {
        int adminId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.ADMINISTRATOR) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return adminService.getOneCompany(id);

    }

    @PostMapping("customers")
    @ResponseStatus(HttpStatus.CREATED)
    Customer addCustomer(@RequestHeader("Authorization") UUID token, @RequestBody Customer customer) throws CouponSystemException, CouponSecurityException {
        int adminId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.ADMINISTRATOR) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return adminService.addCustomer(customer);
    }

    @PutMapping("customers/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT) //returns customer
    Customer updateCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int id, @RequestBody Customer customer) throws CouponSystemException, CouponSecurityException {
        int adminId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.ADMINISTRATOR) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return adminService.updateCustomer(id, customer);
    }

    @DeleteMapping("customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws CouponSystemException, CouponSecurityException {
        int adminId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.ADMINISTRATOR) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        adminService.deleteCustomer(id);
    }

    @GetMapping("customers")
    List<Customer> getAllCustomers(@RequestHeader("Authorization") UUID token) throws CouponSecurityException {
        int adminId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.ADMINISTRATOR) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return adminService.getAllCustomers();
    }

    @GetMapping("customers/{id}")
    Customer getOneCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws CouponSystemException, CouponSecurityException {
        int adminId = tokenManager.getUserId(token);
        if (tokenManager.getType(token) != ClientType.ADMINISTRATOR) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return adminService.getOneCustomer(id);
    }

    @GetMapping("coupons")
    List<Coupon> getAllCoupons(@RequestHeader("Authorization") UUID token) {
        return adminService.getAllCoupons();
    }

    @GetMapping("coupons/payloads")
    List<NotCouponPayLoad> getAllCouponsPayLoads(@RequestHeader("Authorization") UUID token) {
        return adminService.getAllCouponsPayloads();
    }

    @GetMapping("coupons/category")
    List<Coupon> getAllCouponsByCategory(@RequestHeader("Authorization") UUID token, @RequestParam Category category) {
        return adminService.getAllCouponsByCategory(category);
    }

    @GetMapping("coupons/price/max")
    List<Coupon> getAllCouponsByMaxPrice(@RequestHeader("Authorization") UUID token, @RequestParam double value) {
        return adminService.getAllCouponsByMaxPrice(value);
    }


}
