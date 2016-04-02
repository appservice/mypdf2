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
            $scope.page={};
            $scope.startPosition = 0;
            $scope.orderCriteria = {};
            $scope.orderCriteria.page=0;
           // $scope.showButton = false;
            $scope.reverse = true;
            $scope.currentPage=1;




            var orderBy = $filter('orderBy');

            $scope.orderDirections = function (predicate, orderId) {
                $scope.predicate = predicate + orderId;
                $scope.reverse = ($scope.predicate === (predicate + orderId)) ? !$scope.reverse : false;
                $scope.orderList[orderId].items = orderBy($scope.orderList[orderId].items, predicate, $scope.reverse);

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
                    page:  $scope.orderCriteria.page,
                    size: 10,
                    sort: 'DESC'
                };

                //Restangular.all('/orders').customGET("",requestParameters ).then(function(data) {
                Restangular.one('/paged-orders/for-pickup', "").get(requestParameters).then(function (data) {

                 //   if (data instanceof Array) {
                 //       $scope.orderList = [];
                  //      $scope.orderList = data.plain();
                   //     $scope.showButton = true;

                        //$scope.orderDirections('positionInOrder', true);
                        //	console.log($scope.order);
                 //   } else {

                        // $scope.order = data;
                       // $scope.orderList = [];
                        $scope.page = data.plain();
                        // $scope.orderList.push(data.plain());
                  //  }


                });
            };
            $scope.findOrder();



            // ----------------save
            // order--------------------------------------------------------------
            $scope.saveOrder = function (order, numberOnList, ev) {

                RestFulResponse.one('/paged-orders/' + order.id).customPUT(order).then(
                    function (response) {
                        //console.log(response.data.plain());
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

            $scope.changePage=function(page){
                $scope.orderCriteria.page=page-1;
                $scope.findOrder();
            }


        }]);