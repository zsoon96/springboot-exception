package com.example.exceptionprac.delivery.service;

import com.example.exceptionprac.delivery.domain.Address;
import com.example.exceptionprac.delivery.domain.Delivery;
import com.example.exceptionprac.delivery.domain.DeliveryRepository;
import com.example.exceptionprac.delivery.domain.DeliveryStatus;
import com.example.exceptionprac.delivery.dto.DeliveryDto;
import com.example.exceptionprac.delivery.exception.DeliveryNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


// Mockito : Mock 객체를 손쉽게 사용할 수 있도록 지원해주는 대표적인 테스트 프레임워크
// Mock : 실제 객체를 만들어 사용하기에 시간, 비용 등의 소모가 높거나 객체 서로간의 의존성이 강해 구현하기 힘들 경우, "가짜 객체를 만들어서 사용하는 방법"을 말함
    // Mock을 사용하여 테스트를 진행 할 경우, 의존성을 가지는 객체를 제약없이 우리가 원하는 기능만으로 동작하도록 테스트 할 수 있다 !

// JUnit4에서 Mock 객체를 사용하기 위한 어노테이션
@RunWith(MockitoJUnitRunner.class)
public class DeliveryServiceTest {

    // 해당 클래스의 주입되는 객체가 있을 경우, 주입해주는 어노테이션
    @InjectMocks
    private DeliveryService deliveryService;

    // Mock 객체를 생성하는 어노테이션
    @Mock
    private DeliveryRepository deliveryRepository;

    @Test
    public void create() {
        // given
        Address address = buildAddress(); // 객체 생성
        DeliveryDto.CreationReq dto = buildCreateDto(address); // dto 객체 생성
        // given을 이용해 deliveryRepository에서 저장할 경우, 리턴할 값 지정
        given(deliveryRepository.save(any(Delivery.class))).willReturn(dto.toEntity());

        // when
        Delivery delivery = deliveryService.create(dto);

        // then
        assertThat(delivery.getAddress(), is(address));
    }

    @Test
    public void findById() {
        // given
        Address address = buildAddress();
        DeliveryDto.CreationReq dto = buildCreateDto(address);
        given(deliveryRepository.findById(anyLong())).willReturn(Optional.of(dto.toEntity()));

        // when
        Delivery delivery = deliveryService.findById(anyLong());

        // then
        assertThat(delivery.getAddress(), is(address));
    }

    @Test
    public void updateStatus() {
        // given
        Address address = buildAddress();
        DeliveryDto.CreationReq createDto = buildCreateDto(address);
        DeliveryDto.UpdateReq updateDto = buildUpdateDto();

        given(deliveryRepository.findById(anyLong())).willReturn(Optional.of(createDto.toEntity()));

        // when
        Delivery delivery = deliveryService.updateStatus(anyLong(), updateDto);

        // then
        assertThat(delivery.getLogs().get(0).getStatus(), is(updateDto.getStatus()));
    }

    @Test(expected = DeliveryNotFoundException.class)
    public void findById_존재하지않을경우_DeliveryNotFoundException() {
        // given
        Address address = buildAddress();
        DeliveryDto.CreationReq dto = buildCreateDto(address);
        given(deliveryRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        deliveryService.findById(anyLong());
    }

    private Address buildAddress() {
        return Address.builder()
                .address1("경기도")
                .address2("김포시")
                .zip("12345")
                .build();
    }

    private DeliveryDto.CreationReq buildCreateDto(Address address) {
        return DeliveryDto.CreationReq.builder()
                .address(address)
                .build();
    }

    private DeliveryDto.UpdateReq buildUpdateDto() {
        return DeliveryDto.UpdateReq.builder()
                .status(DeliveryStatus.DELIVERING)
                .build();
    }
}