package com.example.videogame_distribution_app.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Async
    public void sendGameNotification(String gameName) {
        try {
            Thread.sleep(2000); // Simulate delay
            System.out.println("Notification sent for game: " + gameName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
