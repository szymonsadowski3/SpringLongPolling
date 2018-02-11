package pl.edu.agh.kis.application.service;

import pl.edu.agh.kis.application.dao.NotificationDao;
import pl.edu.agh.kis.application.entity.Notification;
import pl.edu.agh.kis.application.observer.Observable;
import pl.edu.agh.kis.application.observer.Observer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * "Middleware" which operates on Notification Data Access Object in order to perform certain operations
 */
@Service
public class NotificationService implements Observable {
    private List<Observer> observers = new ArrayList<>();
    private NotificationDao notificationDao = new NotificationDao();

    /**
     * @return Newest notification in database
     */
    public Notification getNewestNotification() {
        return notificationDao.getNewestNotification();
    }

    /**
     * @param notification Instance of Notification class, which represents the data which is going to be inserted into database
     */
    public void insertNotification(Notification notification) {
        notificationDao.insertNotification(notification.getContent(),
                notification.getImportance(), notification.getAuthorName(), notification.getTitle());

        notifyObservers();
    }

    /**
     * @return List of all notifications from database
     */
    public List<Notification> getNotifications() {
        return notificationDao.getNotifications();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
