package com.example.Service;

import com.example.dao.NotificationDao;
import com.example.entity.Notification;
import com.example.poll.implementations.NewNotificationResolver;
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
        notificationDao.insertNotification(notification.getContent(), notification.getGroupId(),
                notification.getImportance(), notification.getAuthorId(), notification.getTitle());

        if(subscriber != null) {
            subscriber.update();
        }
    }

    public List<Notification> getNotifications() {
        return notificationDao.getNotificationsWithAuthorNames();
    }
}
