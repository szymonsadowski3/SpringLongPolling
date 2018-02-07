package com.example.poll;

import org.json.simple.JSONObject;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Optional;

public class DeferredJSON extends DeferredResult<JSONObject> {
    private Resolver resolver;

    public DeferredJSON(Resolver resolver) {
        this.resolver = resolver;
    }

    public boolean process() {
        Optional<JSONObject> resolveResult = resolver.resolve();

        if (resolveResult.isPresent()) {
            this.setResult(resolveResult.get());
            return true;
        } else {
            return false;
        }
    }
}
