/**
    * Lukasz Mochel
    * Private company LuckyApp
    * Created by lmochel on 2016-03-25.
    */
angular.module('myApp.services')
    .factory('GetLoggedUserService', ['Restangular',function(Restangular) {

return {
    currentUser:Restangular.one('/users/logged',"").get()
      //  Restangular.one('/users/logged', "").get().then(function(currentUser) {
      //      console.log($rootScope.currentUser);
      //   $rootScope.currentUser = currentUser.plain();
            
        //    $rootScope.logout = function() {
         //       Restangular.one("users/logout").customGET("");
        //        $window.location.replace("/"+pathDomain);

        //    };

    //    });
}

    }]);