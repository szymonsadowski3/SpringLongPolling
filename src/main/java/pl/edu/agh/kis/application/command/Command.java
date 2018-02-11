package pl.edu.agh.kis.application.command;


/**
 * Command interface - It encapsulates an action as an object
 */
public interface Command {

    /**
     * @return true if operation succeeded, false otherwise
     */
    boolean execute();
}
