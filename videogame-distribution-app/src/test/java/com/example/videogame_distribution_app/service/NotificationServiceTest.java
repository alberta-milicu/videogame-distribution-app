package com.example.videogame_distribution_app.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Test
    void sendGameNotificationTest() throws Exception {
        // Create spy for behaviour checking
        NotificationService notificationService = Mockito.spy(NotificationService.class);
        notificationService.sendGameNotification("Test Game");
        Thread.sleep(2500);
        verify(notificationService, times(1)).sendGameNotification("Test Game");
    }
}
