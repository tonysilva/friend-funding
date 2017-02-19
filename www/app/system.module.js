'use strict';

angular.module('hackathon.system', [])

.factory('System', function ($location, $window) {
        return {
            'status': sessionStorage.getItem('token') ? true : false,
            'logout': function () {
                sessionStorage.removeItem('token');
                $window.location.reload();
        	},
    	}
	}
)

.controller('SystemCtrl', function($scope, $http, $location, app, System, ProfileService, $mdDialog) {

	$scope.logout = function() {
		System.logout();		
	}

    $scope.back = function() {
        $location.path('/home');
    }

	$scope.status = System.status;
    
    $scope.showAdvanced = function(ev) {
        $mdDialog.show({
          controller: DialogController,
          templateUrl: 'dialog.tmpl.html',
          parent: angular.element(document.body),
          targetEvent: ev,
          clickOutsideToClose: true
        })
        .then(function(answer) {
          $location.path("/profile");
        }, function() {
          
        });
      };

      function DialogController($scope, $mdDialog, $location, $http) {

        $http.get(app.domain + "/profiles/all")
            .then(function(response) {
                $scope.profiles = response.data;
            });

       $scope.sendInvite = function(idProfile) {
            /*$http.post(app.domain + "/invitations?profileToId=" + idProfile)
            .then(function(response) {                
            });*/
            $scope.openFriend(idProfile);
        }

        $scope.openFriend = function(idProfile) {
            ProfileService.setIdProfile(idProfile);            
            $mdDialog.hide();
        }

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

});