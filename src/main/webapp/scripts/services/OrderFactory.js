angular.module('mypdf2').factory('OrderResource', function($resource){
    var resource = $resource('resources/orders/:OrderId',{OrderId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});