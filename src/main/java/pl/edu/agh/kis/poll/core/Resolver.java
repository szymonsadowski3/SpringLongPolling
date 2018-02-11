package pl.edu.agh.kis.poll.core;

import org.json.simple.JSONObject;

import java.util.Optional;

public interface Resolver {
    Optional<JSONObject> resolve();
}
