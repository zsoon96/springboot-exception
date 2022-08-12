package com.example.exceptionprac.delivery.service;

import com.example.exceptionprac.delivery.domain.Delivery;
import com.example.exceptionprac.delivery.domain.DeliveryRepository;
import com.example.exceptionprac.delivery.domain.DeliveryStatus;
import com.example.exceptionprac.delivery.dto.DeliveryDto;
import com.example.exceptionprac.delivery.exception.DeliveryNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class DeliveryService {

    private DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public Delivery create (DeliveryDto.CreationReq dto) {
        Delivery delivery = dto.toEntity(); // delivery 객체 생성
        delivery.addLog(DeliveryStatus.PENDING); // 해당 delivery의 deliveryLog List에 현재 상태 추값
        return deliveryRepository.save(delivery); // db에 저장
    }

    public Delivery findById (long id) {
        Optional<Delivery> delivery = deliveryRepository.findById(id); // id로 delivery 조회
        delivery.orElseThrow(()-> new DeliveryNotFoundException(id)); // 없으면 예외 처리
        return delivery.get();
    }

    public Delivery updateStatus (long id, DeliveryDto.UpdateReq dto) {
        Delivery delivery = findById(id); // id로 delivery 조회하는 메서드 호출
        delivery.addLog(dto.getStatus()); // 찾은 delivery의 추적 상태 추가
        return delivery;
    }
}
