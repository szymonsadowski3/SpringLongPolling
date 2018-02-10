package com.example.poll.implementations;

import com.example.Service.NotificationService;
import com.example.entity.Notification;
import com.example.poll.core.Resolver;
import org.json.simple.JSONObject;

import java.util.Optional;

public class NewNotificationResolver implements Resolver {
    private NotificationService notificationService = new NotificationService();

    private boolean isNewNotification = false;

    public void update() {
        this.isNewNotification = true;
    }


    @Override
    public Optional<JSONObject> resolve() {
        if (!isNewNotification) {
            return Optional.empty();
        } else {
            try {
                Notification newNotification = notificationService.getNewestNotification();
                isNewNotification = false;
                return Optional.of(newNotification.toJSONObject());
            } catch (Exception e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }
    }
}
