<!DOCTYPE html>

<#function url mod dest secure=false>
	<#if mod == "main1">
		<#local mod = "www" />
	</#if>
	
	<#if secure && sslEnabled>
		<#local protocol = "https" />
	<#else>
		<#local protocol = "http" />
	</#if>
	
	<#return "${protocol}://${mod?replace('_', '-')}.${hostName}/${dest}" />
</#function>

<#function replaceNewLines string>
	<#return string?replace("\n", "<br />") />
</#function>

<#macro a mod dest secure=false><a href="${url(mod, dest, secure)}"><#nested /></a></#macro>