<!DOCTYPE html>

<#function url mod dest secure=false>
	<#if mod == "main1">
		<#local mod = "www" />
	</#if>
	
	<#if secure>
		<#local protocol = "https" />
	<#else>
		<#local protocol = "http" />
	</#if>
	
	<#return "${protocol}://${mod?replace('_', '-')}.${hostName}/${dest}" />
</#function>