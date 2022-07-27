package com.jb.CouponSystem3.repos;

import com.jb.CouponSystem3.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Boolean existsByNameOrEmail(String name, String email);

    Boolean existsByEmailAndPassword(String email, String password);

    @Query(value = "SELECT `id` FROM `coupon-system3-147`.`companies` where `email`= :email", nativeQuery = true)
    int getIdByEmail(@Param("email") String email);

    Company findTop1ByEmail(String email);

    List<Company> findAllByOrderById();

    List<Company> findAllById(int companyId);

}
