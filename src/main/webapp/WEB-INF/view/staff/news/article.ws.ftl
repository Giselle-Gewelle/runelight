<#assign showSessionBar=true />
<#assign cssImports = [ "staff/news" ] />
<#assign jsImports = [ "lib/jquery-2.1.4.min", "lib/angular.min", "staff/news" ] />

<#assign angular = 'ng-app="NewsApp" ng-controller="NewsCtrl as ctrl"' />

<#macro script>
	var getIconsUrl = "${url('staff', 'news/getIcons.ws', true)}";
	var uploadIconUrl = "${url('staff', 'news/uploadIcon.ws')}";
	
	var initialTitle = "<#if editArticle??>${editArticle.title}</#if>";
	var initialCategory = "<#if editArticle??>${editArticle.category.id}</#if>";
	var initialDescription = "<#if editArticle??>${editArticle.description}</#if>";
	var initialArticle = "<#if editArticle??>${editArticle.body}</#if>";
	var initialIcon = "<#if editArticle??>${editArticle.iconName}</#if>";
</#macro>

<#macro prepend>
	<div id="iconPopupOverlay" ng-show="ctrl.showIconPopup"></div>
	
	<div id="iconPopup" ng-show="ctrl.showIconPopup">
		<div class="header">
			Select an Icon
		</div>
		
		<div class="content">
			<p ng-hide="ctrl.hasIcons()">There are no icons to display.</p>
			
			<div ng-show="ctrl.hasIcons()">
				<span ng-repeat="icon in ctrl.iconList" ng-click="ctrl.selectIcon(icon)"><img ng-src="${url('main1', 'image.res?name=newsIcons/{{ icon }}', true)}" alt="{{ icon }}" /></span>
			</div>
			
			<hr />
			
			<div ng-show="ctrl.canUpload">
				<strong>Upload a New Icon</strong>
				
				<p>Only images in PNG format will be accepted. Any image uploaded will be automatically resized to 50x50 pixels.</p>
				
				<input type="file" id="iconFile" name="iconFile" file-model="myFile" />
				
				<br />
				
				<button ng-click="ctrl.uploadIcon()">Submit</button>
			</div>
			
			<hr id="uploadHr" />
			
			<div id="uploadError"></div>
		</div>
	</div>
</#macro>

<#include "../../inc/header.ftl" />

<div class="titleFrame">
	<h1><#if editArticle??>Edit<#else>Post</#if> News Article</h1>
	<@a mod="staff" dest="index.ws" secure=true>Staff Center</@a>
</div>

<div class="scroll">
	<div class="top"></div>
	
	<div class="content">
		<h2><#if editArticle??>Edit<#else>Post</#if> News Article</h2>
		
		<hr />
		
		<#if submissionFailed??>
			<span>There was an error with your submission, please 
				<#if editArticle??>
					<@a mod="staff" dest="news/article.ws?id=${editArticle.id}" secure=true>go back</@a>
				<#else>
					<@a mod="staff" dest="news/article.ws" secure=true>go back</@a>
				</#if> and try again.</span><br /><br />
		<#elseif redirectId??>
			<span>Your article has been successfully <#if editArticle??>edited<#else>posted</#if>. You may now <@a mod="news" dest="newsitem.ws?id=${redirectId}">click here</@a> to view it.</span>
			<br /><br />
		<#else>
			<div id="formError" ng-show="ctrl.showErrorMsg()">
				<span>{{ ctrl.errorMsg }}</span>
				
				<hr />
			</div>
			
			<form id="newsArticleForm" method="post" action="${url('staff', 'news/article.ws')}" autocomplete="off">
				<#if editArticle??>
					<input type="hidden" name="id" value="${editArticle.id}" />
				</#if>
				
				<div class="formSection">
					<label for="title">Title:</label>
					<input type="text" id="title" name="title" maxlength="50" 
						ng-model="ctrl.formValues.title" ng-change="ctrl.updateInput('title')" />
					<span>{{ ctrl.remainingCharacters.title }} characters remaining.</span>
				</div>
				
				<div class="formSection">
					<strong>Category:</strong><br />
					<select id="category" name="category" ng-model="ctrl.formValues.category">
						<option value=""></option>
						<#list categoryList as category>
							<option value="${category.id}">${category.name2}</option>
						</#list>
					</select>
				</div>
				
				<div class="formSection">
					<label for="description">Description:</label>
					<textarea id="description" name="description" maxlength="1024" 
						ng-model="ctrl.formValues.description" ng-change="ctrl.updateInput('description')"></textarea>
					<span>{{ ctrl.remainingCharacters.description }} characters remaining.</span>
				</div>
				
				<div class="formSection">
					<label for="article">Article:</label>
					<textarea id="article" name="article" maxlength="65535" 
						ng-model="ctrl.formValues.article" ng-change="ctrl.updateInput('article')"></textarea>
					<span>{{ ctrl.remainingCharacters.article }} characters remaining.</span>
				</div>
				
				<div class="formSection">
					<strong>Icon:</strong><br />
					<img ng-show="ctrl.isIconSelected()" ng-src="${url('main1', 'image.res?name=newsIcons/{{ ctrl.formValues.icon }}', true)}" alt="{{ icon }}" />
					<input type="hidden" name="icon" value="{{ ctrl.formValues.icon }}" />
					<span ng-hide="ctrl.isIconSelected()">None Selected</span>
					<br />
					<div id="selectIconLink" ng-click="ctrl.openIconPopup()">Select</div>
				</div>
			</form>
			
			<button name="submit" ng-click="ctrl.validate()">Submit</button><br /><br />
		</#if>
	</div>
	
	<div class="bottom"></div>
</div>

<#include "../../inc/footer.ftl" />