package com.example.poll.implementations;

import com.example.Service.NotificationService;
import com.example.poll.core.Resolver;
import org.json.simple.JSONObject;

import java.util.Optional;

public class NewNotificationResolver implements Resolver {
    NotificationService notificationService = new NotificationService();

    private boolean isNewNotification = false;

    public void update() {
        isNewNotification = true;
    }


    @Override
    public Optional<JSONObject> resolve() {
        if (!isNewNotification) {
            return Optional.empty();
        } else {
            isNewNotification = false;
            try {
                String newNotification = notificationService.getNewestNotification();
                JSONObject json = new JSONObject();
                json.put("content", newNotification);
                return Optional.of(json);
            } catch (Exception e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }
    }
}
