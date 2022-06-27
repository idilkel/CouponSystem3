package com.jb.CouponSystem3.jobs;

import com.jb.CouponSystem3.beans.Coupon;
import com.jb.CouponSystem3.repos.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class ExpiredCouponsRemoval {

    public static final int SLEEP_TIME = 1000 * 30;

    public final CouponRepository couponRepository;

    //In-order to check the expired coupon deletion set the hour and minute in the if on line 25 to the current hour and minute; rerun; and wait half a minute

    @Scheduled(fixedRate = SLEEP_TIME)
    public void dailyDeleteExpiredCoupons() {
        if (LocalTime.now().getHour() == 0 & LocalTime.now().getMinute() == 0) { //LocalTime.MIDNIGHT might require exact time with seconds
            for (Coupon coupon : couponRepository.findByEndDateBefore(Date.valueOf(LocalDate.now()))) {
                couponRepository.deleteCouponPurchase(coupon.getId());
                couponRepository.deleteById(coupon.getId());
                System.out.printf("Coupon#%d was deleted", coupon.getId());
                System.out.println();
            }
        }
    }
}
