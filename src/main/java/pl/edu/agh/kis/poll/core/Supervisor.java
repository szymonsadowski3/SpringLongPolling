package pl.edu.agh.kis.poll.core;

import org.springframework.stereotype.Component;
import pl.edu.agh.kis.application.command.Command;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This class is used to manage a Queue of commands
 */
@Component
public class Supervisor {
    private final Queue<Command> responseBodyQueue = new ConcurrentLinkedQueue<>();

    /**
     * Execute commands in queue and remove those commands that were able to finish
     */
    public void processQueues() {
        for (Command cmd : responseBodyQueue) {
            if(cmd.execute()) {
                responseBodyQueue.remove(cmd);
            }
        }
    }

    /**
     * @param cmd New command to be added to queue
     */
    public void add(Command cmd) {
        responseBodyQueue.add(cmd);
    }
}
