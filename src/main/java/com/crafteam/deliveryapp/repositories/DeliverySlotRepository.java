package com.crafteam.deliveryapp.repositories;

import com.crafteam.deliveryapp.entities.DeliverySlot;
import com.crafteam.deliveryapp.enums.DeliveryMode;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliverySlotRepository extends JpaRepository<DeliverySlot, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<DeliverySlot> findById(Long id);

    List<DeliverySlot> findByModeAndDay(DeliveryMode mode, DayOfWeek day);
    List<DeliverySlot> findByDayAndModeAndMaxReservationsLessThan(DayOfWeek day, DeliveryMode mode, int maxReservations);
}

