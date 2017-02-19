'use strict';

angular.module('hackathon.contribution', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/contribution', {
    templateUrl: 'contribution/contribution.html',
    controller: 'ContributionCtrl'
  });
}])

.controller('ContributionCtrl', [function() {

	

}]);