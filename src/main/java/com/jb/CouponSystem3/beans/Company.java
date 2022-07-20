package com.jb.CouponSystem3.beans;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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

    // TODO: 05/07/2022 Remove payload CTOR 
//    public Company(CompanyPayLoad companyPayLoad) {
//        this.name = companyPayLoad.getName();
//        this.email = companyPayLoad.getEmail();
//        this.password = companyPayLoad.getPassword();
//        this.coupons = companyPayLoad.getCoupons();
//    }

}