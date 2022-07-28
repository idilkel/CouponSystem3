package com.jb.CouponSystem3.models;

import com.jb.CouponSystem3.beans.Category;
import com.jb.CouponSystem3.beans.Coupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NotCouponPayLoad {
    private int id;
    private int companyId;
    @Enumerated(EnumType.STRING)
    private Category category;

    private String title;

    private String description;

    private java.sql.Date startDate;


    private java.sql.Date endDate;

    private int amount;

    private double price;

    private String image;

    public NotCouponPayLoad(Coupon coupon) {
        this.companyId = coupon.getCompany().getId();
        this.category = coupon.getCategory();
        this.title = coupon.getTitle();
        this.description = coupon.getDescription();
        this.startDate = coupon.getStartDate();
        this.endDate = coupon.getEndDate();
        this.amount = coupon.getAmount();
        this.price = coupon.getPrice();
        this.image = coupon.getImage();
    }


}
