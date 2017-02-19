'use strict';

angular.module('hackathon.login', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/login', {
    templateUrl: 'login/login.html',
    controller: 'LoginCtrl'
  });
}])

.controller('LoginCtrl', function($scope, $http, $location, app, $window) {

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
			profileImg: '',
		},
	}

	$scope.newAccount = function() {
		$scope.notAccount = true;
	}

	$scope.login = function(valid) {
		if (valid) {
			$http.post(app.domain + "/login", $scope.user)
		    .then(function(response) {
		    	var header = response.headers();
		        sessionStorage.setItem("token", header['authorization']); 		        
		        delete $scope.user;
		    	$window.location.reload();
		    });
		    
		}
	}

	$scope.createUser = function(profileImg) {
		$scope.newUser.profile.profileImg = profileImg.base64;
		$http.post(app.domain + "/users", $scope.newUser)
	    .then(function(response) {
	    	$scope.user.username = $scope.newUser.username;
	    	$scope.user.password = $scope.newUser.password;		        
	        delete $scope.newUser;
	        $scope.login(true);
	    }, function(response) {
	    	console.log(response.data.errors)
	    });		    		
	}

});