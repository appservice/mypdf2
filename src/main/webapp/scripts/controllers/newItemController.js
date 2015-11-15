
angular.module('mypdf2').controller('NewItemController', function ($scope, $location, locationParser, flash, ItemResource , OrderResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.item = $scope.item || {};
    
    $scope.orderList = OrderResource.queryAll(function(items){
        $scope.orderSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("orderSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.item.order = {};
            $scope.item.order.id = selection.value;
        }
    });
    
    $scope.dispatchedList = [
        "true",
        "false"
    ];

    $scope.releasedList = [
        "true",
        "false"
    ];


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The item was created successfully.'});
            $location.path('/Items');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        ItemResource.save($scope.item, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Items");
    };
});