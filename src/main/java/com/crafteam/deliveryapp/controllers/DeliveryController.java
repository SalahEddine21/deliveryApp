package com.crafteam.deliveryapp.controllers;

import com.crafteam.deliveryapp.dtos.DeliverySlotDTO;
import com.crafteam.deliveryapp.dtos.OrderDTO;
import com.crafteam.deliveryapp.services.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/available/{dayIndex}/{mode}")
    public ResponseEntity<List<DeliverySlotDTO>> getAvailableSlots(@PathVariable Integer dayIndex, @PathVariable String mode) {
        List<DeliverySlotDTO> slots = deliveryService.getAvailableSlots(dayIndex, mode);
        return ResponseEntity.ok(slots);
    }
}

