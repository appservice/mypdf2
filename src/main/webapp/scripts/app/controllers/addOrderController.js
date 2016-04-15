/**
 * 
 */
angular.module('myApp.controllers').controller('AddOrderController',['$scope',  '$location','Restangular','RestFulResponse','$mdDialog',
function ($scope,  $location,Restangular,RestFulResponse,$mdDialog) {

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
							        .textContent(response.data.error)
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


}]);
