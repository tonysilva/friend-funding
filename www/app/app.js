'use strict';

angular.module('hackathon', [
  'ngRoute',
  'hackathon.home',
  'hackathon.login',
  'hackathon.system',
  'hackathon.wish',
  'hackathon.contribution',
  'hackathon.profile',
  'hackathon.version',
  'ngMaterial',
  'ngResource',
  'naif.base64'
])
.service('app', function () {
  this.domain = 'http://192.168.1.180:8080';
})
.run(function($rootScope, $location, System) {
  $rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute) {
    var logged = sessionStorage.getItem('token') ? true : false;
    var appTo = currRoute.$$route.originalPath;  
    if (appTo != '/login' && !logged) {
      event.preventDefault();
      $location.path('/login');
    } 
    if (appTo == '/login' && logged) {
      $location.path('/home');
    }
  });
})
.config(function ($locationProvider, $routeProvider) {
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
                    config.headers.authorization = sessionStorage.getItem('token');
                }
                return config || $q.when(config);
            },
            responseError: function(rejection) {
                if (rejection.status == 401) {
                    $location.path('/login');
                }
                return $q.reject(rejection);
            }
        }
    }
);