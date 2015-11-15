

angular.module('mypdf2').controller('EditItemController', function($scope, $routeParams, $location, flash, ItemResource , OrderResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.item = new ItemResource(self.original);
            OrderResource.queryAll(function(items) {
                $scope.orderSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.item.order && item.id == $scope.item.order.id) {
                        $scope.orderSelection = labelObject;
                        $scope.item.order = wrappedObject;
                        self.original.order = $scope.item.order;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The item could not be found.'});
            $location.path("/Items");
        };
        ItemResource.get({ItemId:$routeParams.ItemId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.item);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The item was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.item.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Items");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The item was deleted.'});
            $location.path("/Items");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.item.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("orderSelection", function(selection) {
        if (typeof selection != 'undefined') {
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
    
    $scope.get();
});