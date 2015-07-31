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
	</head>
	
	<body>
		<div id="content">