# Opis

Projekt ten jest implementacją mechanizmu server-push z wykorzystaniem techniki long-polling, przeznaczoną dla Frameworka Spring. Technika ta przydaje się w sytuacji, gdy aplikacja kliencka chce pozyskać nowe dane od serwera - wówczas nie trzeba powtarzać w kółko zapytań otrzymując puste odpowiedzi, lecz serwer może utrzymywać zapytanie otwarte aż do pojawienia się nowych danych i dopiero wtedy odesłać odpowiedź. 

Zalety takiego podejścia to:
- Zmniejszenie liczby niepotrzebnych zapytań do serwera
- Wysoka responsywność aplikacji klienckiej (aplikacja kliencka otrzymuje dane natychmiast po ich pojawieniu się)
- Bardzo mała ilość zmian wymagana w aplikacji klienckiej (przy przejściu z "standardowego" pollingu do long-pollingu)

Podejście to przejawia wady takie jak:
- Zwiększenie obciążenia serwera - każde otwarte połączenie będzie zwykle absorbowało jeden wątek serwera (zwykle większość połączeń będzie w bezczynnym stanie oczekiwania)

## Opis implementacji

Główny kontroler aplikacji posiada kolejkę obiektów typu "Promise" - reprezentują one początkowo nieznany rezultat jakiegoś działania, który zostanie ustalony w przyszłości. Wraz z każdym zapytaniem typu "long-polling" do owej kolejki dodawany jest nowy obiekt typu "Promise" reprezentujący to konkretne zapytanie. Zapytanie pozostaje otwarte aż do czasu kiedy wartość zostanie ustalona w tymże obiekcie - kiedy to nastąpi, ustalona wartość zostanie wysłana w ciele odpowiedzi.

Próba ustalenia rezultatu działania w obiektach typu "Promise" następuje cyklicznie poprzez wykonanie metody execute() na każdym z obiektów kolejki. Jeżeli ustalenie rezultatu dla obiektu nastąpiło, wówczas obiekt jest usuwany z kolejki.

## Opis aplikacji

Aby zilustrować działanie long-pollingu postanowiliśmy stworzyć aplikację do wysyłania powiadomień. Po otwarciu aplikacji, wszystkie powiadomienia są ładowane z bazy danych. Następnie, aplikacja wysyła zapytanie typu "long-polling", aby pozyskać nowe powiadomienia. Jeżeli nowe powiadomienie pojawi się, wówczas jego szczegóły zostaną zwrócone w odpowiedzi i aplikacja doda nową notyfikację do widoku aplikacji, po czym otworzy nowe zapytanie typu "long-polling", aby pozyskać kolejne powiadomienia.

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

5. Promise