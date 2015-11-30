/**
 * 
 */
angular.module('myApp.controllers').controller('TableViewsListCtrl', TableViewsListCtrl);

function TableViewsListCtrl($scope,  /*TableViewsFactory,TableViewFactory, UploadFileFactory,*/  $location,Restangular,RestFulResponse,$mdDialog) {

	$scope.editTableView = function(tableView) {

		var now = new Date();
		var currentVersion = tableView.version;

		if (!tableView.isDispatched) {
			tableView.deliveryDate = now;
		} else {
			tableView.deliveryDate = null;
		}

		tableView.isDispatched = !tableView.isDispatched;
		TableViewFactory.update(tableView, function success(data) {
			tableView.version = data.version;

		}, function error(data) {
			if (data.status == 403) {
				alert("Brak uprawnień do tej operacji!");
			} else {
				alert("Błąd podczas aktualizacji bazy danych.")
			}
			tableView.isDispatched = false;
			tableView.deliveryDate = null;
		});

	};

	// -----------------------------------------------------------
	$scope.addWarehouseReleasePerson = function(tableView) {
		var currentVersion = tableView.version;
		console.log(currentVersion);
		var now = new Date();

		if (tableView.warehouseReleasePerson.length > 0) {
			tableView.warehouseReleaseDate = now;
		} else {
			tableView.warehouseReleaseDate = null;
		}

	/*	TableViewFactory.update(tableView, function success(data) {

			tableView.version = data.version;

		}, function error(data) {
			if (data.status == 403) {
				alert("Brak uprawnień do tej operacji!");
			} else {
				alert("Błąd podczas aktualizacji bazy danych.")
			}
		});*/
	}

	// -----------------------------------------------------------
	$scope.updateTableView = function(tableView) {
		var currentVersion = tableView.version;

		console.log(currentVersion);
	/*	TableViewFactory.update(tableView, function success(data) {
			tableView.version = data.version;

		}, function error(data) {
			if (data.status == 403) {
				alert("Brak uprawnień do tej operacji!");
			} else {
				alert("Błąd podczas aktualizacji bazy danych.")
			}
		});*/
	}

	// ----------------------upload file with
	// orders----------------------------------------
	$scope.uploadFile = function(evt) {
		console.log("test");

		var orderReferenceInputTxt = document.getElementById("orderReferenceInputTxt");

		var buttonSendFile = document.getElementById("sendFile");

		buttonSendFile.setAttribute('disabled', 'disabled');

		var formElement = document.getElementById("selectedFile");

		var fd = new FormData();
		if ('files' in selectedFile) {
			if (formElement.files.length > 0) {

				var successCallback = function(response) {
					console.log("success");
					buttonSendFile.removeAttribute("disabled");
console.log(response);
					$mdDialog.show(
						      $mdDialog.alert()
						     //   .parent(angular.element(document.querySelector('#popupContainer')))
						        .clickOutsideToClose(true)
						        .title('Uwaga:')
						        .textContent('Dodano zamówienie '+ response.data.number+' !')
						        //.ariaLabel('Alert Dialog Demo')
						        .ok('OK')
						        .targetEvent(evt)
						    );
						  
					buttonSendFile.removeAttribute("disabled");

				};
				var errorCallback = function(response) {
					if (response.status == 403) {
						alert("Brak uprawnień do tej operacji!");
					} else {
						console.log(response.headers().error);
						
						$mdDialog.show(
							      $mdDialog.alert()
							     //   .parent(angular.element(document.querySelector('#popupContainer')))
							        .clickOutsideToClose(true)
							        .title('Błąd. Zamówienia nie dodano!')
							        .textContent(response.headers().error)
							        //.ariaLabel('Alert Dialog Demo')
							        .ok('OK')
							        .targetEvent(evt)
							    );
						
						//alert("Błąd przy dodawaniu: \n"+ response.headers().error);
					}
					buttonSendFile.removeAttribute("disabled");
				};

				fd.append('selectedFile', formElement.files[0]);
				fd.append('orderReference', orderReferenceInputTxt.value);
				
				RestFulResponse.one('/file/upload')
				.withHttpConfig({transformRequest: angular.identity})
				.customPOST(fd, '', undefined, {'Content-Type': undefined}).then(successCallback,errorCallback);
				//UploadFileFactory.upload(fd, successCallback, errorCallback);

			} else {
				alert("Wybierz plik.");
				buttonSendFile.removeAttribute("disabled");
			}
		}

	}

	$scope.createNewTableView = function() {
		$location.path('/user-creation');
	};

	/*$scope.tableViews = TableViewsFactory.query();*/

	/*
	 * _.defer(quickRepeatList.items, $scope.tableViews);
	 */

	// $scope.tableView = TableViewsFactory.show({id:
	// $routeParams.id});

	// ============================grid
	// ==================================================================
	$scope.gridOptions = {};
	$scope.gridOptions.enableCellEditOnFocus = true;
	$scope.gridOptions.enableGridMenu = true;
	// var rowtpl='<div ng-class="{\'green\':true,
	// \'blue\':row.entity.isDispatched==true }"><div
	// ng-repeat="(colRenderIndex, col) in colContainer.renderedColumns track by
	// col.colDef.name" class="ui-grid-cell" ng-class="{
	// \'ui-grid-row-header-cell\': col.isRowHeader }"
	// ui-grid-cell></div></div>';

	$scope.gridOptions = {
		enableFiltering : true,
		// rowTemplate:rowtpl,
		data : $scope.tableViews
	};

	$scope.gridOptions.columnDefs = [ {
		name : 'id',
		displayName : 'id',
		enableCellEdit : false,
		width : '3%',
		enableFiltering : false
	}, {
		name : 'orderedPerson',
		displayName : 'Zamawiający',
		enableCellEdit : true
	}, {
		name : 'orderId',
		displayName : 'Zamówienie',
		enableCellEdit : true,
		width : '7%'
	}, , {
		name : 'supplier',
		displayName : 'Dostawca',
		enableCellEdit : true
	}, {
		name : 'factory',
		displayName : 'Zakł.',
		enableCellEdit : true,
		width : '4%'
	}, {
		name : 'index',
		displayName : 'Index',
		enableCellEdit : true
	}, {
		name : 'goodsName',
		displayName : 'Nazwa',
		enableCellEdit : true,
		width : '20%'
	}, {
		name : 'goodsAmount',
		displayName : 'Ilość',
		enableCellEdit : true,
		width : '4%',
		enableFiltering : false
	}, {
		name : 'goodsUnit',
		displayName : 'JM',
		enableCellEdit : true,
		width : '3%',
		enableFiltering : false
	}, {
		name : 'mpk',
		displayName : 'MPK',
		enableCellEdit : true
	}, {
		name : 'budget',
		displayName : 'Zlecenie',
		enableCellEdit : true
	}, {
		name : 'expectedDeliveryDate',
		displayName : 'Plan. dat dostaw.',
		enableCellEdit : true
	}, {
		name : 'isDispatched',
		displayName : 'Czy dost.',
		type : 'boolean',
		enableCellEdit : true,
	// cellClass: function(grid, row, col, rowRenderIndex, colRenderIndex) {
	// if (grid.getCellValue(row,col)===true ) {

	// }}/*,
	// cellTemplate : '<input id="editBtn" type="checkbox" checked="false" />'*/
	// cellTemplate : "<input name='isDispatched' type='checkbox'
	// data-bind='checked: isDispatched' #= isDispatched ? checked='checked' :
	// '' #/>"

	}, {
		name : 'deliveryDate',
		displayName : 'Data dost.',
		type : 'date',
		cellFilter : 'date:"dd-MM-yyyy  HH:mm"',
		enableCellEdit : true,
	}, {
		name : 'warehouseReleasePerson',
		displayName : 'Pobrał',
		enableCellEdit : true
	}, {
		name : 'warehouseReleaseDate',
		displayName : 'Data pobr.',
		type : 'date',
		cellFilter : 'date:"dd-MM-yyyy  HH:mm"',
		enableCellEdit : true
	}, {
		name : 'orderReference',
		displayName : 'Referencja',
		enableCellEdit : true
	}, {
		name : 'edit',
		enableFiltering : false,
		enableCellEdit : false,
		displayName : '',
		cellTemplate : '<button id="editBtn" type="button" class=" btn btn-default" ng-click="" >Usuń</button> '
	}

	];

	$scope.gridOptions.onRegisterApi = function(gridApi) {
		// set gridApi on scope
		$scope.gridApi = gridApi;
		gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {

			/* alert("uaktualniono: "+colDef.name); */

			if (newValue != oldValue) {

				if (colDef.name == 'isDispatched') {

					$scope.editTableView(rowEntity);
					$scope.$apply();
					conosle.log("editTableView " + JSON.stringify(rowEntity));
				} else {
					$scope.updateTableView(rowEntity);
					$scope.$apply();
					conosle.log("updateTableView " + JSON.stringify(rowEntity));
					// alert("uwaga: kolumna "+colDef.name+"
					// :"+JSON.stringify(rowEntity));

				}

			}

		});
	};

};
