<#assign showSessionBar=true />
<#assign cssImports = [ "staff/news" ] />
<#assign jsImports = [ "lib/angular.min", "staff/news" ] />

<#assign angular = 'ng-app="NewsApp" ng-controller="NewsCtrl as ctrl"' />

<#macro prepend>
	<div id="iconPopupOverlay" ng-show="ctrl.showIconPopup"></div>
	
	<div id="iconPopup" ng-show="ctrl.showIconPopup">
		<div class="header">
			Select an Icon
		</div>
		
		<div class="content">
			<p ng-hide="ctrl.hasIcons()">There are no uploaded icons to display, please upload one to continue.</p>
			
			<div ng-show="ctrl.hasIcons()">
				<span ng-repeat="icon in ctrl.iconList" ng-click="ctrl.selectIcon(icon)"><img ng-src="${url('main1', 'image.res?name=newsIcons/{{ icon }}', true)}" alt="{{ icon }}" /></span>
			</div>
			
			<hr />
			
			<strong>Upload</strong><br /><br />
			
				<input type="file" id="iconFile" name="iconFile" ng-model="ctrl.fileInput" />
				<br /><br />
				<button ng-click="ctrl.uploadIcon()">Submit</button>
		</div>
	</div>
</#macro>

<#include "../../inc/header.ftl" />

<script type="text/javascript">
	var getIconsUrl = "${url('staff', 'news/getIcons.ws', true)}";
	var uploadIconUrl = "${url('staff', 'news/uploadIcon.ws')}";
</script>

<div class="titleFrame">
	<h1><#if editId??>Edit<#else>Post</#if> News Article</h1>
	<@a mod="staff" dest="index.ws" secure=true>Staff Center</@a>
</div>

<div class="scroll">
	<div class="top"></div>
	
	<div class="content">
		<h2><#if editing??>Edit<#else>Post</#if> News Article</h2>
		
		<hr />
		
		<form method="post" action="${url('staff', 'news/article.ws')}" autocomplete="off">
			<#if editId??>
				<input type="hidden" name="id" value="${editId}" />
			</#if>
			
			<div class="formSection">
				<label for="title">Title:</label>
				<input type="text" id="title" name="title" maxlength="50" value="" />
			</div>
			
			<div class="formSection">
				<strong>Category:</strong><br />
				<select id="category" name="category">
					<option value=""></option>
					<#list categoryList as category>
						<option value="${category.id}">${category.name2}</option>
					</#list>
				</select>
			</div>
			
			<div class="formSection">
				<label for="description">Description:</label>
				<textarea id="description" name="description" maxlength="1024"></textarea>
			</div>
			
			<div class="formSection">
				<label for="article">Article:</label>
				<textarea id="article" name="article" maxlength="65535"></textarea>
			</div>
			
			<div class="formSection">
				<strong>Icon:</strong><br />
				<img ng-show="ctrl.isIconSelected()" ng-src="${url('main1', 'image.res?name=newsIcons/{{ ctrl.selectedIcon }}', true)}" alt="{{ icon }}" />
				<span ng-hide="ctrl.isIconSelected()">None Selected</span>
				<br />
				<div ng-click="ctrl.openIconPopup()">Select</div>
			</div>
			
			<input type="submit" name="submit" value="Submit" />
		</form>
	</div>
	
	<div class="bottom"></div>
</div>

<#include "../../inc/footer.ftl" />