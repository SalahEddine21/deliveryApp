package com.crafteam.deliveryapp.services;

import com.crafteam.deliveryapp.dtos.OrderDTO;
import com.crafteam.deliveryapp.entities.Customer;
import com.crafteam.deliveryapp.entities.DeliverySlot;
import com.crafteam.deliveryapp.entities.Order;
import com.crafteam.deliveryapp.mappers.OrderMapper;
import com.crafteam.deliveryapp.repositories.CustomerRepository;
import com.crafteam.deliveryapp.repositories.DeliverySlotRepository;
import com.crafteam.deliveryapp.repositories.OrderRepository;
import jakarta.persistence.PessimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final DeliverySlotRepository deliverySlotRepository;
    private final OrderMapper orderMapper;

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
}
