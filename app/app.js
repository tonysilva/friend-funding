'use strict';

angular.module('hackathon', [
  'ngRoute',
  'hackathon.home',
  'hackathon.login',
  'hackathon.system',
  'hackathon.version',
  'ngMaterial',
  'ngResource'
])
.service('app', function () {
  this.domain = 'http://192.168.1.180:8080';
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
                    //console.log("token["+window.localStorage.getItem('accessToken')+"], config.headers: ", config.headers);
                    config.headers.authorization = sessionStorage.getItem('token');
                }
                return config || $q.when(config);
            },
            responseError: function(rejection) {
                if (rejection.status == 401) {
                    //console.log("Access denied (error 401), please login again");
                    //$location.nextAfterLogin = $location.path();
                    $location.path('/login');
                }
                return $q.reject(rejection);
            }
        }
    }
)

.run(function($rootScope, $location, System) {
    $rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute) {
      debugger;
      var logged = System.status;
      var appTo = currRoute.$$route.originalPath;
      if(appTo != '/login' && !logged) {
          event.preventDefault();
          $location.path('/login');
      }
    });
});