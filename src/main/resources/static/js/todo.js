var notificationsUrl = '/api/notifications';

var monthMapping = {
    "01": "January",
    "02": "February",
    "03": "March",
    "04": "April",
    "05": "May",
    "06": "June",
    "07": "July",
    "08": "August",
    "09": "September",
    "10": "October",
    "11": "November",
    "12": "December"
};

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
                    notificationList.notifications.forEach(function(obj) {
                        var dateString = obj.createdOn.split(' ')[0];
                        var dateSplitted = dateString.split('-');
                        obj.day = dateSplitted[0];
                        obj.month = monthMapping[dateSplitted[1]];
                        obj.year = dateSplitted[2];
                    });
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