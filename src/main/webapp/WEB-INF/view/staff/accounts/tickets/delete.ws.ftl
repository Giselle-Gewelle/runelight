<#assign showSessionBar=true />
<#assign cssImports = [ "staff/accounts" ] />

<#include "../../../inc/header.ftl" />

<div class="titleFrame">
	<h1>Delete Ticket</h1>
	<@a mod="staff" dest="index.ws" secure=true>Staff Center</@a> - <@a mod="staff" dest="accounts/tickets/queue.ws" secure=true>Ticket Queue</@a>
	<#if ticket??>
		 - <@a mod="staff" dest="accounts/tickets/details.ws?id=${ticket.id}">Ticket Details</@a>
	</#if>
</div>

<div id="ticketDetails" class="scroll">
	<div class="top"></div>
	
	<div class="content">
		<h2>Delete Ticket</h2>
		
		<hr />
		
		<#if ticket??>
			<#if error??>
				<p>An error has occurred while attempting to delete the specified ticket.</p>
				<p>Please <@a mod="staff" dest="accounts/tickets/delete.ws?id=${ticket.id}" secure=true>go back</@a> and try again.</p>
			<#else>
				<p><strong>Are you sure you wish to delete this ticket? This action can not be reversed.</strong></p>
				
				<form id="messageForm" method="post" action="${url('staff', 'accounts/tickets/delete.ws', true)}">
					<input type="hidden" name="id" value="${ticket.id}" />
					
					<input type="submit" name="submit" value="Delete" />
					&nbsp;
					<input type="submit" name="cancel" value="Cancel" />
				</form>
				
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