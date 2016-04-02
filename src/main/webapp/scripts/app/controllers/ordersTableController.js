/**
 *
 */
angular.module('myApp.controllers').controller('OrdersTableCtrl'
    , ['$scope', '$timeout', '$filter', '$mdSidenav', '$log', '$mdDialog', '$mdToast', 'Restangular', 'RestFulResponse', '$rootScope',
        function ($scope, $timeout, $filter, $mdSidenav, $log, $mdDialog, $mdToast, Restangular, RestFulResponse, $rootScope) {
            // $scope.toggleLeft = buildDelayedToggler('left');
            $scope.toggleRight = buildToggler('right');
            $scope.page = {};
            $scope.orderList = [];
//	$scope.orderDateFrom=null;
            $scope.startPosition = 0;
            $scope.orderCriteria = {};
            $scope.orderCriteria.page = 0;
            $scope.showButton = false;
            $scope.reverse = true;
            $scope.currentPage = 1;


            var orderBy = $filter('orderBy');


            $scope.orderDirections = function (predicate, orderId) {
                $scope.predicate = predicate + orderId;
                $scope.reverse = ($scope.predicate === (predicate + orderId)) ? !$scope.reverse : false;
                $scope.orderList[orderId].items = orderBy($scope.orderList[orderId].items, predicate, $scope.reverse);

            };


            $scope.isOpenRight = function () {
                // $scope.orderCriteria.page=0;
                return $mdSidenav('right').isOpen();
            };

            // ------------find
            // order--------------------------------------------------------------------------------------------------
            //$scope.param=jQuery.param()

            $scope.findOrder = function (orderCriteria) {


                var requestParameters = {
                    number: orderCriteria.orderNumber || null,
                    supplier: orderCriteria.orderSupplier || null,
                    purchaser: orderCriteria.orderPurchaser || null,
                    factory: orderCriteria.orderFactory || null,

                    itemName: orderCriteria.orderItemName || null,
                    orderReference: orderCriteria.orderReference || null,
                    itemIndex: orderCriteria.itemIndex || null,
                    itemDescription: orderCriteria.itemDescription || null,
                    suppliesGroup: orderCriteria.suppliesGroup || null,
                    warehouseReleasePerson: orderCriteria.warehouseReleasePerson || null,
                    overdueDays: orderCriteria.overdueDays || null,
                    mpk: orderCriteria.mpk || null,
                    budget: orderCriteria.budget || null,
                    receivingPerson: orderCriteria.receivingPerson || null,
                    startPosition: $scope.startPosition,
                    maxResult: 10,
                    page: orderCriteria.page,
                    size: 10,
                    sort: 'DESC'
                };
                if (orderCriteria.orderDateFrom != null) {
                    requestParameters.orderDateFrom = orderCriteria.orderDateFrom.getTime() || null;
                }
                if (orderCriteria.orderDateTo != null) {
                    requestParameters.orderDateTo = orderCriteria.orderDateTo.getTime() || null;
                }

                if (orderCriteria.warehouseReleaseDateFrom != null) {
                    requestParameters.warehouseReleaseDateFrom = orderCriteria.warehouseReleaseDateFrom.getTime() || null;
                }
                if (orderCriteria.warehouseReleaseDateTo != null) {
                    requestParameters.warehouseReleaseDateTo = orderCriteria.warehouseReleaseDateTo.getTime() || null;
                }
                var query = {};
                for (var item in requestParameters) {
                    if (requestParameters[item] != null) {
                        query[item] = requestParameters[item]

                    }
                }
                $scope.myQueryParam = jQuery.param(query);
                jQuery.param(requestParameters, true);

                console.log($scope.myQueryParam);
                Restangular.one('/paged-orders', '').get(requestParameters).then(function (data) {
                    $scope.showButton = true;
                    $scope.page = data.plain();
                    $scope.close();

                });
            };


            $scope.changePage = function (page) {
                $scope.orderCriteria.page = page - 1;
                $scope.findOrder($scope.orderCriteria);
            }


            $scope.clearFilter = function (filter) {


                filter.orderNumber = null;
                filter.orderSupplier = null;
                filter.orderPurchaser = null;
                filter.orderFactory = null;
                filter.orderItemName = null;
                filter.orderReference = null;
                filter.itemIndex = null;
                filter.itemDescription = null;
                filter.suppliesGroup = null;
                filter.warehouseReleasePerson = null;
                filter.overdueDays = 0;
                filter.mpk = null;
                filter.orderDateFrom = null;
                filter.orderDateTo = null;
                filter.warehouseReleaseDateFrom = null;
                filter.warehouseReleaseDateTo = null;
                filter.receivingPerson = null;
                filter.budget = null;


            }
            // ----------------save
            // order--------------------------------------------------------------
            $scope.saveOrder = function (order, numberOnList, ev) {

                RestFulResponse.one('/paged-orders/' + order.id).customPUT(order).then(
                    function (response) {
                        console.log(response.data.plain());
                        $scope.orderList[numberOnList] = response.data.plain();

                        $mdDialog.show($mdDialog.alert()
                            // .parent(angular.element(document.querySelector('#popupContainer')))
                            .clickOutsideToClose(true).title('Uwaga:').textContent('Dane zamówienia ' + order.number + ' zostały ukaktualnione!').ariaLabel(
                                'Alert Dialog Demo').ok('OK').targetEvent(ev));

                    }, function (response) {
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


            $scope.receivingPersonUpdate = function (item) {
               // console.log(item);
                if (item.warehouseReleasePerson != null && item.warehouseReleasePerson != "") {
                    item.warehouseReleaseDate = new Date();
                    item.realisingPerson = $rootScope.currentUser.name;
                } else {
                    item.warehouseReleaseDate = null;
                    item.realisingPerson = null;
                }

            };


            function buildToggler(navID) {
                return function () {
                    $mdSidenav(navID).toggle().then(function () {
                        $scope.orderCriteria.page = 0;
                        $log.debug("toggle " + navID + " is done");
                    });
                }
            }


            $scope.close = function () {
                $mdSidenav('right').close().then(function () {
                    $log.debug("close RIGHT is done");
                });
            };

            $scope.deleteOrder = function (order, ev) {

                console.log(order);

                var confirm = $mdDialog.confirm()
                    .title('Usuwanie zamówienia!?')
                    .textContent('Czy usunąć zamówienie nr: ' + order.number + " ?")
                    .ariaLabel('Usuwanie')
                    .targetEvent(ev)
                    .ok('Usuń')
                    .cancel('Anuluj');
                //console.log(order);

                $mdDialog.show(confirm).then(function () {
                    deleteOrderService();
                }, function () {

                });


                function deleteOrderService() {
                    RestFulResponse.all('/paged-orders/').customDELETE(order.id, {}, {'Content-Type': 'application/json'}).then(function (data) {
                        //order.remove({},{'Content-Type':   'application/json'}).then(function(results){
                        var index = $scope.orderList.indexOf(order);
                        if (index > -1) $scope.orderList.splice(index, 1);
                        console.log('removed order ');
                        console.log(order);
                    }, function (error) {
                        if (error.status == 403) {
                            window.alert('Nie masz uprawnienia do usuwania zamówień!')
                        } else {
                            window.alert('Błąd podczas usuwania -zamówienei nie zostało usunięte!');
                            console.log(error);
                        }


                    });

                }

            }

        }]);