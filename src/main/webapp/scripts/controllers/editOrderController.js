

angular.module('mypdf2').controller('EditOrderController', function($scope, $routeParams, $location, flash, OrderResource , ItemResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.order = new OrderResource(self.original);
            ItemResource.queryAll(function(items) {
                $scope.itemsSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.order.items){
                        $.each($scope.order.items, function(idx, element) {
                            if(item.id == element.id) {
                                $scope.itemsSelection.push(labelObject);
                                $scope.order.items.push(wrappedObject);
                            }
                        });
                        self.original.items = $scope.order.items;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The order could not be found.'});
            $location.path("/Orders");
        };
        OrderResource.get({OrderId:$routeParams.OrderId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.order);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The order was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.order.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Orders");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The order was deleted.'});
            $location.path("/Orders");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.order.$remove(successCallback, errorCallback);
    };
    
    $scope.itemsSelection = $scope.itemsSelection || [];
    $scope.$watch("itemsSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.order) {
            $scope.order.items = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.id = selectedItem.value;
                $scope.order.items.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});