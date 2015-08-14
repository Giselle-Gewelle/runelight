<#assign showSessionBar=true />
<#assign cssImports = [ "staff/accounts" ] />
<#include "../../inc/header.ftl" />

<div class="titleFrame">
	<h1>Open Support Tickets</h1>
	<@a mod="staff" dest="index.ws" secure=true>Staff Center</@a>
</div>

<div id="userDetails" class="scroll">
	<div class="top"></div>
	
	<div class="content">
		<h2>Open Support Tickets</h2>
		
		<hr />
		
		<#if ticketList??>
			<table id="ticketList">
				<thead>
					<tr>
						<td class="title">Title</td>
						<td class="author">Author</td>
						<td class="date">Date Received</td>
						<td>Actions</td>
					</tr>
				</thead>
				
				<tbody>
					<#list ticketList as ticket>
						<tr>
							<td class="title">${ticket.title}</td>
							<td class="author"><@a mod="staff" dest="accounts/details.ws?accountId=${ticket.authorId}" secure=true>${ticket.authorName}</@a></td>
							<td class="date">${ticket.date}</td>
							<td>
								<@a mod="staff" dest="accounts/ticket/details.ws?id=${ticket.id}" secure=true>View</@a> | 
								<@a mod="staff" dest="accounts/ticket/delete.ws?id=${ticket.id}" secure=true>Delete</@a>
							</td>
						</tr>
					</#list>
				</tbody>
			</table>
		<#else>
			<p>There are no open Support Tickets to view.</p>
			<p><@a mod="staff" dest="index.ws" secure=true>Staff Center Index</@a></p>
		</#if>
	</div>
	
	<div class="bottom"></div>
</div>

<#include "../../inc/footer.ftl" />