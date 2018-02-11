package pl.edu.agh.kis.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.kis.application.service.AppUserService;
import pl.edu.agh.kis.application.service.NotificationService;
import pl.edu.agh.kis.application.auth.Authorizer;
import pl.edu.agh.kis.application.entity.AppUser;
import pl.edu.agh.kis.application.entity.Notification;
import pl.edu.agh.kis.poll.core.DeferredJSON;
import pl.edu.agh.kis.poll.core.Supervisor;
import pl.edu.agh.kis.poll.implementations.NewNotificationResolver;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AppController {
    @Autowired
    private Supervisor supervisor;

    @Autowired
    private NewNotificationResolver resolver;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AppUserService appUserService;

    @PostConstruct
    public void init(){
        notificationService.addObserver(resolver);
    }

    @RequestMapping(value = "/newNotification", method = RequestMethod.GET)
    public @ResponseBody
    DeferredJSON deferredResult() {
        DeferredJSON result = new DeferredJSON(resolver);
        supervisor.add(result);
        return result;
    }

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
    public ResponseEntity<Void> insertNotification(@RequestBody Notification notification) {
        try {
            notificationService.insertNotification(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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