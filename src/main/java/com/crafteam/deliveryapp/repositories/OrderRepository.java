package com.crafteam.deliveryapp.repositories;

import com.crafteam.deliveryapp.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByDeliverySlotId(Long deliverySlotId);
    long countByDeliverySlotId(Long deliverySlotId);
}

