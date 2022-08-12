package com.example.exceptionprac.delivery.dto;

import com.example.exceptionprac.delivery.domain.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryDto {

    // 배송 생성 시 요청
    @NoArgsConstructor
    @Getter
    public static class CreationReq {
        @Valid
        private Address address;

        @Builder
        public CreationReq(Address address) {
            this.address = address;
        }

        public Delivery toEntity() {
            return Delivery.builder()
                    .address(address)
                    .build();
        }
    }

    // 배송 업데이트 시 요청
    @Getter
    @NoArgsConstructor
    public static class UpdateReq {
        private DeliveryStatus status;

        public UpdateReq(DeliveryStatus status) {
            this.status = status;
        }
    }


    // 요청에 따른 최종 응답
    @Getter
    @NoArgsConstructor
    public static class Res {
        private Address address;
        private List<LogRes> logs;

        // 최종 응답 값
        public Res(Delivery delivery) {
            this.address = delivery.getAddress();
            this.logs = delivery.getLogs()
                    .parallelStream().map(LogRes::new)
                    .collect(Collectors.toList());
        }
    }


    // 배송 추적 상태를 담아줄 dto
    @Getter
    public static class LogRes {
        private DeliveryStatus status;
        private DateTime dateTime;

        public LogRes(DeliveryLog log) {
            this.status = log.getStatus();
            this.dateTime = log.getDateTime();
        }
    }
}
