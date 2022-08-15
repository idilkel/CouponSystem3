package com.jb.CouponSystem3.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
    @NotBlank
    private String name;
    @Column(nullable = false, length = 45)
    @Email
    private String email;
    @Column(nullable = false, length = 45)
    @Length(min = 4, max = 12)
    private String password;
    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "company")
    @Singular
    @ToString.Exclude
    @JsonIgnore
    private List<Coupon> coupons = new ArrayList<>();


}