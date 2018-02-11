package pl.edu.agh.kis.poll.implementations;

import org.springframework.stereotype.Component;
import pl.edu.agh.kis.application.observer.Observer;
import pl.edu.agh.kis.application.service.NotificationService;
import pl.edu.agh.kis.application.entity.Notification;
import pl.edu.agh.kis.poll.core.Resolver;
import org.json.simple.JSONObject;

import java.util.Optional;

@Component
public class NewNotificationResolver implements Resolver, Observer {
    private NotificationService notificationService = new NotificationService();

    private boolean isNewNotification = false;

    public void update() {
        this.isNewNotification = true;
    }

    public void setNotNewNotification() {
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
                                nnr.setNotNewNotification();
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
