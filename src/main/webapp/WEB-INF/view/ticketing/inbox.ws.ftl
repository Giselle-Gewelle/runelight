<#assign showSessionBar=true />
<#assign cssImports = [ "account/ticketing" ] />
<#assign jsImports = [ "lib/jquery-2.1.4.min", "Charlimiter" ] />
<#include "../inc/header.ftl" />

<#if error??>
	<#include "inc/error.ftl" />
<#elseif thread??>
	<#include "inc/thread.ftl" />
<#elseif deleteId??>
	<#include "inc/delete.ftl" />
<#elseif replyId??>
	<#include "inc/reply.ftl" />
<#else>
	<div id="header">
		<div class="titleFrame">
			<h1>Your Message Center</h1>
		</div>
	</div>
	
	<#macro queueItem item>
		<div class="item">
			<span class="title">${item.title}</span>
			<span class="messages">${item.messageNum}</span>
			<span class="date">${item.date}</span>
			<span class="actions">
				<@a mod="ticketing" dest="inbox.ws?viewid=${item.messageId}" secure=true><img src="${url('main1', 'img/account/ticketing/view.png', true)}" alt="View" title="View" /></@a>
				<@a mod="ticketing" dest="inbox.ws?deleteid=${item.messageId}" secure=true><img src="${url('main1', 'img/account/ticketing/delete.png', true)}" alt="Delete" title="Delete" /></@a>
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