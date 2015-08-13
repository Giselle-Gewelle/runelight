<#assign showSessionBar=true />
<#assign cssImports = [ "staff/accounts" ] />
<#assign jsImports = [ "lib/jquery-2.1.4.min", "lib/angular.min", "staff/messaging" ] />

<#assign angular = 'ng-app="MessagingApp" ng-controller="MessagingCtrl as ctrl"' />

<#macro script>
	var receiverName = "${account.formattedUsername}";
	var username = "${loginSession.user.formattedUsername}";
	var company = "${companyName}";
	var game = "${gameName}";
</#macro>

<#include "../../inc/header.ftl" />

<div class="titleFrame">
	<h1>Send Message</h1>
	<@a mod="staff" dest="index.ws" secure=true>Staff Center</@a> - <@a mod="staff" dest="accounts/list.ws" secure=true>Account List</@a>
</div>

<div id="userDetails" class="scroll">
	<div class="top"></div>
	
	<div class="content">
		<#if account??>
			<h2>Send Message to User</h2>
			
			<hr />
			
			<p><strong>User:</strong> <@a mod="staff" dest="accounts/details.ws?accountId=${account.accountId}" secure=true>${account.formattedUsername}</@a></p>
			
			<hr />
			
			<form id="messageForm">
				<div class="formSection">
					<label for="inputTitle">Title:</label>
					<input type="text" id="inputTitle" name="inputTitle" maxlength="50" value="" />
				</div>
				
				<div class="formSection">
					<label for="inputAutoReply">Quick Messages:</label>
					<select id="inputAutoReply" name="inputAutoReply" ng-model="ctrl.quickMessage" 
							ng-options="item.title for item in ctrl.quickMessages" 
							ng-change="ctrl.selectQuickMessage()">
						<option value=""></option>
					</select>
				</div>
				
				<div class="formSection">
					<label for="inputMessage">Message:</label>
					<textarea id="inputMessage" name="inputMessage" maxlength="50000" 
						ng-model="ctrl.message"></textarea>
				</div>
				
				<div class="formSection">
					<label for="inputCanReply">User can reply:</label><br />
					<input type="checkbox" id="inputCanReply" name="inputCanReply" checked="checked" value="yes" />
				</div>
				
				<div class="formSection">
					<input type="submit" name="submit" value="Send Message" />
				</div>
			</form>
		<#else>
			<#include "error/notfound.ftl" />
		</#if>
	</div>
	
	<div class="bottom"></div>
</div>

<#include "../../inc/footer.ftl" />