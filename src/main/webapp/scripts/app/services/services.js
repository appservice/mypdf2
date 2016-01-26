/**
 * 
 */
angular.module('myApp.services',[]);//'RestFulResponse'

/*services.factory('TableViewsFactory', function ($resource) {
    return $resource('/mypdf/resources/tableviews', {}, {
        'query': { method: 'GET', isArray: true ,headers:{'Content-Type': 'application/json;charset=utf-8'},cache:true},
        'create': { method: 'POST',headers:{'Content-Type': 'application/json;charset=utf-8'} }
    })
});

services.factory('TableViewFactory', function ($resource) {
    return $resource('/mypdf/resources/tableviews/:id', {}, {
        'show': { method: 'GET' ,isArray:false,headers:{'Content-Type': 'application/json;charset=utf-8'}},
        'update': { method: 'PUT', params: {id: '@id'},headers:{'Content-Type': 'application/json;charset=utf-8'} },
        'delete': { method: 'DELETE', params: {id: '@id'},headers:{'Content-Type': 'application/json;charset=utf-8'} }
    })
});

services.factory('UploadFileFactory', function($resource){//formDataObject
	return $resource('/mypdf/resources/file/upload',{},{
		'upload':{method:'POST',isArray: true}, 
	})
});*/

