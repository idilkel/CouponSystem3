package com.jb.CouponSystem3.services;

import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.beans.Customer;
import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.exceptions.ErrMsg;
import com.jb.CouponSystem3.exceptions.SecMsg;
import com.jb.CouponSystem3.security.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends ClientService implements AdminService {
    private final TokenManager tokenManager;

    @Override
    public boolean login(String email, String password) throws CouponSecurityException {
        if (!(email.equals("admin@admin.com") && password.equals("admin"))) {
            throw new CouponSecurityException(SecMsg.EMAIL_OR_PASSWORD_INCORRECT);
        }
        return true;
    }

    @Override
    public Company addCompany(Company company) throws CouponSystemException {
        if (companyRepository.existsByNameOrEmail(company.getName(), company.getEmail())) {
            throw new CouponSystemException(ErrMsg.COMPANY_DETAILS_ALREADY_EXIST_EXCEPTION);
        }
        return companyRepository.save(company);
    }

    @Override
    public Company updateCompany(int companyId, Company company) throws CouponSystemException {
        if (company.getId() != companyId) {
            throw new CouponSystemException(ErrMsg.CANT_UPDATE);
        }
        if (!(company.getName().equals(companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION)).getName()))) {
            throw new CouponSystemException(ErrMsg.CANT_UPDATE);
        }
        return companyRepository.saveAndFlush(company);
    }

    @Override
    public void deleteCompany(int companyId) throws CouponSystemException {
        if (!companyRepository.existsById(companyId)) {
            throw new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION);
        }
        //Alternative for first deletion of coupon then the purchase within a loop - less efficient (instead of in one query with join as below)
//        for (Coupon coupon : couponRepository.findByCompanyId(companyId)) {
//            couponRepository.deleteById(coupon.getId());
//            couponRepository.deleteCouponPurchase(coupon.getId());
//        }

        //Only one query for coupons deletion from purchase table and from coupons table, with join table which can see also nulls on the purchases (by right join)
        couponRepository.deleteCouponAndPurchaseByCompanyId(companyId);
        companyRepository.deleteById(companyId);
    }

    @Override
    public List<Company> getAllCompanies() {
//        return companyRepository.findAll();
        return companyRepository.findAllByOrderById();
    }

    @Override
    public Company getOneCompany(int companyId) throws CouponSystemException {
        return companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
    }

    @Override
    public Customer addCustomer(Customer customer) throws CouponSystemException {
        if (customerRepository.existsByIdOrEmail(customer.getId(), customer.getEmail())) {
            throw new CouponSystemException(ErrMsg.ID_OR_EMAIL_ALREADY_EXISTS_EXCEPTION);
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(int customerId, Customer customer) throws CouponSystemException {
        if (!customerRepository.existsById(customerId)) {
            throw new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION);
        }
        if (customerId != customer.getId()) {
            throw new CouponSystemException(ErrMsg.CANT_UPDATE);
        }
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public void deleteCustomer(int customerId) throws CouponSystemException {
        if (!customerRepository.existsById(customerId)) {
            throw new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION);
        }
        customerRepository.deleteById(customerId);//the coupons of the customer are deleted with him (checked)
    }

    @Override
    public List<Customer> getAllCustomers() {
//        return customerRepository.findAll();
        return customerRepository.findAllByOrderById();
    }

    @Override
    public Customer getOneCustomer(int customerId) throws CouponSystemException {
        return customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

}
