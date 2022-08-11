package com.jb.CouponSystem3.services;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.exceptions.CouponSecurityException;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.exceptions.ErrMsg;
import com.jb.CouponSystem3.exceptions.SecMsg;
import com.jb.CouponSystem3.repos.CompanyRepository;
import com.jb.CouponSystem3.repos.CouponRepository;
import com.jb.CouponSystem3.security.TokenManager;
import com.jb.CouponSystem3.utils.TableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl extends ClientService implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final TokenManager tokenManager;


//    public CompanyServiceImpl(String email) {
////        this.companyId = companyRepository.getIdByEmail(email);
//    }


    @Override
    public boolean login(String email, String password) throws CouponSecurityException {
        if (!companyRepository.existsByEmailAndPassword(email, password)) {
            throw new CouponSecurityException(SecMsg.EMAIL_OR_PASSWORD_INCORRECT);
        }
        return true;
    }

    @Override
    public int idCompanyService(String email, String password) throws CouponSystemException {
        if (!companyRepository.existsByEmailAndPassword(email, password)) {
            throw new CouponSystemException(ErrMsg.LOGIN_EXCEPTION);
        }
        return companyRepository.getIdByEmail(email);
    }

    @Override
    public Coupon addCoupon(int companyId, Coupon coupon) throws CouponSystemException {
        if (couponRepository.existsByTitleAndCompanyId(coupon.getTitle(), companyId)) {
            throw new CouponSystemException(ErrMsg.COUPON_ALREADY_EXISTS_EXCEPTION);
        }
        if (coupon.getCompany().getId() != companyId) {
            throw new CouponSystemException(ErrMsg.CANT_UPDATE_COUPON_COMPANY_ID);
        }
        Company company = companyRepository.findById((companyId)).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));//companyId exists - checked before
        coupon.setCompany(company);
        couponRepository.save(coupon);
        return coupon;
    }

    @Override
    public Coupon updateCoupon(int companyId, int couponId, Coupon coupon) throws CouponSystemException {
        if (coupon.getId() != couponId) {
            throw new CouponSystemException(ErrMsg.CANT_UPDATE_ID);
        }
        int couponCompanyIdFromDb = couponRepository.findById(couponId).orElseThrow(() -> new CouponSystemException(ErrMsg.COUPON_ID_DOES_NOT_EXIST_EXCEPTION)).getCompany().getId();
        if (coupon.getCompany().getId() != couponCompanyIdFromDb) {
            throw new CouponSystemException(ErrMsg.CANT_UPDATE_COUPON_COMPANY_ID);
        }
        if ((!couponRepository.existsById(coupon.getId()))) {
            throw new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION);
        }
        couponRepository.saveAndFlush(coupon);
        return coupon;
    }

    @Override
    public void deleteCoupon(int companyId, int couponId) throws CouponSystemException {
        if (!couponRepository.existsById(couponId)) {
            throw new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION);
        }
        couponRepository.deleteCouponPurchase(couponId);
        couponRepository.deleteById(couponId);
    }

//    //A company shouldn't get other companies coupons (therefor this ability was transferred to the admin only)
//    @Override
//    public List<Coupon> getAllCoupons(int companyId) {
//        return couponRepository.findAll(companyId);
//    }

    @Override
    public List<Coupon> getAllCouponsByCompanyId(int companyId) {
        return couponRepository.findByCompanyId(companyId);
    }

    @Override
    public Coupon getOneCouponById(int companyId, int couponId) throws CouponSystemException {
        return couponRepository.findById(couponId).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
    }

    @Override
    public Coupon getOneCouponByIdAndCouponId(int companyId, int couponId) throws CouponSystemException {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));

        if (coupon.getCompany().getId() != companyId) {//a company can get only the coupon of itself
            throw new CouponSystemException(ErrMsg.COMPANY_ID_DOESNT_MATCH);
        }
        // TODO: 19/06/2022 Trying to get coupon#6 of company 4 with logged-in company#3 - doesn't work 

//        if (!couponRepository.existsById(couponId)) {
//            throw new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION);
//        }
//        if (couponRepository.getById(couponId).getCompany().getId() != companyId) {//a company can get only the coupon of itself
//            throw new CouponSystemException(ErrMsg.COMPANY_ID_DOESNT_MATCH);
//        }
        return couponRepository.findByIdAndCompanyId(couponId, companyId);
    }

    @Override
    public List<Coupon> getCompanyCoupons(int companyId) {
        return couponRepository.findByCompanyId(companyId);
    }

    // TODO: 07/05/2022
    @Override
    public List<Coupon> getCompanyCouponsByCategory(int companyId, Category category) {
        return couponRepository.findAllByCompanyIdAndCategory(companyId, category);
    }

//    @Override
//    public List<Coupon> getCompanyCouponsByMaxPrice(int companyId, double maxPrice) {
//        return couponRepository.findAllByCompanyIdAndMaxPrice(companyId, maxPrice);
//    }

    @Override
    public List<Coupon> getCompanyCouponsByMaxPrice(int companyId, double maxPrice) {
        return couponRepository.findAllByCompanyIdAndPriceLessThanEqual(companyId, maxPrice);
    }

    @Override
    public Company getCompanyDetails(int companyId) throws CouponSystemException {
        List<Coupon> coupons = couponRepository.findByCompanyId(companyId);
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
        company.setCoupons(coupons);
        TableUtils.drawOneCompanyWithCouponsBuffer(company);
        return company;
    }

    @Override
    public Company getCompanyWoDetails(int companyId) throws CouponSystemException {
        List<Coupon> coupons = couponRepository.findByCompanyId(companyId);
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
//        Company company = companyRepository.getById(companyId);
        return company;
    }

    @Override
    public List<Company> getCompanyAsList(int companyId) {
        List<Company> myCompany = companyRepository.findAllById(companyId);
        return myCompany;
    }


}

