package com.crafteam.deliveryapp.services;

import com.crafteam.deliveryapp.dtos.DeliverySlotDTO;
import com.crafteam.deliveryapp.dtos.OrderDTO;
import com.crafteam.deliveryapp.entities.Customer;
import com.crafteam.deliveryapp.entities.DeliverySlot;
import com.crafteam.deliveryapp.entities.Order;
import com.crafteam.deliveryapp.enums.DeliveryMode;
import com.crafteam.deliveryapp.mappers.DeliverySlotMapper;
import com.crafteam.deliveryapp.mappers.OrderMapper;
import com.crafteam.deliveryapp.repositories.CustomerRepository;
import com.crafteam.deliveryapp.repositories.DeliverySlotRepository;
import com.crafteam.deliveryapp.repositories.OrderRepository;
import com.crafteam.deliveryapp.utils.DeliveryUtils;
import jakarta.persistence.PessimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final DeliverySlotRepository deliverySlotRepository;
    private final OrderMapper orderMapper;
    private final DeliverySlotMapper deliverySlotMapper;

    @Retryable(
            retryFor = PessimisticLockException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 500)
    )
    @Transactional
    public OrderDTO bookDelivery(Long customerId, Long deliverySlotId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        DeliverySlot deliverySlot = deliverySlotRepository.findById(deliverySlotId)
                .orElseThrow(() -> new RuntimeException("Delivery slot not found"));

        long currentReservations = orderRepository.countByDeliverySlotId(deliverySlotId);
        if (currentReservations >= deliverySlot.getMaxReservations()) {
            throw new RuntimeException("Delivery slot is fully booked");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setDeliverySlot(deliverySlot);
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toDTO(savedOrder);
    }

    public List<DeliverySlotDTO> getAvailableSlots(Integer dayIndex, String deliveryMode){
        final DayOfWeek day = DayOfWeek.of(dayIndex);
        final DeliveryMode mode = DeliveryMode.fromString(deliveryMode);
        final List<DeliverySlot> availableSlots = this.deliverySlotRepository.
                findByDayAndModeAndMaxReservationsLessThan(day, mode, DeliveryUtils.DELIVERY_MAX_RESERVATION);
        return deliverySlotMapper.toDto(availableSlots);
    }
}
