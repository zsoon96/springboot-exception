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
        delivery.addLog(DeliveryStatus.PENDING); // 해당 delivery의 deliveryLog List에 현재 상태 추ㅔ
        return deliveryRepository.save(delivery); // db에 저장
        // Delivery가 시작되면 DeliveryLog에는 반드시 PENDING이어야 한다고 가정했을경우,
        // addLog() 편의 메소드를 이용하여 두 객체에 모두 필요한 값을 넣어기
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

    // 고아 객체 활용시, Delivery와 연결된 DeliveryLog의 참조를 제거하므로서 자동으로 데이터가 삭제됨
    public Delivery removeLogs(long id) {
        Delivery delivery = findById(id);
        delivery.getLogs().clear(); // Delivery의 DeliveryLog 참조 제거 = 삭제
        return delivery; // DeliveryLog 삭제 여부를 위한 확인 (실수하기 쉬운 부분이기에 확인 꼭 하기!)
    }

    // Delivery와 DeliveryLog는 연관관계를 맺고 있기 때문에 Delivery만 삭제 불가능
    // DeliveryLog 삭제 후, Delivery 삭제가 가능
    public void remove(long id) {
        deliveryRepository.deleteById(id);
    }

}
