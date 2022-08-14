package com.example.exceptionprac.coupon;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CouponTest {

    @Test // 성공
    public void builder() {

        Coupon coupon = Coupon.builder()
                .discountAmount(10)
                .build();

        assertThat(coupon.getDiscountAmount()).isEqualTo(10);
        assertThat(coupon.isUse()).isEqualTo(false);
    }
}