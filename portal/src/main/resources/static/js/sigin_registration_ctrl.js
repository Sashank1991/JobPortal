var siginRegister = angular.module("siginRegister", ["ngRoute"]);
siginRegister.config(function ($routeProvider) {

    $routeProvider.when("/companySigninReg", {
        templateUrl: "../angular/company_signin_reg.html"
    }).when("/jobSeekerSiginReg", {
        templateUrl: "../angular/job_seeker_sigin_reg.html"
    }).otherwise({
        templateUrl: "../angular/job_seeker_sigin_reg.html"
    });
});


siginRegister.controller("companySigninRegCtrl", function ($scope, $http) {

    $scope.wrongPwd = true;
    $scope.noSuchEmail = true;
    $scope.success = true;

    $scope.signIn = function() {

        $http({
            method : "POST",
            url : '/company',
            data : {
                "email" : $scope.email,
                "password" : $scope.password
            }
        }).success(function(data) {
            // checking the response data for statusCode
            if (data.statusCode == 401) {
                // TODO: error

            } else {
                console.log("SignIn Success");
                window.location.assign("/home");
            }

        }).error(function(error) {
            // TODO: error handling
        });

    };

    $scope.register = function() {

        console.log("signUp in clicked")

        $http({
            method : "POST",
            url : '/signup',
            data : {
                "email" : $scope.newEmail,
                "password" : $scope.newPassword,
                "firstName" : $scope.firstName,
                "lastName" : $scope.lastName
            }
        }).success(function(data) {
            // checking the response data for statusCode
            if (data.statusCode == 401) {
                // TODO: error

            } else {

                // TODO: success : inform user and redirect to next screen
                $scope.success = false;
                console.log("SignUp Success");
                window.location.assign("/home");
            }

        }).error(function(error) {
            // TODO: error handling
        });

    };

});


siginRegister.controller("jobSeekerSigninRegCtrl", function ($scope, $http) {

    $scope.wrongPwd = true;
    $scope.noSuchEmail = true;
    $scope.success = true;


    $scope.signIn = function() {

        // TODO: just follow company signin after uncommenting the code below
        $http({
            method : "POST",
            url : '',
            data : {
                "email" : $scope.email,
                "password" : $scope.password
            }
        }).success(function(data) {
            // checking the response data for statusCode
            if (data.statusCode == 404) {
                // TODO: error

            } else {
                console.log("SignIn Success");
                window.location.assign("/");
            }

        }).error(function(error) {
            // TODO: error handling
        });

    };

    $scope.register = function() {

        $http({
            method : "POST",
            url : '',
            data : {
                "email" : $scope.newEmail,
                "password" : $scope.newPassword,
                "firstName" : $scope.firstName,
                "lastName" : $scope.lastName
                // TODO: similarly follow
            }
        }).success(function(data) {
            // checking the response data for statusCode
            if (data.statusCode == 500) {
                // TODO: error handling

            } else {

                // TODO: success : inform user and redirect to next screen
                $scope.success = false;
                console.log("Register Success");
                window.location.assign("/");
            }

        }).error(function(error) {
            // TODO: error handling
        });

    };



});