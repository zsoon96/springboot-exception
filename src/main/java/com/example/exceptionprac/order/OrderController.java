//package com.example.exceptionprac.order;
//
//import com.example.exceptionprac.coupon.Coupon;
//import com.example.exceptionprac.coupon.CouponService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/orders")
//public class OrderController {
//
//    private OrderService orderService;
//    private CouponService couponService;
//
//    @Autowired
//    public OrderController(OrderService orderService, CouponService couponService) {
//        this.orderService = orderService;
//        this.couponService = couponService;
//    }
//
//    // 주문 시 쿠폰 적용
//    @GetMapping
//    public ResponseEntity<Order> getOrders() {
//        Order order = orderService.order();
//        return ResponseEntity.ok(order);
//    }
//
//    // 부분 조문 조회
//    @GetMapping("/{id}")
//    public ResponseEntity getOrder(@PathVariable("id") Long id) {
//        orderService.findById(id);
//        return ResponseEntity.ok("해당 주문 조회 성공!");
//    }
//
//    // 쿠폰 조회
//    @GetMapping("/coupon/{id}")
//    public ResponseEntity<Coupon> getCoupon(@PathVariable("id") Long id) {
//        Coupon coupon = couponService.findById(id);
//        return ResponseEntity.ok(coupon);
//    }
//}
