'use strict';

angular.module('hackathon.home', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/home', {
    templateUrl: 'home/home.html',
    controller: 'HomeCtrl'
  });
}])

.controller('HomeCtrl', function($scope, $http, app, $location, WishService) {

	$scope.friends = [];
  $scope.wishes = [];
  $scope.myWishes = [];

  $http.get(app.domain + "/wishes")
  .then(function(response) {
    $scope.wishes = response.data;
  });

  $http.get(app.domain + "/profiles/friends")
  .then(function(response) {
    $scope.friends = response.data;
  });

  $http.get(app.domain + "/wishes/mine")
  .then(function(response) {
    $scope.myWishes = response.data;
  });

  $scope.openWish = function(wishName) {
    WishService.setName(wishName);
    $location.path("/wish");
  }

  $scope.openContribution = function(wish) {
    ContributionService.setWish(wish);
    $location.path("/contribution");
  }

});