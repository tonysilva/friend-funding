'use strict';

angular.module('hackathon.contribution', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/contribution', {
    templateUrl: 'contribution/contribution.html',
    controller: 'ContributionCtrl'
  });
}])

.controller('ContributionCtrl', function($scope, $http, app, $mdDialog, $mdToast, WishContributionService) {
	
	$scope.wish = WishContributionService.wish;
	$scope.contributions = [];
	$scope.renderContribute = true;

	$scope.getDonated = function() {
		$http.get(app.domain + "/wishes/" + WishContributionService.wish.id + "/donated")
		.then(function(response) {
		  $scope.donated = response.data;
		});	
	}

	$scope.getDonated();

	$scope.getContributions = function() {
		$http.get(app.domain + "/contributions/" + WishContributionService.wish.id)
		.then(function(response) {
		  $scope.contributions = response.data;
		});
	}

	$scope.getContributions();

	$scope.showAdvanced = function(ev) {
	    $mdDialog.show({
	      controller: DialogController,
	      templateUrl: 'contribution/dialog.tmpl.html',
	      parent: angular.element(document.body),
	      targetEvent: ev,
	      clickOutsideToClose: true
	    })
	    .then(function(answer) {
	      $scope.contribution(answer);
	    }, function() {
	      
	    });
	  };

	  $scope.contribution = function(value) {
	  	var contribution = {};
	  	contribution.value = value;
	  	$http.post(app.domain + "/contributions?wishid=" + WishContributionService.wish.id, contribution)
		.then(function(response) {
		  $scope.contributions.push(response.data);
		  $scope.getDonated();	
		});
	  } 

	  function DialogController($scope, $mdDialog) {
	    $scope.hide = function() {
	      $mdDialog.hide();
	    };

	    $scope.cancel = function() {
	      $mdDialog.cancel();
	    };

	    $scope.answer = function(answer) {
	      $mdDialog.hide(answer);
	    };
	  }
	 
})

.service('WishContributionService', function () {
  this.wish = {};
  this.setWish = function(wish) { 
  	this.wish = wish;
  };
});