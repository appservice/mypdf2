/**
 * 
 */
angular.module('myApp.controllers').controller('OrdersTableCtrl'
		,['$scope', '$timeout', '$mdSidenav', '$log', '$mdDialog', '$mdToast', 'Restangular', 'RestFulResponse', '$rootScope', 
function ($scope, $timeout, $mdSidenav, $log, $mdDialog, $mdToast, Restangular, RestFulResponse, $rootScope) {
	// $scope.toggleLeft = buildDelayedToggler('left');
	$scope.toggleRight = buildToggler('right');
	$scope.orderList = [];
//	$scope.orderDateFrom=null;
	$scope.startPosition=0;
	$scope.orderCriteria={};
	$scope.showButton=false;

/*	$scope.showButton=function(){
		console.log($scope.orderList.length);
		if($scope.orderList.length>0){
			return true;
		}
		
		else
		return false;
	}*/
	
	$scope.isOpenRight = function() {
		return $mdSidenav('right').isOpen();
	};

	// ------------find
	// order--------------------------------------------------------------------------------------------------
	$scope.findOrder = function(orderCriteria) {
		var requestParameters={
				number : orderCriteria.orderNumber,
				supplier : orderCriteria.orderSupplier,
				purchaser : orderCriteria.orderPurchaser,
				factory : orderCriteria.orderFactory,
				
				itemName : orderCriteria.orderItemName,
				orderReference : orderCriteria.orderReference,
				itemIndex : orderCriteria.itemIndex,
				itemDescription:orderCriteria.itemDescription,
				suppliesGroup:orderCriteria.suppliesGroup,
				warehouseReleasePerson:orderCriteria.warehouseReleasePerson,
				mpk:orderCriteria.mpk,	
				startPosition : $scope.startPosition,
				maxResult : 10
			}
	 if(orderCriteria.orderDateFrom!=null){
			 requestParameters.orderDateFrom= orderCriteria.orderDateFrom.getTime();
		 }
		if(orderCriteria.orderDateTo!=null){
			requestParameters.orderDateTo=orderCriteria.orderDateTo.getTime();
		}
		
		if(orderCriteria.warehouseReleaseDateFrom!=null){
			requestParameters.warehouseReleaseDateFrom=orderCriteria.warehouseReleaseDateFrom.getTime();
		}
		if(orderCriteria.warehouseReleaseDateTo!=null){
			requestParameters.warehouseReleaseDateTo=orderCriteria.warehouseReleaseDateTo.getTime();
		}
		
		Restangular.all('/orders').customGET("",requestParameters ).then(function(data) {
			if (data instanceof Array) {
				$scope.orderList = [];
				$scope.orderList = data;
				$scope.showButton=true;
				console.log($scope.order);
			} else {

				// $scope.order = data;
				$scope.orderList = [];
				$scope.orderList.push(data);
			}

			$scope.close();
			console.log($scope.orderList);
		});
	}

	
	$scope.showNextSite=function(){
		$scope.startPosition+=10;
		$scope.findOrder($scope.orderCriteria);
		
	}
	
	$scope.showPreviousSite=function(){
		$scope.startPosition-=10;
		$scope.findOrder($scope.orderCriteria);
		
	}
	
	// ----------------save
	// order--------------------------------------------------------------
	$scope.saveOrder = function(order, numberOnList, ev) {

		RestFulResponse.one('/orders/' + order.id).customPUT(order).then(
				function(response) {
					console.log(response.data.plain());
					$scope.orderList[numberOnList] = response.data.plain();

					$mdDialog.show($mdDialog.alert()
					// .parent(angular.element(document.querySelector('#popupContainer')))
					.clickOutsideToClose(true).title('Uwaga:').textContent('Dane zamówienia ' + order.number + ' zostały ukaktualnione!').ariaLabel(
							'Alert Dialog Demo').ok('OK').targetEvent(ev));

				},function(response) {
						if (response.status == 403) {
							alert("Brak uprawnień do tej operacji!");
						} else {
							console.log(response.headers().error);
							
							$mdDialog.show(
								      $mdDialog.alert()
								        .clickOutsideToClose(true)
								        .title('Błąd. Zamówienia nie zaktualizowane!')
								        .textContent(response.headers().error)
								        .ok('OK')
								        .targetEvent(ev)
								    );
							
					
						}
						
					
				}
				
		);
	}

	// --------------item update-------------------------------------
	$scope.itemUpdate = function(orderid, item) {
		/*
		 * Restangular.one('/orders/'+orderid+"/items/"+item.id).customPUT(item).then(function(response){
		 * console.log(response); });
		 */
		if (item.dispatched == false) {
			item.deliveryDate = new Date();
			item.receivingPerson = $rootScope.currentUser.name;
			// item.
		} else {
			item.deliveryDate = null;
			item.receivingPerson = null;
		}
	}

	$scope.receivingPersonUpdate = function( item) {
		if (item.warehouseReleasePerson != null && item.warehouseReleasePerson != "") {
			item.warehouseReleaseDate = new Date();
			item.realisingPerson = $rootScope.currentUser.name;
		} else {
			item.warehouseReleaseDate = null;
			item.realisingPerson=null;
		}

	}

	$scope.findOrdrList = function(orderNumber) {
		var base = Restangular.all('/orders');
		base.getList({
			number : orderNumber
		}).then(function(data) {
			$scope.orderList = data;
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

	$scope.close = function() {
		$mdSidenav('right').close().then(function() {
			$log.debug("close RIGHT is done");
		});
	};

}]);