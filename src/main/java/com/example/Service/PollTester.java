package com.example.Service;

import com.example.Controller.StudentController;
import com.example.poll.DeferredJSON;
import com.example.poll.Resolver;
import com.example.poll.Supervisor;
import org.json.simple.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@RestController
@RequestMapping("/test")
public class PollTester {
    private Supervisor supervisor = new Supervisor();

    @RequestMapping(value = "/newNotification", method = RequestMethod.GET)
    public @ResponseBody
    DeferredJSON deferredResult() {
        DeferredJSON result = new DeferredJSON(new Resolver() {
            @Override
            public Optional<JSONObject> resolve() {
                return Optional.of(new JSONObject());
            }
        });
        supervisor.add(result);
        return result;
    }

    @Scheduled(fixedRate = 2000)
    public void processQueues() {
        supervisor.processQueues();
    }
}
