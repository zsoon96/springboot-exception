package com.example.exceptionprac.delivery.controller;

import com.example.exceptionprac.controller.ErrorExceptionController;
import com.example.exceptionprac.delivery.domain.Address;
import com.example.exceptionprac.delivery.domain.DeliveryStatus;
import com.example.exceptionprac.delivery.dto.DeliveryDto;
import com.example.exceptionprac.delivery.service.DeliveryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryControllerTest {

    @InjectMocks
    private DeliveryController deliveryController;

    @Mock
    private DeliveryService deliveryService;

    // Json 객체를 java 객체로 deserialization하거나
    // java 객체를 Json 객체로 serialization할 때 사용하는 Jackson 라이브러리 클래스
    // 여기서는 객체를 json 문자열로 바꿔서 body에 실어보내는데 사용!
    private ObjectMapper ObjectMapper = new ObjectMapper();

    // 실제 객체와 비슷하지만, 테스트에 필요한 기능만 가지는 가짜 객체를 만들어서 배포하지 않고도 MVC 동작을 재현할 수 있는 클래스
    private MockMvc mockMvc;

    // MockMvc 초기화 세팅 작업
    @Before
    public void setUp() {
        // standaloneSetup() 메서드는 테스트할 컨트롤러를 수동으로 초기화 하고 주입함
        // setControllerAdvice() 메서드는 @ControllerAdvice로 지정한 ExceptionHandler가 Test에서 동작하도록 클래스를 지정해주기 위함
        mockMvc = MockMvcBuilders.standaloneSetup(deliveryController)
                .setControllerAdvice(new ErrorExceptionController())
                .build();
    }

    @Test
    public void create() throws Exception {
        // given
        Address address = buildAddress();
        DeliveryDto.CreationReq dto = buildCreateDto(address);
        given(deliveryService.create(any())).willReturn(dto.toEntity());

        // when
        ResultActions resultActions = requestCreate(dto); // create() 요청 (실행)

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address.address1", is(dto.getAddress().getAddress1())))
                .andExpect(jsonPath("$.address.address2", is(dto.getAddress().getAddress2())))
                .andExpect(jsonPath("$.address.zip", is(dto.getAddress().getZip())));
    }

    @Test
    public void getDelivery() throws Exception {
        // given
        Address address = buildAddress();
        DeliveryDto.CreationReq dto = buildCreateDto(address);
        given(deliveryService.findById(anyLong())).willReturn(dto.toEntity());

        // when
        ResultActions resultActions = requestGetDelivery(); // getDelivery() 요청 (실행)

        // then
        resultActions
                // andExpect() 메소드는 응답을 검증하는 역할
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address.address1", is(dto.getAddress().getAddress1())))
                .andExpect(jsonPath("$.address.address2", is(dto.getAddress().getAddress2())))
                .andExpect(jsonPath("$.address.zip", is(dto.getAddress().getZip())));
    }

//    @Test
//    public void updateStatus() throws Exception {
//        // given
//        Address address = buildAddress();
//        DeliveryDto.CreationReq createDto = buildCreateDto(address);
//        DeliveryDto.UpdateReq updateDto = buildUpdateDto();
//        given(deliveryService.findById(anyLong())).willReturn(createDto.toEntity());
//
//        // when
//        ResultActions resultActions = requestUpdateStatus(updateDto);
//
//        // then
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.logs.status", is(updateDto.getStatus())));
//    }

    private ResultActions requestCreate(DeliveryDto.CreationReq dto) throws Exception {
        // perform() 메소드를 통해 요청 전송
        return mockMvc.perform(post("/deliveries/create") // HTTP METHOD(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper.writeValueAsString(dto))) // dto 객체를 string 타입으로 변환
                .andDo(print()); // 요청/응답 전체 메세지 확인
    }

    private ResultActions requestGetDelivery() throws Exception {
        return mockMvc.perform(get("/deliveries/mylog/" + anyLong())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

//    private ResultActions requestUpdateStatus(DeliveryDto.UpdateReq dto) throws Exception {
//        return mockMvc.perform(post("/deliveries/" + anyLong() + "/logs")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(ObjectMapper.writeValueAsString(dto)))
//                .andDo(print());
//    }

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