'use strict';

angular.module('hackathon.contribution', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/contribution', {
    templateUrl: 'contribution/contribution.html',
    controller: 'ContributionCtrl'
  });
}])

.controller('ContributionCtrl', function($scope, $http, app, WishContributionService) {
	
	$scope.wish = WishContributionService.wish;
	$scope.contributions = [];

	/*if (WishContributionService.wish) {
		$http.get(app.domain + "/wishes/" + WishContributionService.wish.id)
		.then(function(response) {
		  $scope.wish = response.data;
		}); 	
	}*/

	/*$http.get(app.domain + "/contributions/" + WishContributionService.wish.id)
	.then(function(response) {
	  $scope.contributions = response.data;
	});*/ 
	 
})

.service('WishContributionService', function () {
  this.wish = {};
  this.setWish = function(wish) { 
  	this.wish = wish;
  };
});