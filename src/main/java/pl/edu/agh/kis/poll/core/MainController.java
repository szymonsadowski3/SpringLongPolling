package pl.edu.agh.kis.poll.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * This class is the basis for the controller that is designed to be the main controller of the application.
 * It contains a queue of "long-polling" tasks and tries to resolve them periodically
 */
@Component
public class MainController {
    @Autowired
    protected Supervisor supervisor;

    /**
     * Periodically called method which tries to resolve tasks added to the queue
     */
    @Scheduled(fixedRate = 500)
    public void processQueues() {
        supervisor.processQueues();
    }
}
