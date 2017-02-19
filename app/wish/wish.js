'use strict';

angular.module('hackathon.wish', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/wish', {
    templateUrl: 'wish/wish.html',
    controller: 'WishCtrl'
  });
}])

.controller('WishCtrl', function($scope, $http, $location, $mdDialog, app, WishService) {

	$scope.wish = {
		name: '',
		description: '',
		url: '',
		totalValue: 0,
		dueDate: new Date(),
		creationDate: new Date()
	}

	$scope.wish.name = WishService.name;

	$scope.newWish = function(valid) {
		if (valid) {
			$http.post(app.domain + "/wishes", $scope.wish)
		    .then(function(response) {
		    	$mdDialog.show(
			      $mdDialog.alert()
			        .parent(angular.element(document.querySelector('#popupContainer')))
			        .clickOutsideToClose(true)
			        .title('Sucesso')
			        .textContent('Que massa, agora seus amigos poderam ajudar na realização do seu desejo!')
			        .ariaLabel('Sucesso')
			        .ok('FECHAR')
			    );
		    	$location.path("/home");
		    });
		}
	}

})

.service('WishService', function () {
  this.name = '';
  this.setName = function(name) { 
  	this.name = name;
  };
});

