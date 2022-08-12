package com.example.exceptionprac.delivery.exception;

import com.example.exceptionprac.delivery.DeliveryStatus;

public class DeliveryStatusEqualsException extends RuntimeException{

    private DeliveryStatus status;

    public DeliveryStatusEqualsException(DeliveryStatus status) {
        super(status.name() + "동일한 상태로 변경할 수 없습니다.");
        this.status = status;
    }
}
