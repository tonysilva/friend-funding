'use strict';

angular.module('hackathon.profile', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/profile', {
    templateUrl: 'profile/profile.html',
    controller: 'ProfileCtrl'
  });
}])

.controller('ProfileCtrl', function($scope, $http, app, $location, ProfileService, WishService) {

  $scope.profile = {};
	$scope.friends = [];
  $scope.wishes = [];
  $scope.myWishes = [];
  $scope.idProfile = ProfileService.idProfile;

  $scope.openWish = function(wishName) {
    WishService.setName(wishName);
    $location.path("/wish");
  }

  if ($scope.idProfile != null) {
    $http.get(app.domain + "/profiles/" + $scope.idProfile+"/friends")
    .then(function(response) {
      $scope.friends = response.data;
    });

    $http.get(app.domain + "/"+ $scope.idProfile + "/wishes")
    .then(function(response) {
      $scope.myWishes = response.data;
    });

    $http.get(app.domain + "/wishes/" + $scope.idProfile + "/friends")
    .then(function(response) {
      $scope.wishes = response.data;
    });

    $http.get(app.domain + "/profiles/" + $scope.idProfile)
    .then(function(response) { 
        $scope.profile = response.data;
        sessionStorage.setItem("profile", response.data);
    });
  }

})

.service('ProfileService', function () {
  this.idProfile = 0;
  this.setIdProfile = function(idProfile) { 
    this.idProfile = idProfile;
  };
});
