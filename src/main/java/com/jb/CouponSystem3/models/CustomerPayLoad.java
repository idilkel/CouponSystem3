package com.jb.CouponSystem3.models;

import com.jb.CouponSystem3.beans.Coupon;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomerPayLoad {
    // TODO: 05/07/2022 Remove payload class
    @Column(nullable = false, length = 45)
    private String firstName;
    @Column(nullable = false, length = 45)
    private String lastName;
    @Column(nullable = false, length = 45)
    private String email;
    @Column(nullable = false, length = 45)
    private String password;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @Singular
    @ToString.Exclude
    private Set<Coupon> coupons = new HashSet<>();
}
