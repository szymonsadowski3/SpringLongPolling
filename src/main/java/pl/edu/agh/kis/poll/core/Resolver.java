package pl.edu.agh.kis.poll.core;

import org.json.simple.JSONObject;

import java.util.Optional;

/**
 * Class used to operate on "Promises" objects
 */
public interface Resolver {
    /**
     * @return Result of resolving represented in JSON) if method was able to resolve a Promise
     * Empty Optional if method wasn't able to resolve a Promise,
     */
    Optional<JSONObject> resolve();
}
