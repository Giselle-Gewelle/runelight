<#assign showSessionBar=true />
<#assign cssImports = [ "staff/accounts" ] />
<#include "../../../inc/header.ftl" />

<div class="titleFrame">
	<h1>Ticket Details</h1>
	<@a mod="staff" dest="index.ws" secure=true>Staff Center</@a> - <@a mod="staff" dest="accounts/tickets/queue.ws" secure=true>Ticket Queue</@a>
</div>

<div id="ticketDetails" class="scroll">
	<div class="top"></div>
	
	<div class="content">
		<h2>Ticket Details</h2>
		
		<hr />
		
		<#if ticket??>
			<@a mod="staff" dest="accounts/tickets/reply.ws?id=${ticket.id}" secure=true>Reply to Ticket</@a>
			&nbsp;-&nbsp;
			<@a mod="staff" dest="accounts/tickets/delete.ws?id=${ticket.id}" secure=true>Remove from Queue</@a>
				
			<hr />
			
			<h3>Author</h2>
			<@a mod="staff" dest="accounts/details.ws?accountId=${ticket.authorId}" secure=true>${ticket.authorName}</@a>
			
			<h3>Subject</h3>
			${ticket.title}
			
			<h3>Message</h3>
			${replaceNewLines(ticket.message?html)}
			
			<br /><br />
		<#else>
			<#include "notfound.ftl" />
		</#if>
	</div>
	
	<div class="bottom"></div>
</div>

<#include "../../../inc/footer.ftl" />