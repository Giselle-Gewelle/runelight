(function () {
	
	var app = angular.module("NewsApp", []);
	
	app.controller("NewsCtrl", [ "$http", "$scope", function($http, $scope) {
		
		this.showIconPopup = false;
		this.iconList = null;
		this.selectedIcon = "";
		this.fileInput = "";
		
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
			var thisObj = this;
			
			$http({
				method: "GET",
				url: getIconsUrl
			}).success(function(response) {
				thisObj.iconList = response.iconList;
				
				thisObj.showIconPopup = true;
			});
		};
		
		this.uploadIcon = function() {
			var formData = new FormData();
			formData.append("file", this.fileInput);
			
			$http({
	            method: "POST",
	            url: uploadIconUrl,
	            headers: {
	                "Content-Type": undefined
	            },
	            data: formData
	        }).success(function(response) {
	        	alert(JSON.stringify(response, null, 4));
	        });
		};
		
	}]);
	
})();