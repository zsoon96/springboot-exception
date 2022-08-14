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
    public Order order() {
        Order order = Order.builder()
                .price(10000)
                .build();
        Coupon coupon = couponService.findById(1L);
        order.applyCoupon(coupon);
        return orderRepository.save(order);
    }

    // 주문 조회
    public Order findById(Long id) {
        return orderRepository.findById(id).get();
    }
}
