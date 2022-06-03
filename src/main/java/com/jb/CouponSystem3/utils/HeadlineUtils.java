package com.jb.CouponSystem3.utils;

public class HeadlineUtils {


    private static int count = 1;

    public static void printHeadlineWithCount(String content) {
        System.out.printf("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Test #%d - %s @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n", count++, content);
    }

    public static void printHeadline(String content) {
        System.out.printf("\n*---------------------------------------*%-20s*-------------------------------------------*\n", content);
    }

    public static void printHeadline2(String content) {
        System.out.printf("\n*---------------------%-10s---------------------*\n", content);
    }

}
