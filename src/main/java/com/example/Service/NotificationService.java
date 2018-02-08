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

    @Autowired
    private NotificationDao notificationDao;

    public String getNewestNotification() throws Exception {
        return notificationDao.getNewestNotification();
    }

    public void insertNotificationToDb(String notification) throws Exception {
        notificationDao.insertNotificationToDb(notification);

        if(subscriber != null) {
            subscriber.update();
        }
    }
}
