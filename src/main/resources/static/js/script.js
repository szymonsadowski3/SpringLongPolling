var app = angular.module('main', ['ngRoute']);

var notificationsUrl = '/api/notifications';
var postNotificationUrl = '/api/notification';
var newNotificationsLongPollUrl = '/api/newNotification';

var addedDates = [];

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

var currentRequest;

function pausecomp(millis)
{
    var date = new Date();
    var curDate = null;
    do { curDate = new Date(); }
    while(curDate-date < millis);
}

function format(str, args) {
    var formatted = str;
    for (var i = 0; i < args.length; i++) {
        var regexp = new RegExp('\\{'+i+'\\}', 'gi');
        formatted = formatted.replace(regexp, args[i]);
    }
    return formatted;
}

app.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: '/login.html',
        controller: 'loginCtrl'
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

function renderCard(notification) {
    var animationClass = (notification.isNew) ? 'animated shake' : '';

    return format('<div class="example-1 card">' +
        '        <div class="wrapper {0} {1}">' +
        '        <div ng-if="notification.isNew">' +
        '        <div class="newNotificationIndicator">!</div>' +
        '        </div>' +
        '        <div class="date">\n' +
        '        <span class="day">{2}</span>' +
        '    <span class="month">{3}</span>' +
        '    <span class="year">{4}</span>' +
        '    <span class="time">{5}</span>' +
        '    </div>' +
        '    <div class="data">' +
        '        <div class="content">' +
        '        <span class="author">{6}</span>' +
        '    <h1 class="title"><a href="#">{7}</a></h1>' +
        '    <p class="text">{8}</p>' +
        '    <label for="show-menu" class="menu-button"><span></span></label>' +
        '    </div>' +
        '    <input type="checkbox" id="show-menu" />' +
        '        </div>' +
        '        </div>' +
        '        </div>', [notification.backgroundClass, animationClass, notification.day, notification.year, notification.month, notification.timeValue, notification.authorName, notification.title, notification.content]);
}

app.controller('loginCtrl', ["$scope", "$http", "$location", "user", function ($scope, $http, $location, user) {
    $scope.goToRegister = function () {
        $location.path('/register');
    };

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

app.controller('dashboardCtrl', ['$scope', '$http', '$interval', 'user', function ($scope, $http, $interval, user) {
    $scope.user = user.getName();

    var notificationList = this;

    $("#spinner").show();

    function updateNotificationFields(obj) {
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
                notificationList.notifications = response.data;
                notificationList.notifications.forEach(function (obj) {
                    updateNotificationFields(obj);
                });

                $("#spinner").hide();
            },
            function (response) {
                // failure call back
                notificationList.notifications = [];
            }
        );

    function pollNewNotification() {
        console.log("Polling new notification");
        currentRequest = $.get(newNotificationsLongPollUrl, function (response) {
            // success callback
            updateNotificationFields(response);
            response.isNew = true;

            if (addedDates.indexOf(response.createdOn)  == -1) {
                $('#notificationsWrapper').prepend(renderCard(response));
                addedDates.push(response.createdOn);
            }

            setTimeout(pollNewNotification, 250);
        });
    }

    pollNewNotification();

    notificationList.addNotification = function () {
        currentRequest.abort();
        $http.post(postNotificationUrl, JSON.stringify({
            title: notificationList.newNotification.title,
            content: notificationList.newNotification.content,
            importance: notificationList.newNotification.importance,
            authorName: user.getName()
        })) // IMPORTANT: Timeout value
            .then(
                function (response) {
                    // success callback
                    currentRequest.abort();
                    setTimeout(pollNewNotification, 1000);
                },
                function (response) {
                    // failure call back
                    currentRequest.abort();
                    setTimeout(pollNewNotification, 1000);
                }
            );
    };
}]);