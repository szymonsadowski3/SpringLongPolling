package pl.edu.agh.kis.poll.core;

import org.springframework.stereotype.Component;
import pl.edu.agh.kis.application.command.Command;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class Supervisor {
    private final Queue<Command> responseBodyQueue = new ConcurrentLinkedQueue<>();

    public void processQueues() {
        for (Command cmd : responseBodyQueue) {
            if(cmd.execute()) {
                responseBodyQueue.remove(cmd);
            }
        }
    }

    public void add(Command cmd) {
        responseBodyQueue.add(cmd);
    }
}
