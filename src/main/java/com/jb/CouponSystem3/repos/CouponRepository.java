package com.jb.CouponSystem3.repos;

import com.jb.CouponSystem3.beans.Company;
import com.jb.CouponSystem3.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    List<Coupon> findByCompany(Company company);

    //Should work by smart dialect only too
    //@Query(value = "SELECT * FROM `coupon-system3-147`.coupons where company_id= :companyId;", nativeQuery = true)
    List<Coupon> findByCompanyId(@Param("companyId") int companyId);

    @Query(value = "SELECT * FROM `coupon-system3-147`.coupons where id= :couponId and company_id= :companyId", nativeQuery = true)
    Coupon getByCompanyIdAndCouponId(@Param("couponId") int couponId, @Param("companyId") int companyId);

    @Query(value = "SELECT * FROM `coupon-system3-147`.coupons where company_id= :companyId and category= :categoryName", nativeQuery = true)
    List<Coupon> findAllByCompanyIdAndCategory(@Param("companyId") int companyId, @Param("categoryName") String categoryName);

    @Query(value = "SELECT * FROM `coupon-system3-147`.coupons where company_id= :companyId and price<= :maxPrice", nativeQuery = true)
    List<Coupon> findAllByCompanyIdAndMaxPrice(@Param("companyId") int companyId, @Param("maxPrice") double maxPrice);

    @Query(value = "SELECT * FROM `coupon-system3-147`.customers_coupons cvc\n" +
            "join `coupon-system3-147`.coupons co\n" +
            "on cvc.coupons_id=co.id\n" +
            "where cvc.customer_id= :customerId", nativeQuery = true)
    Set<Coupon> findByCustomerId(@Param("customerId") int customerId);

    @Query(value = "select Exists (SELECT * FROM `coupon-system3-147`.coupons where `title`= :title and `company_id`= :companyId) as res;", nativeQuery = true)
    int existsByTitleAndCompanyId(@Param("title") String title, @Param("companyId") int companyId);

    boolean existsByTitle(String title);

    boolean existsByEndDateBefore(Date date);

    @Query(value = "select exists (SELECT * FROM `coupon-system3-147`.customers_coupons WHERE `CUSTOMER_ID` = :customerId and `coupons_id` = :couponId) as res", nativeQuery = true)
    int existsByCustomerIdAndCouponId(@Param("customerId") int customerId, @Param("couponId") int couponId);

    @Query(value = "SELECT * FROM `coupon-system3-147`.customers_coupons cvc\n" +
            "join `coupon-system3-147`.coupons co\n" +
            "on cvc.coupons_id=co.id\n" +
            "where cvc.customer_id= :customerId and co.category= :categoryName", nativeQuery = true)
    Set<Coupon> findAllByCustomerIdAndCategory(@Param("customerId") int customerId, @Param("categoryName") String categoryName);

    @Query(value = "SELECT * FROM `coupon-system3-147`.customers_coupons cvc\n" +
            "join `coupon-system3-147`.coupons co\n" +
            "on cvc.coupons_id=co.id\n" +
            "where cvc.customer_id=:customerId and co.price<= :maxPrice", nativeQuery = true)
    Set<Coupon> findAllByCustomerIdAndMaxPrice(@Param("customerId") int customerId, @Param("maxPrice") double maxPrice);

    List<Coupon> findByEndDateBefore(Date date);

    @Transactional//Statement.executeQuery() cannot issue statements that do not produce result sets
    @Modifying//Statement.executeQuery() cannot issue statements that do not produce result sets
    @Query(value = "DELETE FROM `coupon-system3-147`.`customers_coupons` WHERE (`coupons_id` = :couponId) and `coupons_id` <>0;\n", nativeQuery = true)
    void deleteCouponPurchase(@Param("couponId") int couponId);

    //To delete both the coupon and it's purchases by company ID when deleting a company by AdminService (with only 1 query)
    @Transactional//Statement.executeQuery() cannot issue statements that do not produce result sets
    @Modifying//Statement.executeQuery() cannot issue statements that do not produce result sets
    @Query(value = "delete c, cc\n" +
            "FROM `coupon-system3-147`.customers_coupons cc\n" +
            "right join `coupon-system3-147`.coupons c\n" +
            "on cc.coupons_id =c.id where company_id= :companyId and company_id <>0", nativeQuery = true)
    void deleteCouponAndPurchaseByCompanyId(@Param("companyId") int companyId);
}
