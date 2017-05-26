var RequestData = {

}

var ResumeString = "";

// Empty JS for your own code to be here
var application = {
	userId : null,
	resume : null,
	jobId : null
}

var app = angular.module('jobSeeker', [ 'ui.bootstrap', "ui.grid","checklist-model" ]);
app
		.controller(
				'userProfile',
				function($scope, $modal, $http) {
					$scope.init = function() {
						$scope.fetchProfile();
					};
					$scope.gridOptions1 = {
						enableSorting : true,
						appScopeProvider : $scope,
						columnDefs : [
								{
									field : 'MyFav',
									sortable : false,
									cellTemplate : '<input type="checkbox" ng-model="row.entity.checked"></input>'
								},
								{
									field : 'name'
								},
								{
									field : 'title'
								},
								{
									field : 'description'
								},
								{
									field : 'responsibilities'
								},
								{
									field : 'officeLocation'
								},
								{
									field : 'salary'
								},
								{
									field : 'Apply',
									sortable : false,
									cellTemplate : '<button class="btn btn-default"	ng-click="grid.appScope.showModal(row.entity)">Apply</button>'
								} ],

						onRegisterApi : function(gridApi) {
							$scope.grid1Api = gridApi;
						}
					};

					$scope.fetchProfile = function() {
						$http
								.get('/getseeker')
								.then(
										function(response) {
											var data = response.data;
											$scope.profile.userId = data.userId;
											$scope.profile.firstName = data.firstName;
											$scope.profile.lastName = data.lastName;
											$scope.profile.workExperience = data.workExperience;
											$scope.profile.education = data.education;
											$scope.profile.selfIntroduction = data.selfIntroduction;
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
					$scope.favoriteJobs = [];
					$scope.applications = [];
					$scope.companiesFilter = [];
					$scope.selectedCompaniesFilter = []; 
					$scope.searchLocations =[]; 
					$scope.selectedSearchLocations = []; 
					$scope.minSalFilter=0;
					$scope.maxSalFilter=0;
					$scope.saveProfile = function() {
						$http({
							method : 'POST',
							url : '/updateProfile',
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

						var FavList = $scope.favoriteJobs.filter(function(
								position) {
							return position.checked;
						});

						for ( var iterable_element in FavList) {
							delete FavList[iterable_element].checked;
						}

						$http({
							method : 'POST',
							url : '/updateFavfromFav',
							data : FavList
						}).then(function successCallback(response) {
							if (response.data == 0) {
								$scope.fetchProfile();
								$scope.searchJobs();
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
							url : '/applyfromFav',
							data : application
						}).then(function successCallback(response) {
							if (response.data == 0) {
								$scope.fetchProfile();
								alert("Updated Successfully");
							}
							else{
								alert("Can not apply because either user might have more than 5 pending applications or status in not in terminal status.");
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
							url : '/cancelApplied',
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
					
					$scope.rejectfromApplied = function(jobitem) {

						$http({
							method : 'POST',
							url : '/rejectfromApplied',
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
					
					$scope.acceptfromApplied = function(jobitem) {

						$http({
							method : 'POST',
							url : '/acceptfromApplied',
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

					$scope.searchJobs = function() {

					// var formData = $("#txtSearch").val();
						
						var SearchData={
								searchString: $("#txtSearch").val(),
								companyName:$scope.selectedCompaniesFilter,
								officeLocation:$scope.selectedSearchLocations,
								minSal:$scope.minSalFilter,
								maxSal:$scope.maxSalFilter
						}
						if (SearchData != "") {
							$http({
								method : 'POST',
								url : '/jobs',
								data : SearchData
							})
									.then(
											function successCallback(response) {

												var companiesFilter = [];
												var location = [];

												var positions = response.data
														.map(function(result) {
															result.name = result.company.name;
															delete result.applications;
															return result;
														});

												positions
														.map(function(value) {
															companiesFilter
																	.push(value.name);
															location
																	.push(value.officeLocation);

															$scope.favoriteJobs
																	.map(function(
																			value1) {
																		if (value1.jobId == value.jobId) {
																			value.checked = true;
																		}
																	})
														});

												$scope.gridOptions1.data = positions;
												var abc = companiesFilter.filter((v, i, a) => a.indexOf(v) === i); 
												$scope.companiesFilter=[];
												abc.map(function(items){
													$scope.companiesFilter.push(items);
												})
												$scope.searchLocations = location.filter((v, i, a) => a.indexOf(v) === i); 
											},
											function errorCallback(response) {
												alert("Error Occured");
											});
						}
						return false;
					};

					$scope.saveFavinSearch = function() {
						var currentData = $scope.gridOptions1.data;
						var FavList = currentData.filter(function(fav) {
							return fav.checked;
						});

						currentData
								.map(function(currentFav) {

									if (currentFav.checked) {

										var FavList = $scope.favoriteJobs
												.filter(function(position) {
													return position.jobId == currentFav.jobId;
												});
										if (FavList.length < 1) {
											$scope.favoriteJobs
													.push(currentFav);
										}

									} else {
										var FavList = $scope.favoriteJobs
												.filter(function(position) {
													return position.jobId == currentFav.jobId;
												});
										if (FavList.length > 0) {

											var curIndex = -1;
											$scope.favoriteJobs
													.map(function(val, index) {
														if (val.jobId == currentFav.jobId) {
															curIndex = index;
														}
													})

											$scope.favoriteJobs.splice(
													curIndex, 1);
										}
									}

								})

						$scope.saveFavinFav();

					}

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
					
					
					$scope.uploadPic= function(){
						$('#fileUploadProfilePic').ajaxForm({
							url : '/uploadCurrentResume', // or
																				// whatever
							contentType : false,
							success : function(response) {
								$scope.profile.picKey = response;
								alert("Uploded Successfully");
							}
						});
						$("#fileUploadProfilePic").submit()	
						
					}

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
			url : '/uploadCurrentResume', // or whatever
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
