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
		this.fileInput = "";
		this.canUpload = true;
		this.errorMsg = "";
		
		this.formValues = {
			title: initialTitle,
			category: initialCategory,
			description: initialDescription,
			article: initialArticle,
			icon: initialIcon
		};
		
		this.maxCharacters = {
			title: 50,
			description: 1024,
			article: 65535
		};
		this.remainingCharacters = {
			title: 0,
			description: 0,
			article: 0
		};
		
		this.validate = function() {
			this.errorMsg = "";
			$("#title").removeClass("error");
			$("#category").removeClass("error");
			$("#description").removeClass("error");
			$("#article").removeClass("error");
			
			var f = this.formValues;
			if(f.title.length < 1 || f.title.length > 50) {
				this.errorMsg = "Titles must be between 1 and 50 characters in length.";
				this.setFieldError("title");
				return;
			}
			
			if(f.category == "") {
				this.errorMsg = "Please select a news category.";
				this.setFieldError("category");
				return;
			}
			
			if(f.description.length < 1 || f.description.length > 1024) {
				this.errorMsg = "Descriptions must be between 1 and 1,024 characters in length.";
				this.setFieldError("description");
				return;
			}
			
			if(f.article.length < 1 || f.article.length > 1024) {
				this.errorMsg = "Articles must be between 1 and 65,535 characters in length.";
				this.setFieldError("article");
				return;
			}
			
			if(f.icon.length < 1) {
				this.errorMsg = "Please select a news icon.";
				return;
			}
			
			$("#newsArticleForm").submit();
		};
		
		this.setFieldError = function(fieldId) {
			$("#" + fieldId).addClass("error");
			$("#" + fieldId).select();
		};
		
		this.showErrorMsg = function() {
			return (this.errorMsg !== "");
		};
		
		this.updateInput = function(name) {
			this.remainingCharacters[name] = (this.maxCharacters[name] - this.formValues[name].length);
		};
		
		this.selectIcon = function(icon) {
			this.formValues.icon = icon;
			this.showIconPopup = false;
		};
		
		this.isIconSelected = function() {
			return (this.formValues.icon !== "");
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
	        		thisObj.formValues.icon = response.newFileName;
	        		thisObj.showIconPopup = false;
	        		thisObj.canUpload = true;
	        	} else {
	        		uploadError.html(response.errorMsg);
	        		uploadHr.css("display", "block");
	        		thisObj.canUpload = true;
	        	}
	        });
		};
		
		this.updateInput("title");
		this.updateInput("description");
		this.updateInput("article");
		
	}]);
	
})();