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
	//var prof = sessionStorage.getItem("profile");

	/*if (WishContributionService.wish.profile.id == sessionStorage.getItem("profile").id) {
		$scope.renderContribute = false
	}*/

	$http.get(app.domain + "/wishes/" + WishContributionService.wish.id + "/donated")
	.then(function(response) {
	  $scope.donated = response.data;
	});

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
		  //$scope.showCustomToast();	
		});
	  } 

	  /*$scope.showCustomToast = function() {
        $mdToast.show({
          hideDelay   : 3000,
          position    : 'top right',
          controller  : 'ToastCtrl',
          textContent : 'toast-template.html'
        });
      };

      function ToastCtrl($scope, $mdToast, $mdDialog) {
	      $scope.closeToast = function() {
	        if (isDlgOpen) return;
	        $mdToast
	          .hide()
	          .then(function() {
	            isDlgOpen = false;
	          });
	      };
	  }*/

      //$scope.showCustomToast();

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