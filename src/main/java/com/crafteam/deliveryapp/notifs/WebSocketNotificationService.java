package com.crafteam.deliveryapp.notifs;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void notifySlotUpdate(Long slotId) {
        messagingTemplate.convertAndSend("/topic/delivery-slots", slotId);
    }
}
