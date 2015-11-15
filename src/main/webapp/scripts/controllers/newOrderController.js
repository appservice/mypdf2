
angular.module('mypdf2').controller('NewOrderController', function ($scope, $location, locationParser, flash, OrderResource , ItemResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.order = $scope.order || {};
    
    $scope.itemsList = ItemResource.queryAll(function(items){
        $scope.itemsSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("itemsSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.order.items = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.id = selectedItem.value;
                $scope.order.items.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The order was created successfully.'});
            $location.path('/Orders');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        OrderResource.save($scope.order, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Orders");
    };
});