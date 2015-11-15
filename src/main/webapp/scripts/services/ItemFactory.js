angular.module('mypdf2').factory('ItemResource', function($resource){
    var resource = $resource('resources/items/:ItemId',{ItemId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});