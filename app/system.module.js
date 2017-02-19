'use strict';

angular.module('hackathon.system', [])

.factory('System', function ($location, $route, $templateCache) {
        return {
            'status': sessionStorage.getItem('token') ? true : false,
            'logout': function () {
                sessionStorage.removeItem('token');
                var currentPageTemplate = $route.current.templateUrl;
				$templateCache.remove(currentPageTemplate);
				$route.reload();
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