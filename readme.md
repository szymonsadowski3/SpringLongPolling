# Description

This project is an implementation of the server-push mechanism with the use of long-polling technique - designed for Spring Framework.

## Example usage

Aby użyć stworzonej przez nas biblioteki, kod kliencki musi wykonać następujące kroki:

1. Application controller should inherit from MainController

```
@RestController
@RequestMapping("/api")
public class AppController extends MainController {
	...
}
```

2. In order to use this library there is need to create custom class of type Resolver, which serves as "helper" who finds out value of DeferredJSON objects (these are "Promise objects"). 

```
/**
 * Resolver that is successfully resolving Promises, when new record has been added to notification table
 */
@Component
public class NewNotificationResolver implements Resolver, Observer {
	...
}
```

3. After receiving "long-polling" request, to controller's queque there should be appended new DeferredJSON obejct, which represents future result of this certain request.
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

