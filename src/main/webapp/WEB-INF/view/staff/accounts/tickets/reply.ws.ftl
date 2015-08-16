<#assign showSessionBar=true />
<#assign cssImports = [ "staff/accounts" ] />
<#assign jsImports = [ "lib/jquery-2.1.4.min", "lib/angular.min", "staff/ticketing", "Charlimiter" ] />

<#assign angular = 'ng-app="TicketingApp" ng-controller="TicketingCtrl as ctrl"' />

<#macro script>
	<#if ticket??>
		var receiverName = "${ticket.authorName}";
		var username = "${loginSession.user.formattedUsername}";
		var company = "${companyName}";
		var game = "${gameName}";
	</#if>
</#macro>

<#include "../../../inc/header.ftl" />

<div class="titleFrame">
	<h1>Reply to Ticket</h1>
	<@a mod="staff" dest="index.ws" secure=true>Staff Center</@a> - <@a mod="staff" dest="accounts/tickets/queue.ws" secure=true>Ticket Queue</@a>
	<#if ticket??>
		 - <@a mod="staff" dest="accounts/tickets/details.ws?id=${ticket.id}">Ticket Details</@a>
	</#if>
</div>

<div id="ticketDetails" class="scroll">
	<div class="top"></div>
	
	<div class="content">
		<h2>Reply to Ticket</h2>
		
		<hr />
		
		<#if ticket??>
			<#if submitted??>
				<#if successful>
					<p>Your reply has been successfully sent to <strong>${ticket.authorName}</strong>.</p>
					<p><@a mod="staff" dest="accounts/tickets/queue.ws" secure=true>Click here to return to the ticket queue.</@a></p>
				<#else>
					<p>An error has occurred while attempting to send your reply.</p>
					<p>Please <@a mod="staff" dest="accounts/tickets/reply.ws?id=${ticket.id}" secure=true>go back</@a> and try again.</p>
				</#if>
			<#else>
				<div ng-show="ctrl.error">
					<p>Please fill out the <strong>Message</strong> field.</p>
					
					<hr />
				</div>
				
				<form id="messageForm" method="post" action="${url('staff', 'accounts/tickets/reply.ws', true)}">
					<input type="hidden" name="id" value="${ticket.id}" />
					
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
				
				<button name="submit" ng-click="ctrl.submit()">Send Reply</button>
				&nbsp;
				<button name="cancel" onclick="window.location='${url('staff', 'accounts/tickets/details.ws?id=${ticket.id}', true)}'">Cancel</button>
				
				<hr />
				
				<h3>Author</h3>
				<@a mod="staff" dest="accounts/details.ws?accountId=${ticket.authorId}" secure=true>${ticket.authorName}</@a>
				
				<h3>Message</h3>
				${replaceNewLines(ticket.message?html)}
				
				<br /><br />
			</#if>
		<#else>
			<#include "notfound.ftl" />
		</#if>
	</div>
	
	<div class="bottom"></div>
</div>

<#include "../../../inc/footer.ftl" />