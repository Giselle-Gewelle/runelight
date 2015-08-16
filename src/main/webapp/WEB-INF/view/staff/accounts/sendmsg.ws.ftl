<#assign showSessionBar=true />
<#assign cssImports = [ "staff/accounts" ] />
<#assign jsImports = [ "lib/jquery-2.1.4.min", "lib/angular.min", "staff/messaging", "Charlimiter" ] />

<#assign angular = 'ng-app="MessagingApp" ng-controller="MessagingCtrl as ctrl"' />

<#if account??>
	<#macro script>
		var receiverName = "${account.formattedUsername}";
		var username = "${loginSession.user.formattedUsername}";
		var company = "${companyName}";
		var game = "${gameName}";
	</#macro>
</#if>

<#include "../../inc/header.ftl" />

<div class="titleFrame">
	<h1>Send Message</h1>
	<@a mod="staff" dest="index.ws" secure=true>Staff Center</@a> - <@a mod="staff" dest="accounts/list.ws" secure=true>Account List</@a>
</div>

<div id="userDetails" class="scroll">
	<div class="top"></div>
	
	<div class="content">
		<#if account??>
			<#if submitted??>
				<#if successful>
					<h2>Message Sent</h2>
					
					<hr />
					
					<p>Your message has been successfully sent to <strong>${account.formattedUsername}</strong>.</p>
					<p><@a mod="staff" dest="accounts/details.ws?accountId=${account.accountId}" secure=true>Click here to return to the user details page.</@a></p>
				<#else>
					<h2>An Error has Occurred</h2>
					
					<hr />
					
					<p>An error has occurred while attempting to send your message.</p>
					<p>Please <@a mod="staff" dest="accounts/sendmsg.ws?user=${account.accountId}" secure=true>go back</@a> and try again.</p>
				</#if>
			<#else>
				<h2>Send Message to User</h2>
				
				<hr />
				
				<p><strong>User:</strong> <@a mod="staff" dest="accounts/details.ws?accountId=${account.accountId}" secure=true>${account.formattedUsername}</@a></p>
				
				<hr />
				
				<div ng-show="ctrl.error">
					<p>Please fill out both the <strong>Title</strong> and the <strong>Message</strong> fields.</p>
					
					<hr />
				</div>
				
				<form id="messageForm" method="post" action="${url('staff', 'accounts/sendmsg.ws', true)}">
					<input type="hidden" name="user" value="${account.accountId}" />
					
					<div class="formSection">
						<label for="inputTitle">Title:</label>
						<input type="text" id="inputTitle" name="inputTitle" maxlength="50" value="" ng-model="ctrl.title" />
						<div id="inputTitleCharlimiter"></div>
						<input type="hidden" id="inputTitleChars" value="50" />
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
						<div id="inputMessageCharlimiter"></div>
						<input type="hidden" id="inputMessageChars" value="50000" />
					</div>
					
					<div class="formSection">
						<label for="inputCanReply">User can reply:</label><br />
						<input type="checkbox" id="inputCanReply" name="inputCanReply" checked="checked" value="yes" />
					</div>
				</form>
				
				<button name="submit" ng-click="ctrl.submit()">Send Message</button>
				
				<br /><br />
	
				<script type="text/javascript">
					new Charlimiter("inputTitle");
				</script>
			</#if>
		<#else>
			<#include "error/notfound.ftl" />
		</#if>
	</div>
	
	<div class="bottom"></div>
</div>

<#include "../../inc/footer.ftl" />