'use strict';

angular.module('hackathon.system', [])

.factory('System', function ($location, $window) {
        return {
            'status': sessionStorage.getItem('token') ? true : false,
            'logout': function () {
                sessionStorage.removeItem('token');
                $window.location.reload();
        	},
    	}
	}
)

.controller('SystemCtrl', function($scope, $http, $location, app, System) {

	$scope.logout = function() {
		System.logout();		
	}

	$scope.status = System.status;

});