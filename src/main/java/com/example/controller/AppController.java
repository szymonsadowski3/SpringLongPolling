package com.example.controller;

import com.example.Service.AppUserService;
import com.example.Service.GroupService;
import com.example.Service.NotificationService;
import com.example.entity.AppUser;
import com.example.entity.Notification;
import com.example.poll.core.DeferredJSON;
import com.example.poll.core.Supervisor;
import com.example.poll.implementations.NewNotificationResolver;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
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

    @RequestMapping(value = "/notifications", method = RequestMethod.GET)
    public List<Notification> getNotifications() {
        return notificationService.getNotifications();
    }

    @RequestMapping(value = "/user/{userid}", method = RequestMethod.GET)
    public AppUser getUser(@PathVariable("userid") int userid) {
        return appUserService.readAppUser(userid);
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