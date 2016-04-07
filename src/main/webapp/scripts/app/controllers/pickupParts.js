/**
 *
 */
angular.module('myApp.controllers').controller('PickupPartsController'
    , ['$scope', '$timeout', '$filter', '$mdSidenav', '$log', '$mdDialog', '$mdToast', 'Restangular', 'RestFulResponse', '$rootScope',
        function ($scope, $timeout, $filter, $mdSidenav, $log, $mdDialog, $mdToast, Restangular, RestFulResponse, $rootScope) {
            // $scope.toggleLeft = buildDelayedToggler('left');
            //  $scope.toggleRight = buildToggler('right');
            //  $scope.orderList = [];
//	$scope.orderDateFrom=null;
            $scope.page = {};
            $scope.startPosition = 0;
            $scope.orderCriteria = {};
            $scope.orderCriteria.page = 0;
            // $scope.showButton = false;
            $scope.reverse = true;
            $scope.currentPage = 1;


            var orderBy = $filter('orderBy');

            $scope.orderDirections = function (predicate, orderId) {
                $scope.predicate = predicate + orderId;
                $scope.reverse = ($scope.predicate === (predicate + orderId)) ? !$scope.reverse : false;
                $scope.page.content[orderId].items = orderBy($scope.page.content[orderId].items, predicate, $scope.reverse);

            };


            $scope.isOpenRight = function () {
                return $mdSidenav('right').isOpen();
            };

            // ------------find
            // order--------------------------------------------------------------------------------------------------
            $scope.findOrder = function () {


                var requestParameters = {
                    department: null,
                    startPosition: $scope.startPosition,
                    maxResult: 10,
                    page: $scope.orderCriteria.page,
                    size: 10,
                    sort: 'DESC'
                };

                //Restangular.all('/orders').customGET("",requestParameters ).then(function(data) {
                Restangular.one('/paged-orders/for-pickup', "").get(requestParameters).then(function (data) {
                    $scope.page = data.plain();

                });
            };
            $scope.findOrder();


            // ----------save order----------------------------------
            $scope.saveOrder = function (order, numberOnList, ev) {

                RestFulResponse.one('/paged-orders/' + order.id).customPUT(order).then(
                    function (response) {
                        //console.log(response.data.plain());
                        $scope.page.content[numberOnList] = response.data.plain();

                        $mdDialog.show($mdDialog.alert()
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

            // --------------item update-------------------------------------
            $scope.itemUpdate = function (orderid, item) {
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

            $scope.receivingPersonUpdate = function (item) {
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
                        //$log.debug("toggle " + navID + " is done");
                    });
                }
            }

            $scope.changePage = function (page) {
                $scope.orderCriteria.page = page - 1;
                $scope.findOrder();
            }


        }]);