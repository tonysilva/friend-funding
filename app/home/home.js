'use strict';

angular.module('hackathon.home', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/home', {
    templateUrl: 'home/home.html',
    controller: 'HomeCtrl'
  });
}])

.controller('HomeCtrl', function($scope) {

	$scope.friends = [];
	for (var i = 0; i < 30; i++) {
      var friend = {}
      $scope.friends.push(friend);
    }

    $scope.wishes = [];
	for (var i = 0; i < 8; i++) {
      var wish = {}
      $scope.wishes.push(wish);
    }

});