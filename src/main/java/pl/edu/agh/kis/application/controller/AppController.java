package pl.edu.agh.kis.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.kis.application.service.AppUserService;
import pl.edu.agh.kis.application.service.NotificationService;
import pl.edu.agh.kis.application.auth.Authorizer;
import pl.edu.agh.kis.application.entity.AppUser;
import pl.edu.agh.kis.application.entity.Notification;
import pl.edu.agh.kis.poll.core.DeferredJSON;
import pl.edu.agh.kis.poll.core.MainController;
import pl.edu.agh.kis.poll.implementations.NewNotificationResolver;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value="notifications_app", description="CRUD interface designed for Notifications application")
public class AppController extends MainController {
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

    @ApiOperation(value = "The newest notification in database (Note: This endpoint is designed for long-polling)", response = DeferredJSON.class)
    @RequestMapping(value = "/newNotification", method = RequestMethod.GET)
    public @ResponseBody
    DeferredJSON deferredResult() {
        DeferredJSON result = new DeferredJSON(resolver);
        supervisor.add(result);
        return result;
    }

    @ApiOperation(value = "List of all notifications fetched from database " +
            "(Note: JSON Web Token must be specified in request)", response = Iterable.class)
    @RequestMapping(value = "/notifications", method = RequestMethod.GET)
    public List<Notification> getNotifications(@RequestParam("token") String token, @RequestParam("user") String user) {
        if (Authorizer.verifyToken(token, user)) {
            List<Notification> result = notificationService.getNotifications();
            return notificationService.getNotifications();
        }

        return null;
    }

    @ApiOperation(value = "Details of user fetched from database by his ID", response = AppUser.class)
    @RequestMapping(value = "/user/{userid}", method = RequestMethod.GET)
    public AppUser getUser(@PathVariable("userid") int userid) {
        return appUserService.readAppUser(userid);
    }

    @ApiOperation(value = "Add new user to database: request body must contain username and password")
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

    @ApiOperation(value = "Add new user to database: request body must contain username and password")
    @RequestMapping(value="/notification", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> insertNotification(@RequestBody Notification notification) {
        try {
            notificationService.insertNotification(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Start session by logging in (specify username & password) and obtain JSON Web Token in response")
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
}