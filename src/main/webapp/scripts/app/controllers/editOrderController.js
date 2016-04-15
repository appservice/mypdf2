/**
 * Created by lmochel on 2016-04-07.
 */
/**
 *
 */
angular.module('myApp.controllers').controller('EditOrderController'
    , ['$scope', '$timeout', '$filter', '$mdSidenav', '$log', '$mdDialog', '$mdToast', 'Restangular', 'RestFulResponse', '$rootScope','$routeParams',
        function ($scope, $timeout, $filter, $mdSidenav, $log, $mdDialog, $mdToast, Restangular, RestFulResponse, $rootScope,$routeParams) {
              $scope.orderCriteria = {};
          //  $scope.order={};

            $scope.reverse = true;

            var orderBy = $filter('orderBy');


            $scope.orderDirections = function (predicate, orderId) {
          //      $scope.predicate = predicate + orderId;
          ////      $scope.reverse = ($scope.predicate === (predicate + orderId)) ? !$scope.reverse : false;
           //     $scope.page.content[orderId].items = orderBy($scope.page.content[orderId].items, predicate, $scope.reverse);

            };



            var orderID=$routeParams.orderID;
            console.log(orderID);
          var findOrder = function () {
                Restangular.one('/paged-orders/',orderID).get().then(function (data) {

                    $scope.order = data.plain();
                 //   $scope.dispatchedDate=new Date()

                });
            };

            findOrder();



            // ------save order--------------------------------------------------
            $scope.saveOrder = function (order, numberOnList, ev) {

                RestFulResponse.one('/paged-orders/' + order.id).customPUT(order).then(
                    function (response) {
                        console.log(response.data.plain());
                        $scope.order = response.data.plain();

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
                        var index = $scope.page.content.indexOf(order);
                        if (index > -1) $scope.page.content.splice(index, 1);
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