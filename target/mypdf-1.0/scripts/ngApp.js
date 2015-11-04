/**
 * main part of js view
 */
 var ngApp=angular.module('ngApp', [ 'ngApp.services',  'ngApp.controllers','ngApp.directives','ngRoute','ngTouch','ui.grid', 'ui.grid.resizeColumns', 'ui.grid.edit', 'ui.grid.moveColumns', 'ui.grid.cellNav']);

 ngApp.config(function ($httpProvider) {
 	$httpProvider.defaults.headers.post['Content-Type'] = undefined;
 });



 ngApp.config(['$routeProvider','$locationProvider', function ($routeProvider,$locationProvider) {
 	$routeProvider.when('/main-table2', {templateUrl: 'views/main-table2.html', controller: 'TableViewsListCtrl'});
 	$routeProvider.when('/main-table', {templateUrl: 'views/main-table.html', controller: 'TableViewsListCtrl'});
 	/*   $routeProvider.when('/user-creation', {templateUrl: 'partials/user-creation.html', controller: 'UserCreationCtrl'});*/
 	$routeProvider.otherwise({redirectTo: '/main-table2'});

 	/*$locationProvider.html5Mode(true);*/
 }]);
