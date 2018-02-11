# Running:

--spring.config.location=classpath:/application.properties 

## Użyte wzorce projektowe:

1. Data Access Object Pattern
    - AppUserDao
    - NotificationDao
    
2. Observer Pattern
    - NotificationService jest typu Observable (gdy pojawia się nowa notyfikacja, powiadamia obserwatorów)
    - NewNotificationResolver jest typu Observator ("rozwiązuje" zapytanie asynchroniczne po pojawieniu się nowej notyfikacji)
    
3. Command
    - DeferredJSON
    
4. Singleton
    - DbConnection