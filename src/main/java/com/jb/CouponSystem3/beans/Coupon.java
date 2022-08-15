package com.jb.CouponSystem3.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Table(name = "coupons")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Company company;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;
    @Column(nullable = false, length = 45)
    private String title;
    @Column(nullable = false, length = 45)
    private String description;

    //    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private java.sql.Date startDate;

    //    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private java.sql.Date endDate;
    @Column(nullable = false)
    @Min(0)
    private int amount;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false, length = 45)
    private String image;


    //For comparison of the set on a unique base
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return id == coupon.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
