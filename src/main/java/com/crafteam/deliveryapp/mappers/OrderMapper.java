package com.crafteam.deliveryapp.mappers;

import com.crafteam.deliveryapp.dtos.OrderDTO;
import com.crafteam.deliveryapp.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "deliverySlot.id", target = "deliverySlotId")
    OrderDTO toDTO(Order order);

    @Mapping(source = "customerId", target = "customer.id")
    @Mapping(source = "deliverySlotId", target = "deliverySlot.id")
    Order toEntity(OrderDTO orderDTO);
}
