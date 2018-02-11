package pl.edu.agh.kis.application.observer;

/**
 * This class represents an observable object.
 * An observable object can have one or more observers.
 * An observer may be any object that implements interface Observer.
 * Observable can call notifyObservers method, which causes all of its observers to be notified
 * by a call to their update method.
 */
public interface Observable {
    /**
     * @param o Observer which is going to be registered by Observable
     */
    void addObserver(Observer o);

    /**
     * @param o Observer which is going to be removed from notifying by Observable
     */
    void removeObserver(Observer o);

    /**
     * Causes all of observers to be notified by a call to their update method.
     */
    void notifyObservers();
}
