var RequestData = {

}

var ResumeString = "";

// Empty JS for your own code to be here
var application = {
	userId : null,
	resume : null,
	jobId : null
}

var app = angular.module('jobSeeker', [ 'ui.bootstrap' ]);
app.controller('userProfile', function($scope, $modal, $http) {
	$scope.init = function() {
		$scope.fetchProfile();
	};

	$scope.fetchProfile = function() {
		$http.get('http://localhost:8080/getseeker').then(function(response) {
			var data = response.data;
			$scope.profile.userId = data.userId;
			$scope.profile.firstName = data.firstName;
			$scope.profile.lastName = data.lastName;
			$scope.profile.workExperience = data.workExperience;
			$scope.profile.education = data.education;
			// $scope.profile.selfIntroduction = data.selfIntroduction;
			$scope.profile.skills = data.skills;
			$scope.profile.picKey = data.picKey;
			$scope.applications = data.applications;
			var favJobs = {

			}
			for ( var iterable_element in data.favoriteJobs) {
				data.favoriteJobs[iterable_element].checked = true;
			}
			$scope.favoriteJobs = data.favoriteJobs;
		});

	};

	$scope.profile = {
		userId : "",
		firstName : "",
		lastName : "",
		workExperience : "",
		education : "",
		selfIntroduction : "",
		skills : "",
		picKey : ""
	};
	$scope.saveProfile = function() {
		$http({
			method : 'POST',
			url : 'http://localhost:8080/updateProfile',
			data : $scope.profile
		}).then(function successCallback(response) {
			if (response.data == 0) {
				alert("Updated Successfully");
			}
		}, function errorCallback(response) {
			alert("Error Occured");
		});

	};
	$scope.saveFavinFav = function() {

		var FavList = $scope.favoriteJobs.filter(function(position) {
			return position.checked;
		});

		for ( var iterable_element in FavList) {
			delete FavList[iterable_element].checked;
		}

		$http({
			method : 'POST',
			url : 'http://localhost:8080/updateFavfromFav',
			data : FavList
		}).then(function successCallback(response) {
			if (response.data == 0) {
				$scope.fetchProfile();
				alert("Updated Successfully");
			}
		}, function errorCallback(response) {
			alert("Error Occured");
		});

		// alert(FavList);

	};
	$scope.applyinFav = function(jobitem) {

		var application = {
			status : "pending",
			resumeKey : ResumeString,
			jobPosition : jobitem
		}

		$http({
			method : 'POST',
			url : 'http://localhost:8080/applyfromFav',
			data : application
		}).then(function successCallback(response) {
			if (response.data == 0) {
				$scope.fetchProfile();
				alert("Updated Successfully");
			}
			return false;
		}, function errorCallback(response) {
			alert("Error Occured");
		});
		return false;
	};
	$scope.cancelApplied = function(jobitem) {
		$http({
			method : 'POST',
			url : 'http://localhost:8080/cancelApplied',
			data : jobitem
		}).then(function successCallback(response) {
			if (response.data == 0) {
				$scope.fetchProfile();
				alert("Updated Successfully");
			}
		}, function errorCallback(response) {
			alert("Error Occured");
		});
		return false;
	};

	$scope.favoriteJobs = [];
	$scope.applications = [];
	$scope.showModal = function(jobitem) {
		$scope.opts = {
			backdrop : true,
			backdropClick : true,
			dialogFade : false,
			keyboard : true,
			templateUrl : 'popup.html',
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
			$scope.applyinFav(jobitem);
		}, function() {
			// on cancel button press
			console.log("Modal Closed");
		});
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

	$scope.upload = function() {
		$('#fileUpload').ajaxForm({
			url : 'http://localhost:8080/uploadCurrentResume', // or whatever
			contentType : false,
			success : function(response) {
				ResumeString = response;
				alert("Uploded Successfully");
			}
		});
		$("#fileUpload").submit()
		return false;
	};
}
