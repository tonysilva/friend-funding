'use strict';

angular.module('hackathon.home', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/home', {
    templateUrl: 'home/home.html',
    controller: 'HomeCtrl'
  });
}])

.controller('HomeCtrl', function($scope, $http, app, $location, WishService, WishContributionService, ProfileService) {

  $scope.profile = {};

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

  $scope.profile = function() {
    $http.get(app.domain + "/profiles/me")
    .then(function(response) { 
        $scope.profile = response.data;
        sessionStorage.setItem("profile", response.data);
    });
  }
  
  $scope.profile();

  $scope.openWishContribution = function(wish) {
    WishContributionService.setWish(wish);
    $location.path("/contribution");
  }

  $scope.openFriend = function(idProfile) {
    ProfileService.setIdProfile(idProfile);
    $location.path("/profile");
  }

});