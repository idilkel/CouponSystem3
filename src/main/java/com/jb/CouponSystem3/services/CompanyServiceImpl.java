package com.jb.CouponSystem3.services;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.exceptions.CouponSystemException;
import com.jb.CouponSystem3.exceptions.ErrMsg;
import com.jb.CouponSystem3.utils.TableUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)//Intentional Error - will be fixed on phase3
@NoArgsConstructor
@AllArgsConstructor
@Data//to see which company service id I am getting
public class CompanyServiceImpl extends ClientService implements CompanyService {

    private int companyId;

    public CompanyServiceImpl(String email) {
        this.companyId = companyRepository.getIdByEmail(email);
    }


    @Override
    public boolean login(String email, String password) throws CouponSystemException {
        if (!companyRepository.existsByEmailAndPassword(email, password)) {
            throw new CouponSystemException(ErrMsg.LOGIN_EXCEPTION);
        }
        this.companyId = companyRepository.getIdByEmail(email);
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
    public void addCoupon(Coupon coupon) throws CouponSystemException {
        if (couponRepository.existsByTitleAndCompanyId(coupon.getTitle(), this.companyId) == 1) {
            throw new CouponSystemException(ErrMsg.COUPON_ALREADY_EXISTS_EXCEPTION);
        }
        if (coupon.getCompany().getId() != this.companyId) {
            throw new CouponSystemException(ErrMsg.CANT_UPDATE_COUPON_COMPANY_ID);
        }
        couponRepository.save(coupon);
    }

    @Override
    public void updateCoupon(int couponId, Coupon coupon) throws CouponSystemException {
        if (coupon.getId() != couponId) {
            throw new CouponSystemException(ErrMsg.CANT_UPDATE_ID);
        }
        if (coupon.getCompany().getId() != couponRepository.getById(couponId).getCompany().getId()) {
            throw new CouponSystemException(ErrMsg.CANT_UPDATE_COUPON_COMPANY_ID);
        }
        if ((!couponRepository.existsById(coupon.getId()))) {
            throw new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION);
        }
        couponRepository.saveAndFlush(coupon);
    }

    @Override
    public void deleteCoupon(int couponId) throws CouponSystemException {
        if (!couponRepository.existsById(couponId)) {
            throw new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION);
        }
        couponRepository.deleteCouponPurchase(couponId);
        couponRepository.deleteById(couponId);
    }

//    //A company shouldn't get other companies coupons (therefor this ability was transferred to the admin only)
//    @Override
//    public List<Coupon> getAllCoupons() {
//        return couponRepository.findAll();
//    }

    @Override
    public List<Coupon> getAllCouponsByCompanyId() {
        return couponRepository.findByCompanyId(this.companyId);
    }

    @Override
    public Coupon getOneCouponById(int couponId) throws CouponSystemException {
        return couponRepository.findById(couponId).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
    }

    @Override
    public Coupon getOneCouponByIdAndCouponId(int couponId) throws CouponSystemException {
        if (!couponRepository.existsById(couponId)) {
            throw new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION);
        }
        if (couponRepository.getById(couponId).getCompany().getId() != this.companyId) {//a company can get only the coupon of itself
            throw new CouponSystemException(ErrMsg.COMPANY_ID_DOESNT_MATCH);
        }
        return couponRepository.getByCompanyIdAndCouponId(couponId, this.companyId);
    }

    @Override
    public List<Coupon> getCompanyCoupons() {
        return couponRepository.findByCompanyId(this.companyId);
    }

    // TODO: 07/05/2022
    @Override
    public List<Coupon> getCompanyCoupons(Category category) {
        return couponRepository.findAllByCompanyIdAndCategory(this.companyId, category.name().toString());
    }

    @Override
    public List<Coupon> getCompanyCoupons(double maxPrice) {
        return couponRepository.findAllByCompanyIdAndMaxPrice(this.companyId, maxPrice);
    }

    @Override
    public Company getCompanyDetails() throws CouponSystemException {
        List<Coupon> coupons = couponRepository.findByCompanyId(this.companyId);
        Company company = companyRepository.findById(this.companyId).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
        company.setCoupons(coupons);
        TableUtils.drawOneCompanyWithCouponsBuffer(company);
        return company;
    }

    @Override
    public Company getCompanyWoDetails() {
        List<Coupon> coupons = couponRepository.findByCompanyId(this.companyId);
        Company company = companyRepository.getById(this.companyId);
        return company;
    }
}

