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

    public void setToFalse() {
        this.isNewNotification = false;
    }


    @Override
    public Optional<JSONObject> resolve() {
        if (!isNewNotification) {
            return Optional.empty();
        } else {
            try {
                Notification newNotification = notificationService.getNewestNotification();

                NewNotificationResolver nnr = this;

                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                nnr.setToFalse();
                            }
                        },
                        500
                );

                return Optional.of(newNotification.toJSONObject());
            } catch (Exception e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }
    }
}
