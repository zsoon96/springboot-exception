package com.example.exceptionprac.coupon;

import com.example.exceptionprac.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "discount_amount")
    private double discountAmount; // 할인 가격

    @Column(name = "use")
    private boolean use; // 사용 여부

    @JsonIgnore // @JsonIgnore을 통해 데이터 교환 시, 해당 필드는 응답값에 포함되지 않음
    @OneToOne(mappedBy = "coupon")
    private Order order; // 주문

    @Builder
    public Coupon (double discountAmount) {
        this.discountAmount = discountAmount;
        this.use = false;
    }

    public void use (Order order) {
        this.order = order;
        this.use = true;
    }

}
