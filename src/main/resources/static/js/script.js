var app = angular.module('main', ['ngRoute']);

app.config(function($routeProvider) {
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
            check: function($location, user) {
                if(!user.isUserLoggedIn()) {
                    $location.path('/login');
                }
            }
        },
        templateUrl: '/dashboard.html',
        controller: 'dashboardCtrl'
    }).when('/errorCreateUser', {
        templateUrl: '/errorCreateUser.html',
        controller: 'dashboardCtrl'
    })
        .otherwise({
        template: '404'
    })
});

app.controller('homeCtrl', function($scope, $location) {
    $scope.goToLogin = function() {
        $location.path('/login');
    };
    $scope.register = function() {
        $location.path('/register');
    }
});

app.controller('loginCtrl', ["$scope", "$http", "$location", "user", function($scope, $http, $location, user) {
    $scope.login = function() {
        var username = $scope.username;
        var password = $scope.password;
        $http({
            url: 'http://localhost:8080/api/login',
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            data: 'username='+username+'&password='+password
        }).then(function(response) {
            console.dir(response);
            if(response.data.authorized) {
                user.userLoggedIn();
                user.setName(response.data.user);
                user.setToken(response.data.token);
                $location.path('/dashboard');
            } else {
                alert('invalid login');
            }
        })
    }
}]);

app.service('user', function() {
    var username;
    var loggedin = false;
    var token;

    this.setName = function(name) {
        username = name;
    };
    this.getName = function() {
        return username;
    };

    this.setToken = function(tokenToSet) {
        token = tokenToSet;
    };
    this.getToken = function() {
        return token;
    };

    this.isUserLoggedIn = function() {
        return loggedin;
    };
    this.userLoggedIn = function() {
        loggedin = true;
    };
});

app.controller('dashboardCtrl', function($scope, user) {
    $scope.user = user.getName();
});

app.controller('registerCtrl', ["$scope", "$http", "$location", "user", function($scope, $http, $location, user) {
    $scope.register = function() {
        var username = $scope.reg_username;
        var password = $scope.reg_password;
        $http({
            url: 'http://localhost:8080/api/user',
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            data: 'username='+username+'&password='+password
        }).then(function(response) {
            console.dir(response);
            if(response.data.success) {
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