package com.crafteam.deliveryapp.entities;

import com.crafteam.deliveryapp.enums.DeliveryMode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class DeliverySlot {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliveryMode mode;

    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer maxReservations;

    @OneToMany(mappedBy = "deliverySlot")
    private List<Order> orders;

}
