<#include "global.ftl" />
<html lang="en-US">
	<head>
		<meta charset="UTF-8" />
		
		<title>${gameName}</title>
		
		<link rel="stylesheet" type="text/css" href="${url('main1', 'css/layout.css?rt=${rsTime}', securePage??)}" />
		
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
	
	<body>
		<div id="content">
			<#if showSessionBar??>
				<div class="frame">
					<#if loginSession.loggedIn>
						<div class="floatRight">
							<@a mod="main1" dest="title.ws">Main Menu</@a> | 
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