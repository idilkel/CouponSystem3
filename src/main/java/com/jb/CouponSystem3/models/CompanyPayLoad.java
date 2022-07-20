package com.jb.CouponSystem3.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jb.CouponSystem3.beans.Coupon;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CompanyPayLoad {
    // TODO: 05/07/2022 Remove payload class
    @Column(nullable = false, length = 45)
    private String name;
    @Column(nullable = false, length = 45)
    private String email;
    @Column(nullable = false, length = 45)
    private String password;
    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "company")
    @Singular
    @ToString.Exclude
    @JsonManagedReference
    private List<Coupon> coupons = new ArrayList<>();

}
