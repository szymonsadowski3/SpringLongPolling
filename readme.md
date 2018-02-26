# Description

This project is an implementation of the server-push mechanism with the use of long-polling technique - designed for Spring Framework.

## Example usage

In order to use this library, client code should do this steps:

1. Application controller should inherit from MainController

```
@RestController
@RequestMapping("/api")
public class AppController extends MainController {
	...
}
```

2. You need to create custom class (that implements Resolver), which will be serving as "helper" who determines values of DeferredJSON objects (these are "Promise objects" which represent future result of some operation). 

```
/**
 * Resolver that is successfully resolving Promises, when new record has been added to notification table
 */
@Component
public class NewNotificationResolver implements Resolver, Observer {
	...
}
```

3. After receiving "long-polling" request there should be created DeferredJSON object, which will represent future result of this request. In the next step - this DeferredJSON object should be appended to tasks queue (by `supervisor.add(result);`)
```
@RequestMapping(value = "/newNotification", method = RequestMethod.GET)
public @ResponseBody
DeferredJSON deferredResult() {
    DeferredJSON result = new DeferredJSON(resolver);
    supervisor.add(result);
    return result;
}
```

# Running example application

1. First step is to import dependencies

```
    mvn clean install -U
```

2. In next step we can run aplliction by:

```
    mvn exec:java -Dexec.mainClass="pl.edu.agh.kis.Main" --spring.config.location=classpath:/application.properties 
```

wher `classpath:/application.properties` means path to apllication's config file

# Configuration

Configuration is located in `src/main/resources/application.properties`

## Example application screens

![main](https://raw.githubusercontent.com/szymonsadowski3/SpringLongPolling/master/docs/screens/loginPage.png)

----

----

![dashboard](https://raw.githubusercontent.com/szymonsadowski3/SpringLongPolling/master/docs/screens/dashboard.png)

