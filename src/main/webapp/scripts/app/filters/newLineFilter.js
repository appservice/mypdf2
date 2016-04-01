/**
 * Created by LMochel on 2016-04-01.
 */
angular.module('myApp.filters').filter('newlines', function () {
    return function(text) {
        return text.replace(/\n/g, '<br/>');
       // return text.replace(/(&#13;)?&#10;/g, '<br/>');
    }
});