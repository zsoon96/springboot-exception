package com.example.exceptionprac.delivery.domain;

import com.example.exceptionprac.delivery.exception.DeliveryAlreadyDeliveringException;
import com.example.exceptionprac.delivery.exception.DeliveryStatusEqualsException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
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

    @Builder
    public DeliveryLog (DeliveryStatus status, Delivery delivery) {
        verifyStatus(status, delivery); // 배송 추적 검증
        setStatus(status); // 추적 상태 변경
        this.delivery = delivery;
    }

    /////////////////////////////////////////////////////////////////////

    // 객체의 상태는 언제는 자기 자신이 관리해야 한 곳에서 관리 가능 = 유지보수 향상 / 객체 지향적 코드

    // 배송 추적 처리 메서드
    private void verifyStatus(DeliveryStatus status, Delivery delivery) {
        if (!delivery.getLogs().isEmpty()) {
            lastStatus = getLastStatus(delivery); // 마지막 추적 상태 확인
            verifyLastStatusEquals(status); // 마지막 추적 상태와 파라미터 값과 같은지 확인
            verifyAlreadyCompleted(); // 마지막 추적 상태가 COMPLETED일 경우 추적 종료
        }
    }

    // 요청받은 delivery의 마지막 배송 추적 상태를 확인하는 메서드
    private DeliveryStatus getLastStatus(Delivery delivery) {
        int lastIndex = delivery.getLogs().size() - 1;
        return delivery.getLogs().get(lastIndex).getStatus();
    }

    // 요청받은 추적 내용과 마지막 추적 내용과 같은지 검증하는 메서드
    private void verifyLastStatusEquals(DeliveryStatus status) {
        if ( lastStatus == status ) throw new DeliveryStatusEqualsException(lastStatus);
    }

    // 마지막 추적 상태가 COMPLETED인지 확인하는 메서드
    private void verifyAlreadyCompleted() {
        if ( lastStatus == DeliveryStatus.COMPLETED )
            throw new IllegalArgumentException("이미 완료되어 변경할 수 없습니다.");
    }

    // 배송 추적 상태 변경하는 메서드
    private void setStatus(DeliveryStatus status) {
        switch(status) {
            case DELIVERING:
                delivering();
                break;
            case COMPLETED:
                completed();
                break;
            case CANCELED:
                canceled();
                break;
            case PENDING:
                pending();
                break;
            default:
                throw new IllegalArgumentException(status.name() + "을 찾을 수 없습니다.");
        }
    }

    private void delivering() {
        this.status = DeliveryStatus.DELIVERING;
    }

    private void completed() {
        this.status = DeliveryStatus.COMPLETED;
    }

    private void canceled() {
        verifyNotYetDelivering(); // 배송중인지 확인
        this.status = DeliveryStatus.CANCELED;
    }

    private void pending() {
        this.status = DeliveryStatus.PENDING;
    }

    // 배송중인지 검증하는 메서드
    private void verifyNotYetDelivering() {
        if ( this.lastStatus != DeliveryStatus.PENDING )
            throw new DeliveryAlreadyDeliveringException();
    }
}
