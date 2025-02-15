package com.crafteam.deliveryapp.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private Long id;
    private Long customerId;
    private Long deliverySlotId;
    private LocalDateTime createdAt;
}
