package com.example.controller;

import com.example.Service.AppUserService;
import com.example.Service.GroupService;
import com.example.Service.NotificationService;
import com.example.entity.Notification;
import com.example.poll.core.DeferredJSON;
import com.example.poll.core.Supervisor;
import com.example.poll.implementations.NewNotificationResolver;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class AppController {
    private Supervisor supervisor = new Supervisor();
    private NewNotificationResolver resolver = new NewNotificationResolver();

    private NotificationService notificationService = new NotificationService(resolver);
    private GroupService groupService = new GroupService();
    private AppUserService appUserService = new AppUserService();

    @RequestMapping(value = "/newNotification", method = RequestMethod.GET)
    public @ResponseBody
    DeferredJSON deferredResult() {
        DeferredJSON result = new DeferredJSON(resolver);
        supervisor.add(result);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertNotification(@RequestBody Notification notification) {
        try {
            notificationService.insertNotification(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 2000)
    public void processQueues() {
        supervisor.processQueues();
    }
}