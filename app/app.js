'use strict';

// Declare app level module which depends on views, and components
angular.module('hackathon', [
  'ngRoute',
  'hackathon.home',
  'hackathon.login',
  'hackathon.version',
  'ngMaterial',
  'ngResource'
]).

config(function ($locationProvider, $routeProvider) {
  $locationProvider.hashPrefix('!');
  $routeProvider.otherwise({redirectTo: '/home'});
})

.config(function ($httpProvider) {
    $httpProvider.interceptors.push('MyAuthRequestInterceptor');
})

.factory('MyAuthRequestInterceptor',
    function ($q, $location) {
        return {
            'request': function (config) {
                if (sessionStorage.getItem('token')) {
                    //console.log("token["+window.localStorage.getItem('accessToken')+"], config.headers: ", config.headers);
                    config.headers.authorization = sessionStorage.getItem('token');
                }
                return config || $q.when(config);
            },
            responseError: function(rejection) {
                //console.log("Found responseError: ", rejection);
                if (rejection.status == 401) {
                    //console.log("Access denied (error 401), please login again");
                    //$location.nextAfterLogin = $location.path();
                    $location.path('/login');
                }
                return $q.reject(rejection);
            }
        }
    }
);