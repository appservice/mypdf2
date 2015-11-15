'use strict';

angular.module('mypdf2',['ngRoute','ngResource'])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/landing.html',controller:'LandingPageController'})
      .when('/Items',{templateUrl:'views/Item/search.html',controller:'SearchItemController'})
      .when('/Items/new',{templateUrl:'views/Item/detail.html',controller:'NewItemController'})
      .when('/Items/edit/:ItemId',{templateUrl:'views/Item/detail.html',controller:'EditItemController'})
      .when('/Orders',{templateUrl:'views/Order/search.html',controller:'SearchOrderController'})
      .when('/Orders/new',{templateUrl:'views/Order/detail.html',controller:'NewOrderController'})
      .when('/Orders/edit/:OrderId',{templateUrl:'views/Order/detail.html',controller:'EditOrderController'})
      .otherwise({
        redirectTo: '/'
      });
  }])
  .controller('LandingPageController', function LandingPageController() {
  })
  .controller('NavController', function NavController($scope, $location) {
    $scope.matchesRoute = function(route) {
        var path = $location.path();
        return (path === ("/" + route) || path.indexOf("/" + route + "/") == 0);
    };
  });
