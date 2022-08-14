package com.example.exceptionprac.order;

import com.example.exceptionprac.coupon.Coupon;
import com.example.exceptionprac.coupon.CouponService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.transaction.Transactional;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(SpringRunner.class) // JUnit4에서 지원하는 해당 어노테이션을 사용하면 @Autowired, @MockBean에 해당 되는 것들만 apllication context를 로딩하게 됨으로 필요에 맞게 사용하는 역할
@SpringBootTest // @SpringBootTest를 사용하면 application context를 전부 로딩해서 잘못하면 프로젝트가 무겁게 동작할 가능성이 있음
@Transactional
public class OrdersServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CouponService couponService;

    @Test // 실패
    public void order_쿠폰할인적용() {
        Orders orders = orderService.order();

        assertThat(orders.getPrice(), is(9_000D));
        assertThat(orders.getId(), is(notNullValue()));
        assertThat(orders.getCoupon(), is(notNullValue()));

        Orders findOrders = orderService.findById(orders.getId());
        System.out.println("couponId: " + findOrders.getCoupon().getId());

        Coupon coupon = couponService.findById(1);
        assertThat(coupon.isUse(), is(true));
        assertThat(coupon.getId(), is(notNullValue()));
        assertThat(coupon.getDiscountAmount(), is(notNullValue()));
    }

    @Test // 실패
    public void use_메서드에_order_필요이유() {
        Orders order = orderService.order();
        assertThat(order.getPrice(), is(9_000D));

        Coupon coupon = order.getCoupon();
        assertThat(coupon.getOrders(), is(notNullValue()));

    }
}