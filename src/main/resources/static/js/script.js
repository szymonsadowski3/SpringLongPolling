var app = angular.module('main', ['ngRoute']);

var notificationsUrl = '/api/notifications';
var newNotificationsLongPollUrl = '/api/newNotification';

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

var backgroundClassMapping = {
    1: "greenBg",
    2: "yellowBg",
    3: "redBg"
};

app.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: '/home.html',
        controller: 'homeCtrl'
    }).when('/login', {
        templateUrl: '/login.html',
        controller: 'loginCtrl'
    }).when('/register', {
        templateUrl: '/register.html',
        controller: 'registerCtrl'
    }).when('/dashboard', {
        resolve: {
            check: function ($location, user) {
                if (!user.isUserLoggedIn()) {
                    $location.path('/login');
                }
            }
        },
        templateUrl: '/dashboard.html',
        controller: 'dashboardCtrl'
    }).when('/errorCreateUser', {
        templateUrl: '/errorCreateUser.html',
        controller: 'dashboardCtrl'
    }).when('/invalidLogin', {
        templateUrl: '/invalidLogin.html'
    })
        .otherwise({
            template: '404'
        })
});

app.controller('homeCtrl', function ($scope, $location) {
    $scope.goToLogin = function () {
        $location.path('/login');
    };
    $scope.register = function () {
        $location.path('/register');
    }
});

app.controller('loginCtrl', ["$scope", "$http", "$location", "user", function ($scope, $http, $location, user) {
    $scope.login = function () {
        var username = $scope.username;
        var password = $scope.password;
        $http({
            url: 'http://localhost:8080/api/login',
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            data: 'username=' + username + '&password=' + password
        }).then(function (response) {
            console.dir(response);
            if (response.data.authorized) {
                user.userLoggedIn();
                user.setName(response.data.user);
                user.setToken(response.data.token);
                $location.path('/dashboard');
            } else {
                $location.path('/invalidLogin');
            }
        })
    }
}]);

app.service('user', function () {
    var username;
    var loggedin = false;
    var token;

    this.setName = function (name) {
        username = name;
    };
    this.getName = function () {
        return username;
    };

    this.setToken = function (tokenToSet) {
        token = tokenToSet;
    };
    this.getToken = function () {
        return token;
    };

    this.isUserLoggedIn = function () {
        return loggedin;
    };
    this.userLoggedIn = function () {
        loggedin = true;
    };
});

app.controller('dashboardCtrl', function ($scope, user) {
    $scope.user = user.getName();
});

app.controller('registerCtrl', ["$scope", "$http", "$location", "user", function ($scope, $http, $location, user) {
    $scope.register = function () {
        var username = $scope.reg_username;
        var password = $scope.reg_password;
        $http({
            url: 'http://localhost:8080/api/user',
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            data: 'username=' + username + '&password=' + password
        }).then(function (response) {
            console.dir(response);
            if (response.data.success) {
                user.userLoggedIn();
                user.setName(response.data.user);
                user.setToken(response.data.token);
                $location.path('/dashboard');
            } else {
                $location.path('/errorCreateUser');
            }
        })
    }
}]);

app.controller('dashboardCtrl', ['$scope', '$http', 'user', function ($scope, $http, user) {
    var notificationList = this;

    $("#spinner").show();

    function updateNotificationFields(obj) {
        console.log("updateNotificationFields");
        console.dir(obj);
        var createdOnSplitted = obj.createdOn.split(' ');
        var dateString = createdOnSplitted[0];
        var timeString = createdOnSplitted[1];
        var dateSplitted = dateString.split('-');
        obj.day = dateSplitted[0];
        obj.month = monthMapping[dateSplitted[1]];
        obj.year = dateSplitted[2];
        obj.timeValue = timeString;
        obj.backgroundClass = backgroundClassMapping[obj.importance];
    }

    $http.get(notificationsUrl, {params: { token: user.getToken(), user: user.getName()}})
        .then(
            function (response) {
                // success callback
                // var responseData = response.data;

                notificationList.notifications = response.data;
                notificationList.notifications.forEach(function (obj) {
                    updateNotificationFields(obj);
                });

                // notificationList.notifications.

                console.dir(notificationList.notifications);
                $("#spinner").hide();
            },
            function (response) {
                // failure call back
                notificationList.notifications = [];
            }
        );

    $http.get(newNotificationsLongPollUrl, {timeout: 600000}) // IMPORTANT: Timeout value
        .then(
            function (response) {
                // success callback
                var newData = response.data;
                updateNotificationFields(newData);
                console.dir(newData);
                newData.isNew = true;
                notificationList.notifications.unshift(newData);
                // $("#notificationsWrapper").prepend("<h1>New notification!</h1>");
            },
            function (response) {
                // failure call back
                // alert('XD');
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