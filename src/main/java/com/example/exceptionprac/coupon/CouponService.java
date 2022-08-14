package com.example.exceptionprac.coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class CouponService {

    private CouponRepository couponRepository;

    @Autowired
    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon findById(long id) {
        return couponRepository.findById(id).get(); // .get()이 문제...여기서 테스트 막힘
    }
}
