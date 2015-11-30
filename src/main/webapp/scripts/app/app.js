var app = angular.module('myApp', [ 'ngRoute','ngMaterial', 'restangular',/*'myApp.directives',*/ 'myApp.controllers',
                            		'myApp.services']);


app.config(['$routeProvider','$locationProvider', function ($routeProvider,$locationProvider) {
 	$routeProvider.when('/main-table2', {templateUrl: 'views/main-table2.html', controller: 'TableViewsListCtrl'});
 	$routeProvider.when('/main-table', {templateUrl: 'views/main-table.html', controller: 'TableViewsListCtrl'});
 	$routeProvider.when('/orders-table', {templateUrl: 'views/ordersTable.html', controller: 'OrdersTableCtrl'});
 	$routeProvider.when('/add-order', {templateUrl: 'templates/header.html', controller: 'TableViewsListCtrl'});
// 	$routeProvider.when('/', {templateUrl: 'index.html', controller: 'OrdersTableCtrl'});
 	$routeProvider.otherwise({redirectTo: '/orders-table'});

 	/*$locationProvider.html5Mode(true);*/
 }]);

app.config(function(RestangularProvider){
	RestangularProvider.setBaseUrl('/mypdf2/resources/');
});

app.run(function($rootScope,$location,Restangular,$window){
	
	console.log($location.absUrl());
	
	Restangular.one('/users/logged', "").get().then(function(currentUser) {
		
		$rootScope.currentUser = currentUser.plain();
		console.log($rootScope.currentUser);
		

	$rootScope.logout=function(){
		
		Restangular.one("users/logout").customGET("");
		//$location.path("/main-table");
	//	$location.replace("/mypdf");
		$window.location.replace("/mypdf2");
		
	}		
		
			

		
		});
	
});

