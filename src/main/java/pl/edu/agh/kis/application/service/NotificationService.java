package pl.edu.agh.kis.application.service;

import pl.edu.agh.kis.application.dao.NotificationDao;
import pl.edu.agh.kis.application.entity.Notification;
import pl.edu.agh.kis.poll.implementations.NewNotificationResolver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private NewNotificationResolver subscriber;
    private NotificationDao notificationDao = new NotificationDao();

    public NotificationService() {

    }

    public NotificationService(NewNotificationResolver subscriber) {
        this.subscriber = subscriber;
    }

    public void addSubscriber(NewNotificationResolver subscriber) {
        this.subscriber = subscriber;
    }

    public Notification getNewestNotification() {
        return notificationDao.getNewestNotification();
    }

    public void insertNotification(Notification notification) {
        notificationDao.insertNotification(notification.getContent(),
                notification.getImportance(), notification.getAuthorName(), notification.getTitle());

        if(subscriber != null) {
            subscriber.update();
        }
    }

    public List<Notification> getNotifications() {
        return notificationDao.getNotificationsWithAuthorNames();
    }
}
