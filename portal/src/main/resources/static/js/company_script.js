var companyHome = angular.module("companyHome", ["ngRoute"]);
companyHome.config(function ($routeProvider) {

    $routeProvider.when("/postJob", {
        templateUrl: "../angular/PostJob.html"
    }).when("/companyProfile", {
        templateUrl: "../angular/CompanyProfile.html"
    }).when("/browseJobs", {
        templateUrl: "../angular/BrowseJobs.html"
    }).when("/jobDetail/:id", {
        templateUrl: "../angular/JobDetail.html"
    }).otherwise({
        templateUrl: "../angular/BrowseJobs.html"
    });
});

// companyHome.service('JobDetailService', function(value) {
//         this.jobId = value;
// });

companyHome.controller("companyHomeCtrl", function ($scope) {
});

companyHome.controller("browseJobCtrl", function ($scope, $http) {

    $scope.successMsg = true;
    $scope.failureMsg = true;
    $scope.loadFailMsg = true;
    // fetch all job positions
    // $http({
    //     method: "GET",
    //     url: '/jobPosition/getAll'
    //
    // }).success(function (data) {
    //
    //     if (data.statusCode == 401) {
    //         $scope.loadFailMsg = false;
    //
    //     } else {
    //         $scope.loadFailMsg = true;
    //         $scope.jobPositions = data.data;
    //     }
    //
    // }).error(function (error) {
    //     $scope.loadFailMsg = false;
    // });

});

companyHome.controller("jobCtrl", function ($scope, $http) {
    $scope.successMsg = true;
    $scope.failureMsg = true;

    $scope.submit = function () {

        $http({
            method: "POST",
            url: '/jobPosition/create',
            data: {
                "title": $scope.title,
                "description": $scope.description,
                "responsibilities": $scope.responsibilities,
                "officeLocation": $scope.officeLocation,
                "salary": $scope.salary
            }
        }).success(function (data) {

            if (data.statusCode === 401) {
                $scope.failureMsg = false;
                $scope.successMsg = true;
            } else {
                $scope.failureMsg = true;
                $scope.successMsg = false;
                // clearing form input post submission
                var form = document.getElementsByName('listing-form')[0];
                form.reset();
            }

        }).error(function (error) {
            $scope.failureMsg = false;
            $scope.successMsg = true;

        });

    };

});

companyHome.controller("profileCtrl", function ($scope, $http) {

    $scope.successMsg = true;
    $scope.failureMsg = true;
    $scope.disabled = true;
    $scope.loadFailMsg = true;
    // fetch company profile
    // $http({
    //     method: "GET",
    //     url: '/company'
    //
    // }).success(function (data) {
    //
    //     if (data.statusCode === 401) {
    //         $scope.loadFailMsg = false;
    //     } else {
    //         $scope.name = data.name;
    //         $scope.website = data.website;
    //         $scope.logoImgURL = data.logoImgURL;
    //         $scope.addressHQ = data.addressHQ;
    //         $scope.description = data.description;
    //         $scope.contact = data.contact;
    //     }
    //
    // }).error(function (error) {
    //     $scope.loadFailMsg = false;
    // });
    //
    // $scope.update = function () {
    //
    //     $http({
    //         method: "POST",
    //         url: '/company/update',
    //         data: {
    //             "name": $scope.name,
    //             "website": $scope.website,
    //             "logoImgURL": $scope.logoImgURL,
    //             "addressHQ": $scope.addressHQ,
    //             "description": $scope.description,
    //             "contact": $scope.contact
    //         }
    //     }).success(function (data) {
    //
    //         if (data.statusCode === 401) {
    //             $scope.failureMsg = false;
    //
    //         } else {
    //             $scope.successMsg = false;
    //             $scope.disabled = true;
    //         }
    //
    //     }).error(function (error) {
    //         $scope.failureMsg = false;
    //     });
    //
    // };

    $scope.edit = function () {
        $scope.disabled = false;
        $scope.successMsg = true;
        $scope.failureMsg = true;
    };

});

companyHome.controller("jobDetailCtrl", function ($scope, $http, $routeParams) {

    $scope.id = $routeParams.id;
    $scope.successMsg = true;
    $scope.failureMsg = true;
    $scope.disabled = true;
    $scope.loadFailMsg = true;
    // fetch job info
    // $http({
    //     method: "GET",
    //     url: '/jobPosition/' + id
    //
    // }).success(function (data) {
    //
    //     if (data.statusCode === 401) {
    //         $scope.loadFailMsg = false;
    //     } else {
    //         $scope.name = data.name;
    //         $scope.website = data.website;
    //         $scope.logoImgURL = data.logoImgURL;
    //         $scope.addressHQ = data.addressHQ;
    //         $scope.description = data.description;
    //         $scope.contact = data.contact;
    //     }
    //
    // }).error(function (error) {
    //     $scope.loadFailMsg = false;
    // });
    //
    // $scope.update = function () {
    //
    //     $http({
    //         method: "POST",
    //         url: '/company/update',
    //         data: {
    //             "name": $scope.name,
    //             "website": $scope.website,
    //             "logoImgURL": $scope.logoImgURL,
    //             "addressHQ": $scope.addressHQ,
    //             "description": $scope.description,
    //             "contact": $scope.contact
    //         }
    //     }).success(function (data) {
    //
    //         if (data.statusCode === 401) {
    //             $scope.failureMsg = false;
    //
    //         } else {
    //             $scope.successMsg = false;
    //             $scope.disabled = true;
    //         }
    //
    //     }).error(function (error) {
    //         $scope.failureMsg = false;
    //     });
    //
    // };

    $scope.edit = function () {
        $scope.disabled = false;
        $scope.successMsg = true;
        $scope.failureMsg = true;
    };

});