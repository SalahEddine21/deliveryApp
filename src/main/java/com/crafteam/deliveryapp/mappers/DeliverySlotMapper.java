package com.crafteam.deliveryapp.mappers;

import com.crafteam.deliveryapp.dtos.DeliverySlotDTO;
import com.crafteam.deliveryapp.entities.DeliverySlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeliverySlotMapper {

    DeliverySlotMapper INSTANCE = Mappers.getMapper(DeliverySlotMapper.class);

    @Mapping(target = "orderIds", source = "orders") // Convert Order list to order IDs
    DeliverySlotDTO toDto(DeliverySlot deliverySlot);

    DeliverySlot toEntity(DeliverySlotDTO deliverySlotDTO);

    List<DeliverySlotDTO> toDto(List<DeliverySlot> deliverySlots);
}