/**
 * 
 */
angular.module('myApp.controllers').controller('PickupPartsController'
		,['$scope', '$timeout', '$filter','$mdSidenav', '$log', '$mdDialog', '$mdToast', 'Restangular', 'RestFulResponse', '$rootScope',
function ($scope, $timeout,$filter, $mdSidenav, $log, $mdDialog, $mdToast, Restangular, RestFulResponse, $rootScope) {
	// $scope.toggleLeft = buildDelayedToggler('left');
	$scope.toggleRight = buildToggler('right');
	$scope.orderList = [];
//	$scope.orderDateFrom=null;
	$scope.startPosition=0;
	$scope.orderCriteria={};
	$scope.showButton=false;
	$scope.reverse=true;


	var orderBy = $filter('orderBy');

	 $scope.orderDirections = function(predicate,orderId) {
		    $scope.predicate = predicate+orderId;
		    $scope.reverse = ($scope.predicate === (predicate+orderId)) ? !$scope.reverse : false;
		    $scope.orderList[orderId].items= orderBy($scope.orderList[orderId].items, predicate, $scope.reverse);

		  };



	$scope.isOpenRight = function() {
		return $mdSidenav('right').isOpen();
	};

	// ------------find
	// order--------------------------------------------------------------------------------------------------
	$scope.findOrder = function() {



		var requestParameters={
				department:null,
				startPosition : $scope.startPosition,
				maxResult : 10
			};

		//Restangular.all('/orders').customGET("",requestParameters ).then(function(data) {
		Restangular.all('/orders/for-pickup').getList(requestParameters ).then(function(data) {

			if (data instanceof Array) {
				$scope.orderList = [];
				$scope.orderList = data.plain();
				$scope.showButton=true;

				//$scope.orderDirections('positionInOrder', true);
			//	console.log($scope.order);
			} else {

				// $scope.order = data;
				$scope.orderList = [];
				$scope.orderList.push(data.plain());
			}

			$scope.close();
			//console.log($scope.orderList);
		});
	};

	$scope.findOrder();
	$scope.showNextSite=function(){
		$scope.startPosition+=10;
		$scope.findOrder($scope.orderCriteria);

	};

	$scope.showPreviousSite=function(){
		$scope.startPosition-=10;
		$scope.findOrder($scope.orderCriteria);

	};



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
	};

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
	};

	$scope.receivingPersonUpdate = function( item) {
		if (item.warehouseReleasePerson != null && item.warehouseReleasePerson != "") {
			item.warehouseReleaseDate = new Date();
			item.realisingPerson = $rootScope.currentUser.name;
		} else {
			item.warehouseReleaseDate = null;
			item.realisingPerson=null;
		}

	};

	$scope.findOrdrList = function(orderNumber) {
		var base = Restangular.all('/orders');
		base.getList({
			number : orderNumber
		}).then(function(data) {
			$scope.orderList = data;
		});
	};

	function buildToggler(navID) {
		return function() {
			$mdSidenav(navID).toggle().then(function() {
				//$log.debug("toggle " + navID + " is done");
			});
		}
	}


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




	$scope.close = function() {
		$mdSidenav('right').close().then(function() {
			$log.debug("close RIGHT is done");
		});
	};



}]);