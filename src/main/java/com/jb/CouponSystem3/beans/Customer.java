package com.jb.CouponSystem3.beans;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 45)
    @NotBlank
    private String firstName;
    @Column(nullable = false, length = 45)
    @NotBlank
    private String lastName;
    @Column(nullable = false, length = 45)
    @Email
    private String email;
    @Column(nullable = false, length = 45)
    @Length(min = 4, max = 12)
    private String password;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @Singular
    @ToString.Exclude
    private Set<Coupon> coupons = new HashSet<>();


}
