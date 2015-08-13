<div id="header">
	<div class="titleFrame">
		<h1>Viewing Message Exchange</h1>
		<@a mod="ticketing" dest="inbox.ws" secure=true>Back to Message Center</@a>
	</div>
</div>

<div id="threadTitle">
	<p>${thread.title}</p>
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
			<span class="inner">
				<#if message.includeTitleInMsg>
					${message.title}<br /><br />
				</#if>
				
				<#if message.authorStaff>
					${replaceNewLines(message.message)}
				<#else>
					${replaceNewLines(message.message?html)}
				</#if>
			</span>
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
		<p><@a mod="ticketing" dest="inbox.ws?replyid=${thread.mainMessageId}" secure=true>Send Reply</@a></p>
	</#if>
	
	<p><@a mod="ticketing" dest="inbox.ws" secure=true>Back to Message Center</@a></p>
	<p><i>Messages sent to you by ${companyName} staff are strictly confidential 
		and are intended for the owner of the account to which they are sent. The 
		contents of these messages must not be disclosed to any other person or 
		copies taken.</i></p>
</div>