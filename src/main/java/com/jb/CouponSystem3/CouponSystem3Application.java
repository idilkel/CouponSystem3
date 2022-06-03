package com.jb.CouponSystem3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.jb.CouponSystem3"}, excludeFilters = @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "com.jb.CouponSystem3.clr.off.*"))
@EnableScheduling
public class CouponSystem3Application {

    public static void main(String[] args) {
        SpringApplication.run(CouponSystem3Application.class, args);
    }

}
