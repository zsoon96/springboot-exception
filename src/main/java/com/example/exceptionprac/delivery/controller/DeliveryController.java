package com.example.exceptionprac.delivery.controller;

import com.example.exceptionprac.delivery.dto.DeliveryDto;
import com.example.exceptionprac.delivery.service.DeliveryService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("deliveries")
public class DeliveryController {

    private DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("/create")
    public DeliveryDto.Res create (@RequestBody @Valid DeliveryDto.CreationReq dto) {
        return new DeliveryDto.Res (deliveryService.create(dto));
    }

    @GetMapping("/mylog/{id}")
    public DeliveryDto.Res getDelivery (@PathVariable Long id) {
        return new DeliveryDto.Res (deliveryService.findById(id));
    }

    @PostMapping("/{id}/logs")
    public DeliveryDto.Res updateDelivery (@PathVariable Long id, @RequestBody DeliveryDto.UpdateReq dto) {
        return new DeliveryDto.Res (deliveryService.updateStatus(id, dto));
    }
}
