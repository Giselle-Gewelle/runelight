<#assign showSessionBar=true />
<#assign cssImports = [ "account/ticketing" ] />
<#include "../inc/header.ftl" />

<#if thread??>
	<div id="header">
		<div class="titleFrame">
			<h1>Viewing Message Exchange</h1>
			<@a mod="ticketing" dest="inbox.ws" secure=true>Back to Message Center</@a>
		</div>
	</div>
	
	<div class="center">
		<p>${thread.title?html}</p>
	</div>
	
	<#list thread.messageList as message>
		<#assign sealType = "player" />
		<#if message.authorStaff>
			<#assign sealType = "mod" />
		</#if>
		
		<div class="message">
			<span class="userDetails">
				${message.authorName}
				<img src="${url('main1', 'img/account/ticketing/${sealType}.png', true)}" alt="" />
			</span> 
			
			<span class="messageDetails">
				<span>${replaceNewLines(message.message?html)}</span>
			</span>
			
			<span class="dateDetails">
				${message.date}
				<img src="${url('main1', 'img/account/ticketing/${message.read?string("broken_", "")}seal_${sealType}.png', true)}" 
					alt="${message.read?string('Read', 'Not Read')}"
					title="${message.read?string('Read', 'Not Read')}" />
			</span>
		</div>
	</#list>
	
	<div class="center">
		<#if thread.canReply>
			<p><@a mod="ticketing" dest="inbox.ws?replyId=${thread.mainMessageId}" secure=true>Send Reply</@a></p>
		</#if>
		
		<p><@a mod="ticketing" dest="inbox.ws" secure=true>Back to Message Center</@a></p>
		<p><i>Messages sent to you by ${companyName} staff are strictly confidential 
			and are intended for the owner of the account to which they are sent. The 
			contents of these messages must not be disclosed to any other person or 
			copies taken.</i></p>
	</div>
<#else>
	<div id="header">
		<div class="titleFrame">
			<h1>Your Message Center</h1>
		</div>
	</div>
	
	<#macro queueItem item>
		<div class="item">
			<span class="title">${item.title?html}</span>
			<span class="messages">${item.messageNum}</span>
			<span class="date">${item.date}</span>
			<span class="actions">
				<@a mod="ticketing" dest="inbox.ws?viewId=${item.messageId}" secure=true><img src="${url('main1', 'img/account/ticketing/view.png', true)}" alt="View" title="View" /></@a>
				<@a mod="ticketing" dest="inbox.ws?deleteId=${item.messageId}" secure=true><img src="${url('main1', 'img/account/ticketing/delete.png', true)}" alt="Delete" title="Delete" /></@a>
			</span>
		</div>
	</#macro>
	
	<#macro noMessages><div class="noMessages">No conversations in this section.</div></#macro>
	
	<div class="center">
		<p><@a mod="ticketing" dest="inbox.ws" secure=true><img src="${url('main1', 'img/layout/refresh.png', true)}" alt="" />Refresh</@a></p>
	</div>
	
	<div class="section">
		<img class="header" src="${url('main1', 'img/account/ticketing/received.png', true)}" alt="" />
		
		<#if receivedList??>
			<#list receivedList as item>
				<@queueItem item />
			</#list>
		<#else>
			<@noMessages />
		</#if>
	</div>
	
	<div class="section">
		<img class="header" src="${url('main1', 'img/account/ticketing/sent.png', true)}" alt="" />
		
		<#if sentList??>
			<#list sentList as item>
				<@queueItem item />
			</#list>
		<#else>
			<@noMessages />
		</#if>
	</div>
	
	<div class="section">
		<img class="header" src="${url('main1', 'img/account/ticketing/read.png', true)}" alt="" />
		
		<#if readList??>
			<#list readList as item>
				<@queueItem item />
			</#list>
		<#else>
			<@noMessages />
		</#if>
	</div>
	
	<br />
</#if>

<#include "../inc/footer.ftl" />