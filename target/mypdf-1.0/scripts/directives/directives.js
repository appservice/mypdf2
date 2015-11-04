/**
 * 
 */
var app = angular.module('ngApp.directives', []);

app.directive('myOnChangeSecond', function() {
	return {
		restrict : 'A',
		require : 'ngModel',
		scope : {
			myOnChange : '='
		},
		link : function(scope, elm, attr) {
			if (attr.type === 'radio' || attr.type === 'checkbox') {
				return;
			}
			// store value when get focus
			elm.bind('focus', function() {
				scope.value = elm.val();

			});

			// execute the event when loose focus and value was change
			elm.bind('blur', function() {
				var currentValue = elm.val();
				if (scope.value !== currentValue) {
					if (scope.myOnChange) {
						scope.myOnChange();
					}
				}
			});
		}
	};
});

app.directive('postRepeatDirective', [
		'$timeout',
		function($timeout) {
			return function(scope) {
				if (scope.$first)
					window.a = new Date(); // window.a can be updated anywhere
											// if to reset counter at some
											// action if ng-repeat is not
											// getting started from $first
				if (scope.$last)
					$timeout(function() {
						console.log("## DOM rendering list took: "
								+ (new Date() - window.a) + " ms");
					});
			};
		} ]);

/*app.directive('render-list', function (arr) {
	  this.el.innerHTML = arr.map(function (item) {
	    return '<li>' + item + '</li>' // just concat some html string here
	  })
	});
*/

app.directive('myFilterInject', function() {
    return {
        restrict: 'AEC',
        templateUrl: 'templates/header.html', // markup for template
        scope: {
            type: '=' // allows data to be passed into directive from controller scope
        }
    };
});