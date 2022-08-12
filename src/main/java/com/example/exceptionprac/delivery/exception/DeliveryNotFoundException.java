package com.example.exceptionprac.delivery.exception;

public class DeliveryNotFoundException extends RuntimeException {
    private long id;

    public DeliveryNotFoundException(long id) {
        super(id + "는 찾을 수 없습니다.");
        this.id = id;
    }
}
