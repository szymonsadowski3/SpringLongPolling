package pl.edu.agh.kis.poll.core;

import org.json.simple.JSONObject;
import org.springframework.web.context.request.async.DeferredResult;
import pl.edu.agh.kis.application.command.Command;

import java.util.Optional;

/**
 * This class represent a kind of "Promise" used for asynchronous requests.
 * This promise will resolve itself when it will be able to finish its "execute" method.
 * Objects of this class are also "Commands", because they encapsulate action performed by execute() method
 */
public class DeferredJSON extends DeferredResult<JSONObject> implements Command {
    private Resolver resolver;

    public DeferredJSON(Resolver resolver) {
        this.resolver = resolver;
    }

    /**
     * @return true if the object was able to execute its task, false otherwise
     */
    public boolean execute() {
        Optional<JSONObject> resolveResult = resolver.resolve();

        if (resolveResult.isPresent()) {
            this.setResult(resolveResult.get());
            return true;
        } else {
            return false;
        }
    }
}
