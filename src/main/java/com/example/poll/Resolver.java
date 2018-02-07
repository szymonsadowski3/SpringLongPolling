package com.example.poll;

import org.json.simple.JSONObject;

import java.util.Optional;

public interface Resolver {
    Optional<JSONObject> resolve();
}
