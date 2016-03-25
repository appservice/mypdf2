'use strict';

var app = angular.module('myApp', [ 'ngRoute', 'ngMaterial', 'restangular',/* 'myApp.directives', */'myApp.controllers', 'myApp.services' ]);

app.config([ '$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {

	$routeProvider.when('/orders-table', {
		templateUrl : 'views/ordersTable.html',
		controller : 'OrdersTableCtrl'
	});
	$routeProvider.when('/add-order', {
		templateUrl : 'views/add_order.html',
		controller : 'TableViewsListCtrl'
	});

	$routeProvider.when('/home',{
		templateUrl:'views/home.html'
	//	resolve:'currentUser'
	});

	$routeProvider.when('/pick-up',{
		templateUrl:'views/pickupParts.html',
		controller : 'PickupPartsController'

	});
	$routeProvider.otherwise({
		redirectTo : '/home'

	});

	/* $locationProvider.html5Mode(true); */
} ]);


var pathDomain= window.location.pathname.split( '/' )[1];


app.config(function(RestangularProvider) {
	RestangularProvider.setBaseUrl('/'+pathDomain+'/resources/');
});

app.run(function($rootScope, $location, Restangular, $window) {
	console.log($location.absUrl());
	Restangular.one('/users/logged', "").get().then(function(currentUser) {
		$rootScope.currentUser = currentUser.plain();
		console.log($rootScope.currentUser);


	});

	$rootScope.logout = function() {
		Restangular.one("users/logout").customGET("");
		$window.location.replace("/"+pathDomain);

	};
});

app.filter('sqlfilter',function(){
	return function(input) {
        if (input) {
            return input.replace(/\*/g, '%');    
        }
    } 
});


