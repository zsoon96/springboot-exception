package com.example.exceptionprac.order;

import com.example.exceptionprac.coupon.Coupon;
import com.example.exceptionprac.coupon.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderService {

    private OrderRepository orderRepository;
    private CouponService couponService;

    @Autowired
    public OrderService(OrderRepository orderRepository, CouponService couponService) {
        this.orderRepository = orderRepository;
        this.couponService = couponService;
    }

    // 주문에 대한 쿠폰 적용
    public Orders order() {
        Orders orders = Orders.builder()
                .price(10_000D)
                .build();
        Coupon coupon = couponService.findById(1);
        orders.applyCoupon(coupon);
        return orderRepository.save(orders);
    }

    // 주문 조회
    public Orders findById(long id) {
        return orderRepository.findById(id).get();
    }
}
