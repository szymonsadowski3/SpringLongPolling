package com.example.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.Service.AppUserService;
import com.example.Service.GroupService;
import com.example.Service.NotificationService;
import com.example.auth.Authorizer;
import com.example.entity.AppUser;
import com.example.entity.Notification;
import com.example.poll.core.DeferredJSON;
import com.example.poll.core.Supervisor;
import com.example.poll.implementations.NewNotificationResolver;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
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

//    @RequestMapping(value = "/notifications", method = RequestMethod.GET)
//    public List<Notification> getNotifications() {
//        return notificationService.getNotifications();
//    }

    @RequestMapping(value = "/notifications", method = RequestMethod.GET)
    public List<Notification> getNotifications(@RequestParam("token") String token, @RequestParam("user") String user) {
        if (Authorizer.verifyToken(token, user)) {
            return notificationService.getNotifications();
        }

        return null;
    }

    @RequestMapping(value = "/user/{userid}", method = RequestMethod.GET)
    public AppUser getUser(@PathVariable("userid") int userid) {
        return appUserService.readAppUser(userid);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public JSONObject createUser(@RequestBody MultiValueMap<String,String> formData) {
        JSONObject response = new JSONObject();

        String username = formData.getFirst("username");
        String password = formData.getFirst("password");

        boolean wasSuccess = appUserService.createAppUser(username, password);

        String jwToken = Authorizer.generateToken(username);

        response.put("success", wasSuccess);
        response.put("token", jwToken);
        response.put("user", username);

        return response;
    }

    @RequestMapping(value="/notification", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertNotification(@RequestBody Notification notification) {
        try {
            notificationService.insertNotification(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value="/login",method=RequestMethod.POST)
    public JSONObject createRole(@RequestBody MultiValueMap<String,String> formData){
        JSONObject jsonObj = new JSONObject();

        String username = formData.getFirst("username");
        String password = formData.getFirst("password");

        if(appUserService.verifyAppUser(username, password)) {
            jsonObj.put("authorized", true);
            String jwToken = Authorizer.generateToken(username);
            jsonObj.put("token", jwToken);
        } else {
            jsonObj.put("authorized", false);
        }

        jsonObj.put("user", username);

        return jsonObj;
    }

    @Scheduled(fixedRate = 500)
    public void processQueues() {
        supervisor.processQueues();
    }
}