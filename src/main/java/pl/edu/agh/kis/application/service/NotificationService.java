package pl.edu.agh.kis.application.service;

import pl.edu.agh.kis.application.dao.NotificationDao;
import pl.edu.agh.kis.application.entity.Notification;
import pl.edu.agh.kis.application.observer.Observable;
import pl.edu.agh.kis.application.observer.Observer;
import pl.edu.agh.kis.poll.implementations.NewNotificationResolver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService implements Observable {
    private List<Observer> observers = new ArrayList<>();
    private NotificationDao notificationDao = new NotificationDao();

    public Notification getNewestNotification() {
        return notificationDao.getNewestNotification();
    }

    public void insertNotification(Notification notification) {
        notificationDao.insertNotification(notification.getContent(),
                notification.getImportance(), notification.getAuthorName(), notification.getTitle());

        notifyObservers();
    }

    public List<Notification> getNotifications() {
        return notificationDao.getNotificationsWithAuthorNames();
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
