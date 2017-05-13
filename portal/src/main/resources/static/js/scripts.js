// Empty JS for your own code to be here
var testData = {
	userId : 1,
	firstName : "Sashank",
	lastName : "Malladi",
	workExperience : "3",
	education : "Masters",
	selfIntroduction : "Bla Bla Bla",
	skills : "java,javascript",
	profilePic : "http://media.cntraveler.com/photos/55d242c637284fb1079cba35/16:9/w_1024,c_limit/golden-gate-ferry-san-francisco.jpg",
	favs : [ {
		title : "Developer",
		description : " bla bla bla",
		responsibilities : " bla bla bla",
		officeLocation : "san jose",
		salary : "123456",
		name : "abc1"
	}, {
		title : "Developer",
		description : " bla bla bla",
		responsibilities : " bla bla bla",
		officeLocation : "san jose",
		salary : "123456",
		name : "abc2"
	}, {
		title : "Developer",
		description : " bla bla bla",
		responsibilities : " bla bla bla",
		officeLocation : "san jose",
		salary : "123456",
		name : "abc3"
	} ],
	applications : [ {
		title : "Developer",
		description : " bla bla bla",
		responsibilities : " bla bla bla",
		officeLocation : "san jose",
		salary : "123456",
		name : "abc1",
		status : "Pending"
	}, {
		title : "Developer",
		description : " bla bla bla",
		responsibilities : " bla bla bla",
		officeLocation : "san jose",
		salary : "123456",
		name : "abc2",
		status : "Pending"
	}, {
		title : "Developer",
		description : " bla bla bla",
		responsibilities : " bla bla bla",
		officeLocation : "san jose",
		salary : "123456",
		name : "abc3",
		status : "Pending"
	} ]

}

var application = {
	userId : null,
	resume : null,
	jobId : null
}

var app = angular.module('jobSeeker', [ 'ui.bootstrap' ]);
app.controller('userProfile', function($scope, $modal,$http) {
	$scope.init = function() {
		$http.get('http://localhost:8080/seeker/1').then(function(response) {
			$scope.greeting = response.data;
		});
	};
	$scope.userId = testData.userId;
	$scope.firstName = testData.firstName;
	$scope.lastName = testData.lastName;
	$scope.workExperience = testData.workExperience;
	$scope.education = testData.education;
	$scope.selfIntroduction = testData.selfIntroduction;
	$scope.skills = testData.skills;
	$scope.profilePic = testData.profilePic;
	$scope.favs = testData.favs;
	$scope.applications = testData.applications;
	$scope.applyJob = application;

	$scope.showModal = function() {
		$scope.opts = {
			backdrop : true,
			backdropClick : true,
			dialogFade : false,
			keyboard : true,
			templateUrl : 'index.html',
			controller : ModalInstanceCtrl,
			resolve : {}
		// empty storage
		};

		$scope.opts.resolve.item = function() {
			return $scope // pass name to Dialog
		}

		var modalInstance = $modal.open($scope.opts);

		modalInstance.result.then(function() {
			// on ok button press
		}, function() {
			// on cancel button press
			console.log("Modal Closed");
		});
	};

	$scope.showSelectedJob = function(job) {
		$scope.showSelectedJobModel = true;
		$scope.applyJob.userId = $scope.userId;
		$scope.applyJob.jobId = job;
		$scope.applyJob.resume = "";
	};

});

var ModalInstanceCtrl = function($scope, $modalInstance, $modal, item) {

	$scope.item = item;

	$scope.ok = function() {
		$modalInstance.close();
	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};
}
