package com.crafteam.deliveryapp.controllers;

import com.crafteam.deliveryapp.dtos.OrderDTO;
import com.crafteam.deliveryapp.services.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping("/book")
    public ResponseEntity<OrderDTO> bookDelivery(
            @RequestParam Long customerId,
            @RequestParam Long deliverySlotId
    ) {
        OrderDTO order = deliveryService.bookDelivery(customerId, deliverySlotId);
        return ResponseEntity.ok(order);
    }
}

