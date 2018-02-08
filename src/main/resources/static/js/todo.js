var notificationsUrl = '/api/notifications';

angular.module('todoApp', [])
    .controller('TodoListController', ['$scope','$http', function ($scope, $http) {
        var todoList = this;

        $http.get(notificationsUrl, {})
            .then(
                function(response) {
                    // success callback
                    todoList.todos = response;
                },
                function(response) {
                    // failure call back
                    todoList.todos = [];
                }
            );

        // todoList.todos = [
        //     {text: 'learn AngularJS', done: true},
        //     {text: 'build an AngularJS app', done: false}];

        todoList.addTodo = function () {
            todoList.todos.push({text: todoList.todoText, done: false});
            todoList.todoText = '';
        };

        todoList.remaining = function () {
            var count = 0;
            angular.forEach(todoList.todos, function (todo) {
                count += todo.done ? 0 : 1;
            });
            return count;
        };

        todoList.archive = function () {
            var oldTodos = todoList.todos;
            todoList.todos = [];
            angular.forEach(oldTodos, function (todo) {
                if (!todo.done) todoList.todos.push(todo);
            });
        };
    }]);