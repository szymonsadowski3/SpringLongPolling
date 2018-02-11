package pl.edu.agh.kis.poll.implementations;

import org.springframework.stereotype.Component;
import pl.edu.agh.kis.application.observer.Observer;
import pl.edu.agh.kis.application.service.NotificationService;
import pl.edu.agh.kis.application.entity.Notification;
import pl.edu.agh.kis.poll.core.Resolver;
import org.json.simple.JSONObject;

import java.util.Optional;

/**
 * Resolver that is successfully resolving Promises, when new record has been added to notification table
 */
@Component
public class NewNotificationResolver implements Resolver, Observer {
    private NotificationService notificationService = new NotificationService();

    private boolean isNewNotification = false;

    /**
     * {@inheritDoc}
     */
    @Override
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

                NewNotificationResolver nnr = this;

                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                nnr.setIsNewNotification(false);
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

    /**
     * Sets new isNewNotification.
     *
     * @param isNewNotification New value of isNewNotification.
     */
    public void setIsNewNotification(boolean isNewNotification) {
        this.isNewNotification = isNewNotification;
    }
}
