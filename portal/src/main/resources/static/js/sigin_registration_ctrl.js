var siginRegister = angular.module("siginRegister", ["ngRoute", "ngProgress"]);
siginRegister.config(function ($routeProvider) {

    $routeProvider.when("/companySigninReg", {
        templateUrl: "../angular/company_signin_reg.html"
    }).when("/jobSeekerSigninReg", {
        templateUrl: "../angular/job_seeker_sigin_reg.html"
    }).when("/Companyregister", {
        templateUrl: "../angular/company_signin_reg.html"
    }).when("/Companysign_in", {
        templateUrl: "../angular/company_signin_reg.html"
    }).otherwise({
        templateUrl: "../angular/job_seeker_sigin_reg.html"
    });
});

siginRegister.controller("companySigninRegCtrl", function ($scope, $http, ngProgressFactory) {

    $scope.wrongPwd = true;
    $scope.noSuchEmail = true;
    $scope.success = true;

    $scope.showVerificationCode = false;
    $scope.verificationCode = '';

    $scope.signIn = function () {

        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('6px');
        $scope.progressbar.setColor('#4EBADB');
        $scope.progressbar.start();

        $http({
            method: "POST",
            url: '/retrieveCompany',
            data: {
                "email": $scope.email,
                "password": $scope.password,
                "verificationCode": $scope.verificationCode
            }
        }).success(function (response) {
            // checking the response data for statusCode
            if (response.message == "success") {
                window.location.assign("/companyHome");

            } else if (response.message == "verify") {
                $scope.showVerificationCode = true;

            } else {
                alert("Error occured");

            }
            $scope.progressbar.complete();

        }).error(function (error) {
            $scope.progressbar.complete();
            // TODO: error handling
        });

    };

    $scope.register = function () {

        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('6px');
        $scope.progressbar.setColor('#4EBADB');
        $scope.progressbar.start();

        data = {
            "name": $scope.name,
            "website": $scope.website,
            "logoImageURL": $scope.logoImageURL,
            "addressHQ": $scope.addressHQ,
            "newEmail": $scope.newEmail,
            "newPassword": $scope.newPassword
        }
        $http({
            method: "POST",
            url: '/registerCompany',
            data: data
        }).success(function (response) {
            // checking the response data for statusCode
            if (response.message == "success") {
                alert("please check your mail for verification code");

            }
            else {
                alert("Error occured");
            }
            $scope.progressbar.complete();

        }).error(function (error) {
            alert("Error occured");
            $scope.progressbar.complete();
        });

        return false;

    };

});

siginRegister.controller("jobSeekerSigninRegCtrl", function ($scope, $http, ngProgressFactory) {

    $scope.wrongPwd = true;
    $scope.noSuchEmail = true;
    $scope.success = true;
    $scope.showVerificationCode = false;
    $scope.verificationCode = '';
    $scope.signIn = function () {

        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('6px');
        $scope.progressbar.setColor('#4EBADB');
        $scope.progressbar.start();

        // TODO: just follow company signin after uncommenting the code below
        $http({
            method: "POST",
            url: '/retrieveJobSeeker',
            data: {
                "email": $scope.email,
                "password": $scope.password,
                "verificationCode": $scope.verificationCode
            }
        }).success(function (response) {
            // checking the response data for statusCode
            if (response.message == "success") {
                window.location.assign("/JobSeekerHome");

            } else if (response.message == "verify") {
                $scope.showVerificationCode = true;

            } else {
                alert("Error occured");

            }
            $scope.progressbar.complete();

        }).error(function (error) {
            $scope.progressbar.complete();
            // TODO: error handling
        });

        return false;

    };

    $scope.registerSeeker = function () {

        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('6px');
        $scope.progressbar.setColor('#4EBADB');
        $scope.progressbar.start();

        data = {
            "email": $scope.newEmail,
            "password": $scope.newPassword,
            "firstName": $scope.firstName,
            "lastName": $scope.lastName
        }
        $http({
            method: "POST",
            url: '/registerJobSeeker',
            data: data
        }).success(function (response) {
            // checking the response data for statusCode
            if (response.message == "success") {
                alert("please check your mail for verification code");

            }
            $scope.progressbar.complete();

        }).error(function (error) {
            alert("Error occured");
            $scope.progressbar.complete();
        });

        return false;

    };

});