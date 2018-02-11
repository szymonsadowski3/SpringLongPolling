package pl.edu.agh.kis.poll.core;

import org.json.simple.JSONObject;
import org.springframework.web.context.request.async.DeferredResult;
import pl.edu.agh.kis.application.command.Command;

import java.util.Optional;

public class DeferredJSON extends DeferredResult<JSONObject> implements Command {
    private Resolver resolver;

    public DeferredJSON(Resolver resolver) {
        this.resolver = resolver;
    }

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
