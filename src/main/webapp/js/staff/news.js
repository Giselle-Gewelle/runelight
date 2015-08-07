(function () {
	
	var app = angular.module("NewsApp", []);
	
	app.directive('fileModel', ['$parse', function ($parse) {
	    return {
	        restrict: 'A',
	        link: function(scope, element, attrs) {
	            var model = $parse(attrs.fileModel);
	            var modelSetter = model.assign;
	            
	            element.bind('change', function(){
	                scope.$apply(function(){
	                    modelSetter(scope, element[0].files[0]);
	                });
	            });
	        }
	    };
	}]);
	
	app.controller("NewsCtrl", [ "$http", "$scope", function($http, $scope) {
		
		this.showIconPopup = false;
		this.iconList = null;
		this.selectedIcon = "";
		this.fileInput = "";
		this.canUpload = true;
		
		this.selectIcon = function(icon) {
			this.selectedIcon = icon;
			this.showIconPopup = false;
		};
		
		this.isIconSelected = function() {
			return (this.selectedIcon !== "");
		};
		
		this.hasIcons = function() {
			return (this.iconList !== null && this.iconList.length > 0);
		};
		
		this.openIconPopup = function() {
			$("#uploadError").css("display", "none");
			$("#uploadHr").css("display", "none");
			var thisObj = this;
			
			$http({
				method: "GET",
				url: getIconsUrl
			}).success(function(response) {
				if(response.iconCount > 0) {
					thisObj.iconList = response.iconList;
				}
				
				thisObj.showIconPopup = true;
			});
		};
		
		this.uploadIcon = function() {
			var uploadHr = $("#uploadHr").css("display", "none");
			var uploadError = $("#uploadError");
			uploadError.html("Uploading, please wait...").css("display", "block");
			
			this.canUpload = false;
			var thisObj = this;
			
			var formData = new FormData();
			formData.append("iconFile", $scope.myFile);
			
			$http({
	            method: "POST",
	            url: uploadIconUrl,
	            headers: {
	                "Content-Type": undefined
	            },
	            data: formData,
	            transformRequest: angular.identity
	        }).success(function(response) {
	        	if(response.successful) {
	        		thisObj.selectedIcon = response.newFileName;
	        		thisObj.showIconPopup = false;
	        		thisObj.canUpload = true;
	        	} else {
	        		uploadError.html(response.errorMsg);
	        		uploadHr.css("display", "block");
	        		thisObj.canUpload = true;
	        	}
	        });
		};
		
	}]);
	
})();