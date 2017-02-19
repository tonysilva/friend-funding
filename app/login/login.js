'use strict';

angular.module('hackathon.login', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/login', {
    templateUrl: 'login/login.html',
    controller: 'LoginCtrl'
  });
}])

.controller('LoginCtrl', function($scope, $http, $location, app) {

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
			$http.post(app.domain + "/login", $scope.user)
		    .then(function(response) {
		    	var authorization = response.headers();
		        sessionStorage.setItem("token", authorization['authorization']);
		        delete $scope.user;
		    	$location.path("/home");
		    });
		    
		}
	}

	$scope.createUser = function(valid) {
		if (valid) {
			$http.post(app.domain + "/users", $scope.newUser)
		    .then(function(response) {
		    	$scope.user.username = $scope.newUser.username;
		    	$scope.user.password = $scope.newUser.password;		        
		        delete $scope.newUser;
		        $scope.login(true);
		    });		    
		}		
	}

});