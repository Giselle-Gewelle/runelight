<#include "global.ftl" />
<html lang="en-US">
	<head>
		<meta charset="UTF-8" />
		
		<title>${gameName}</title>
		
		<link rel="stylesheet" type="text/css" href="${url('main1', 'css/layout.css?rt=${rsTime}', securePage??)}" />
		
		<#if script??>
			<script type="text/javascript">
				<@script />
			</script>
		</#if>
		
		<#if cssImports??>
			<#list cssImports as cssImport>
				<link rel="stylesheet" type="text/css" href="${url('main1', 'css/${cssImport}.css?rt=${rsTime}', securePage??)}" />
			</#list>
		</#if>
		
		<#if jsImports??>
			<#list jsImports as jsImport>
				<script type="text/javascript" src="${url('main1', 'js/${jsImport}.js?rt=${rsTime}', securePage??)}"></script>
			</#list>
		</#if>
		
		<link rel="icon" type="image/x-icon" href="${url('main1', 'favicon.ico')}" />
		<link rel="shortcut icon" type="image/x-icon" href="${url('main1', 'favicon.ico')}" />
	</head>
	
	<body<#if angular??> ${angular}</#if>>
		<#if prepend??>
			<@prepend />
		</#if>
		
		<div id="content">
			<#if showSessionBar??>
				<div class="frame">
					<#if loginSession.loggedIn>
						<div class="floatRight">
							<@a mod="main1" dest="title.ws">Main Menu</@a> | 
							
							<#if loginSession.user.staff>
								<@a mod="ticketing" dest="inbox.ws" secure=true>Inbox (${loginSession.user.unreadMessages})</@a> | 
								<@a mod="staff" dest="accounts/tickets/queue.ws" secure=true>Support Tickets (${loginSession.user.supportQueries})</@a> | 
							</#if>
							
							<@a mod=currentMod dest="logout.ws" secure=true>Logout</@a>
						</div>
						
						<div>
							Logged in as <span class="orange">${loginSession.user.formattedUsername}</span>
						</div>
					<#else>
						<div class="floatRight">
							<@a mod="main1" dest="title.ws">Main Menu</@a> | 
							<@a mod="main1" dest="loginform.ws?mod=${currentMod?html}&amp;dest=${currentDest?html}${currentQuery?html}" secure=true>Login</@a>
						</div>
						
						<div>
							You are not logged in.
						</div>
					</#if>
				</div>
				
				<br />
			</#if>