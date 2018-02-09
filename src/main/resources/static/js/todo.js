var notificationsUrl = '/api/notifications';

angular.module('todoApp', [])
    .controller('NotificationListController', ['$scope','$http', function ($scope, $http) {
        var notificationList = this;

        $("#spinner").show();
        $http.get(notificationsUrl, {})
            .then(
                function(response) {
                    // success callback
                    var responseData = response.data;

                    notificationList.notifications = response.data;
                    console.dir(notificationList.notifications);
                    $("#spinner").hide();
                },
                function(response) {
                    // failure call back
                    notificationList.notifications = [];
                }
            );


        notificationList.addTodo = function () {
            notificationList.notifications.push({text: notificationList.todoText, done: false});
            notificationList.todoText = '';
        };

        notificationList.remaining = function () {
            var count = 0;
            angular.forEach(notificationList.notifications, function (todo) {
                count += todo.done ? 0 : 1;
            });
            return count;
        };

        notificationList.archive = function () {
            var oldTodos = notificationList.notifications;
            notificationList.notifications = [];
            angular.forEach(oldTodos, function (todo) {
                if (!todo.done) notificationList.notifications.push(todo);
            });
        };
    }]);