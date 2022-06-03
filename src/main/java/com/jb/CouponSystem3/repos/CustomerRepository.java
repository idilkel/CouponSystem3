package com.jb.CouponSystem3.repos;

import com.jb.CouponSystem3.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Boolean existsByEmail(String email);

    //to get only once a cp to db for two checks
    Boolean existsByIdOrEmail(int id, String email);

    Boolean existsByEmailAndPassword(String email, String password);

    @Query(value = "SELECT id FROM `coupon-system3-147`.customers where email= :email", nativeQuery = true)
    int getIdByEmail(@Param("email") String email);
}
