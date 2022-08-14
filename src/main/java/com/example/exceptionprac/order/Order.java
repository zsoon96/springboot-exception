package com.example.exceptionprac.order;

import com.example.exceptionprac.coupon.Coupon;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price")
    private double price;

    // OneToOne일 때, 연관관계 주인은 OneToMany로 뱐경될 가능성이 있다고 판단되는 엔티티로 설정하는게 좋음
    @OneToOne
    @JoinColumn(name = "coupon_id" , referencedColumnName = "id", nullable = false)
    private Coupon coupon;

    @Builder
    public Order (double price) {
        this.price = price;
    }

    public void applyCoupon ( Coupon coupon ) {
        this.coupon = coupon;
        coupon.use(this); // 쿠폰 사용 처리
        price -= coupon.getDiscountAmount(); // 할인 가격 산출
    }
}
