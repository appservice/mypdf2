var app = angular.module('StarterApp', [ 'ngMaterial', 'restangular' ]);

app.controller('AppCtrl', function($scope, $timeout, $mdSidenav, $log, $mdDialog, $mdToast, Restangular) {
	// $scope.toggleLeft = buildDelayedToggler('left');
	$scope.toggleRight = buildToggler('right');
	$scope.isOpenRight = function() {
		return $mdSidenav('right').isOpen();
	};

	$scope.findOrder = function(orderNumber) {
		Restangular.one('mypdf/resources/orders', orderNumber).get().then(function(data) {
			if (data instanceof Array) {
				$scope.order = data;
			}else{
				$scope.orderList=data;
			}

			console.log(data);
		});
	}

	
	$scope.findOrdrList=function(orderNumber){
		var base=Restangular.all('mypdf/resources/orders');
		base.getList({number:orderNumber}).then(function(data){
			$scope.orderList=data;
		});
	}
	
	function buildToggler(navID) {
		return function() {
			$mdSidenav(navID).toggle().then(function() {
				$log.debug("toggle " + navID + " is done");
			});
		}
	}

	$scope.showAlert = function(ev) {
		// Appending dialog to document.body to cover sidenav in docs app
		// Modal dialogs should fully cover application
		// to prevent interaction outside of dialog
		$mdDialog.show($mdDialog.alert().parent(angular.element(document.querySelector('#popupContainer'))).clickOutsideToClose(true).title(
				'This is an alert title').content('You can specify some description text in here.').ariaLabel('Alert Dialog Demo').ok('Got it!')
				.targetEvent(ev));
	};

	$scope.showConfirm = function(ev) {
		// Appending dialog to document.body to cover sidenav in docs app
		var confirm = $mdDialog.confirm().title('Would you like to delete your debt?').content(
				'All of the banks have agreed to <span class="debt-be-gone">forgive</span> you your debts.').ariaLabel('Lucky day').targetEvent(ev)
				.ok('Zapisz').cancel('Anuluj');
		$mdDialog.show(confirm).then(function() {
			$scope.status = 'You decided to get rid of your debt.';
		}, function() {
			$scope.status = 'You decided to keep your debt.';
		});
	};

	var last = {
		bottom : false,
		top : true,
		left : false,
		right : true
	};
	$scope.showSimpleToast = function() {
		console.log("show simple toast");
		$mdToast.show($mdToast.simple().content('Simple Toast!').position('bottom right')// $scope.getToastPosition())
		.hideDelay(3000));
	};
});
app.controller('RightCtrl', function($scope, $timeout, $mdSidenav, $log/* ,Restangular */) {

	$scope.close = function() {
		$mdSidenav('right').close().then(function() {
			$log.debug("close RIGHT is done");
		});
	};

});