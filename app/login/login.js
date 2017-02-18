'use strict';

angular.module('hackathon.login', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/login', {
    templateUrl: 'login/login.html',
    controller: 'LoginCtrl'
  });
}])

.controller('LoginCtrl', function($scope, $http) {

	$scope.user = {
		username: '',
		password: ''
	}

	$scope.newUser = {
		username: '',
		password: '',
		profile: {
			name: '',
			email: '',
		},
	}

	$scope.newAccount = function() {
		$scope.notAccount = true;
	}

	$scope.login = function(valid) {
		if (valid) {
			$http.post("http://192.168.1.180:8080/login", $scope.user)
		    .then(function(response) {
		    	var authorization = response.headers();
		        sessionStorage.setItem(authorization['authorization']);
		    });
		    delete $scope.user;
		    $scope.userForm.$setPristine();
		    $scope.userForm.$setUntouched();
		}
	}

	$scope.createUser = function(valid) {
		if (valid) {
			$http.post("http://192.168.1.180:8080/users", $scope.newUser)
		    .then(function(response) {
		    	$scope.user.username = $scope.newUser.username;
		    	$scope.user.password = $scope.newUser.password;
		        $scope.login(true);
		    });
		    delete $scope.newUser;
		    $scope.userForm.$setPristine();
		    $scope.userForm.$setUntouched();
		}		
	}

});