package com.crafteam.deliveryapp.utils;

import com.crafteam.deliveryapp.entities.Customer;
import com.crafteam.deliveryapp.entities.DeliverySlot;
import com.crafteam.deliveryapp.enums.DeliveryMode;
import com.crafteam.deliveryapp.repositories.CustomerRepository;
import com.crafteam.deliveryapp.repositories.DeliverySlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final DeliverySlotRepository deliverySlotRepository;

    @Override
    public void run(String... args) throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("Salaheddine");
        customer.setLastName("LAHMAM");
        customer.setEmail("think@gmail.com");
        customerRepository.save(customer);

        Arrays.stream(DayOfWeek.values()).forEach(day -> {
            final List<DeliverySlot> daySlots = new ArrayList<>();
            for (DeliveryMode mode : DeliveryMode.values()) {
                LocalTime startTime = LocalTime.of(8, 0); // 08:00 AM
                LocalTime endTime = LocalTime.of(18, 0);  // 06:00 PM

                while (startTime.isBefore(endTime)) {
                    DeliverySlot slot = new DeliverySlot();
                    slot.setDay(day);
                    slot.setMode(mode);
                    slot.setStartTime(startTime);
                    slot.setEndTime(startTime.plusHours(1));
                    daySlots.add(slot);
                    startTime = startTime.plusHours(1);
                }
            }
            deliverySlotRepository.saveAll(daySlots);
        });
    }
}
