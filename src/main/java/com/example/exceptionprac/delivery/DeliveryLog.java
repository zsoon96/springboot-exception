package com.example.exceptionprac.delivery;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class DeliveryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Enum 타입을 db에 저장하게 되면 기본적으로 정의한 타입명이 아닌 index로 저장이 되는데,
    // EnumType.STRING을 통해 index가 아닌 정의한 타입명 그대로 db에 반영할 수 있게 설정 가능 ( 1 -> CANCELED )
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false, updatable = false)
    private DeliveryStatus status;

    @Embedded
    private DateTime dateTime;

    // @Transient를 통해 해당변수를 임시 데이터로 보고 테이블과 맵핑되지 않도록 설정
    @Transient
    private DeliveryStatus lastStatus;

    // @JsonProperty를 통해 해당 필드는 오직 쓰려는 경우에만 접근 허용되도록 설정
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne // 엔티티 기준 Delivery 클래스와의 관계
    @JoinColumn(name = "delivery_id", nullable = false, updatable = false) // DeliveryLog 테이블에 delivery_id 라는 컬럼명으로 정의
    private Delivery delivery;
}
