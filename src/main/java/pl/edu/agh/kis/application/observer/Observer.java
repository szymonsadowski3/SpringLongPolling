package pl.edu.agh.kis.application.observer;

/**
 * A class can implement the Observer interface when it wants to be informed of changes in observable objects.
 */
public interface Observer {
    /**
     * This method is called by observed object (Observable) when it wants to notify their observers.
     */
    void update();
}
