package com.example.Service;

import com.example.dao.NotificationDao;
import com.example.poll.implementations.NewNotificationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private NewNotificationResolver subscriber;

    public NotificationService() {

    }

    public NotificationService(NewNotificationResolver subscriber) {
        this.subscriber = subscriber;
    }

    public void addSubscriber(NewNotificationResolver subscriber) {
        this.subscriber = subscriber;
    }

    private NotificationDao notificationDao = new NotificationDao();

    public String getNewestNotification() {
        return notificationDao.getNewestNotification().toString();
    }

    public void insertNotificationToDb(String notification) {
        notificationDao.insertNotificationToDb(notification);

        if(subscriber != null) {
            subscriber.update();
        }
    }
}
