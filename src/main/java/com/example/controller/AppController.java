package com.example.controller;

import com.example.Service.NotificationService;
import com.example.poll.core.DeferredJSON;
import com.example.poll.core.Resolver;
import com.example.poll.core.Supervisor;
import com.example.poll.implementations.NewNotificationResolver;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/test")
public class AppController {
    private Supervisor supervisor = new Supervisor();
    private NewNotificationResolver resolver = new NewNotificationResolver();

    private NotificationService notificationService = new NotificationService(resolver);

    @RequestMapping(value = "/newNotification", method = RequestMethod.GET)
    public @ResponseBody
    DeferredJSON deferredResult() {
        DeferredJSON result = new DeferredJSON(resolver);
        supervisor.add(result);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void insertStudent(@RequestBody String notificationContent) {
        try {
            notificationService.insertNotificationToDb(notificationContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 2000)
    public void processQueues() {
        supervisor.processQueues();
    }
}