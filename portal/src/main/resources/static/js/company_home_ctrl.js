var companyHome = angular.module("companyHome", ["ngRoute", "ngProgress"]);
companyHome.config(function ($routeProvider) {

    $routeProvider.when("/postJob", {
        templateUrl: "../angular/post_job.html"
    }).when("/companyProfile", {
        templateUrl: "../angular/company_profile.html"
    }).when("/browseJobs", {
        templateUrl: "../angular/browse_jobs.html"
    }).when("/jobDetail/:id", {
        templateUrl: "../angular/job_detail.html"
    }).otherwise({
        templateUrl: "../angular/browse_jobs.html"
    });
});

companyHome.directive('setHeight', function ($window) {
    return {
        link: function (scope, element) {
            element.css('height', ($window.innerHeight - 100) + 'px');
        }
    }
});

companyHome.controller("browseJobCtrl", function ($scope, $http) {

    // local copy
    var allPositions = [];

    $scope.successMsg = true;
    $scope.failureMsg = true;
    $scope.loadFailMsg = true;
    $scope.noSuchJobs = true;

    // default filer criteria
    $scope.jobStatus = {
        closed: "true",
        open: "true",
        filled: "true"
    }

    // fetch all job positions
    $http({
        method: "GET",
        url: '/jobPosition/getAll'

    }).success(function (data) {

        $scope.jobPositions = [];
        $scope.jobPositions = data;

        allPositions = $scope.jobPositions.slice();

        $scope.loadFailMsg = true;

    }).error(function (error) {

        $scope.loadFailMsg = false;
    });


    $scope.applyFilters = function () {

        $scope.noSuchJobs = true;

        $scope.jobPositions.splice(0, $scope.jobPositions.length);

        console.log($scope.jobStatus);

        if ($scope.jobStatus.closed) {

            for (var i = 0; i < allPositions.length; i++) {
                if (allPositions[i].status == 0) {
                    $scope.jobPositions.push(allPositions[i]);
                }
            }
        }

        if ($scope.jobStatus.open) {

            for (var i = 0; i < allPositions.length; i++) {
                if (allPositions[i].status == 1) {
                    $scope.jobPositions.push(allPositions[i]);
                }
            }
        }

        if ($scope.jobStatus.filled) {

            for (var i = 0; i < allPositions.length; i++) {
                if (allPositions[i].status == 2) {
                    $scope.jobPositions.push(allPositions[i]);
                }
            }
        }

        if ($scope.jobPositions.length < 1) {

            $scope.noSuchJobs = false;
        }
    };

});

// done
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

            if (data.statusCode == 500) {
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

// done
companyHome.controller("profileCtrl", function ($scope, $http) {

    $scope.successMsg = true;
    $scope.failureMsg = true;
    $scope.disabled = true;
    $scope.loadFailMsg = true;
    $scope.data = null;

    // fetch company profile
    $http({
        method: "GET",
        url: '/company'

    }).success(function (data) {

        // if session timed out
        if (data.statusCode == 408) {
            $scope.loadFailMsg = false;

            // TODO: redirect to login screen
            // window.location.assign("/signin");

        } else {

            $scope.name = data.name;
            $scope.website = data.website;
            $scope.logoImageURL = data.logoImageURL;
            $scope.addressHQ = data.addressHQ;
            $scope.description = data.description;
            $scope.contact = data.contact;
        }

    }).error(function (error) {

        $scope.loadFailMsg = false;
    });

    $scope.update = function () {

        $http({
            method: "POST",
            url: '/company/update',
            data: {
                "name": $scope.name,
                "website": $scope.website,
                "logoImageURL": $scope.logoImageURL,
                "addressHQ": $scope.addressHQ,
                "description": $scope.description,
                "contact": $scope.contact
            }
        }).success(function (data) {

            if (data.statusCode === 408) {
                $scope.failureMsg = false;

            } else {
                $scope.successMsg = false;
                $scope.disabled = true;
            }

        }).error(function (error) {
            $scope.failureMsg = false;
        });

    };

    $scope.edit = function () {
        $scope.disabled = false;
        $scope.successMsg = true;
        $scope.failureMsg = true;
    };

});

// done
companyHome.controller("jobDetailCtrl", function ($scope, $http, $routeParams, ngProgressFactory) {

    $scope.successMsg = true;
    $scope.failureMsg = true;
    $scope.disabled = true;
    $scope.noApplications = true;

    $scope.warning400 = true;
    $scope.warning403 = true;

    // fetch job detail
    $http({
        method: "GET",
        url: '/jobPosition/' + $routeParams.id

    }).success(function (data) {

        if (data.statusCode === 404) {

            $scope.loadFailMsg = false;
        } else {

            $scope.data = data;

            if ($scope.data.applications.length < 1) {
                $scope.noApplications = false;
            }
        }

    }).error(function (error) {

        $scope.loadFailMsg = false;
    });

    $scope.update = function () {

        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('6px');
        $scope.progressbar.setColor('#4EBADB');
        $scope.progressbar.start();

        $http({
            method: "POST",
            url: '/jobPosition/update',
            data: $scope.data

        }).success(function (data) {

            if (data.statusCode == 400) {

                $scope.warning400 = false;
            } else if (data.statusCode == 403) {

                $scope.warning403 = false;
            } else {

                $scope.successMsg = false;
            }
            $scope.disabled = true;
            $scope.progressbar.complete();

        }).error(function (error) {

            $scope.failureMsg = false;
            $scope.progressbar.complete();

        });

    };

    $scope.accept = function (application) {

        application.jobPosition = $scope.data;

        $scope.successMsg = true;
        $scope.failureMsg = true;

        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('6px');
        $scope.progressbar.setColor('#4EBADB');
        $scope.progressbar.start();

        $http({
            method: "POST",
            url: '/jobPosition/application/accept',
            data: application

        }).success(function (data) {

            $scope.successMsg = false;
            $scope.progressbar.complete();

        }).error(function (error) {

            $scope.failureMsg = false;
            $scope.progressbar.complete();

        });

    };

    $scope.decline = function (application) {

        application.jobPosition = $scope.data;

        $scope.successMsg = true;
        $scope.failureMsg = true;

        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('6px');
        $scope.progressbar.setColor('#4EBADB');
        $scope.progressbar.start();


        $http({
            method: "POST",
            url: '/jobPosition/application/decline',
            data: application

        }).success(function (data) {

            $scope.successMsg = false;
            $scope.progressbar.complete();

        }).error(function (error) {

            $scope.failureMsg = false;
            $scope.progressbar.complete();

        });

    };


    $scope.edit = function () {
        $scope.disabled = false;
        $scope.successMsg = true;
        $scope.failureMsg = true;
        $scope.warning400 = true;
        $scope.warning403 = true;
    };

});