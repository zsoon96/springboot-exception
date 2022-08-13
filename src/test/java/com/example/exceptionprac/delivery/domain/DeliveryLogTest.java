package com.example.exceptionprac.delivery.domain;

import com.example.exceptionprac.delivery.exception.DeliveryAlreadyDeliveringException;
import com.example.exceptionprac.delivery.exception.DeliveryStatusEqualsException;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeliveryLogTest {

    @Test
    public void delivery_pending_로그저장() {
        final DeliveryStatus status = DeliveryStatus.PENDING;
        final DeliveryLog log = buildLog(buildDelivery(), status);

        assertThat(status, is(log.getStatus()));
    }

    @Test
    public void delivery_delivering() {
        final Delivery delivery = buildDelivery();
        final DeliveryStatus status = DeliveryStatus.PENDING;

        delivery.addLog(status);
        delivery.addLog(DeliveryStatus.DELIVERING);
    }

    @Test
    public void delivery_canceled() {
        final Delivery delivery = buildDelivery();
        final DeliveryStatus status = DeliveryStatus.PENDING;

        delivery.addLog(status);
        delivery.addLog(DeliveryStatus.CANCELED);
    }

    @Test
    public void delivery_completed() {
        final Delivery delivery = buildDelivery();
        final DeliveryStatus status = DeliveryStatus.DELIVERING;

        delivery.addLog(status);
        delivery.addLog(DeliveryStatus.COMPLETED);
    }

    // @Test에 발생할 예외 클래스를 expected 옵션에 넣어주어 해당 예외가 발생함을 검증
    @Test(expected = DeliveryStatusEqualsException.class)
    public void 동일한_status_변경시_DeliveryStatusEqualsException() {
        // 예외 발생 코드
        Delivery delivery = buildDelivery();
        DeliveryStatus status = DeliveryStatus.DELIVERING;

        delivery.addLog(status);
        delivery.addLog(DeliveryStatus.DELIVERING);
    }

    @Test(expected = DeliveryAlreadyDeliveringException.class)
    public void 배송시작후_취소시_DeliveryAlreadyDeliveringException() {
        Delivery delivery = buildDelivery();
        DeliveryStatus status = DeliveryStatus.DELIVERING;

        delivery.addLog(status);
        delivery.addLog(DeliveryStatus.CANCELED);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 완료상태_변경시_IllegalArgumentException() {
        Delivery delivery = buildDelivery();
        DeliveryStatus status = DeliveryStatus.COMPLETED;

        delivery.addLog(status);
        delivery.addLog(DeliveryStatus.CANCELED);
    }

    private DeliveryLog buildLog(Delivery delivery, DeliveryStatus status) {
        return DeliveryLog.builder()
                .delivery(delivery)
                .status(status)
                .build();
    }

    private Delivery buildDelivery() {
        return Delivery.builder()
                .build();
    }

}