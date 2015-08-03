<#assign showSessionBar=true />
<#assign cssImports = [ "staff/accounts" ] />
<#include "../../inc/header.ftl" />

<div class="titleFrame">
	<h1>User Accounts</h1>
	<@a mod="staff" dest="index.ws" secure=true>Staff Center</@a>
</div>

<div class="scroll">
	<div class="top"></div>
	
	<div class="content">
		<!--
		Username search here
		+ button/link for IP search (which can be far more extensive)
		-->
		
		<#if accountList??>
			<p>Page ${accountList.currentPage} of ${accountList.pageCount}</p>
		<#else>
			<p><strong>No user accounts were found with the specified username.</strong></p>
		</#if>
	</div>
	
	<div class="bottom"></div>
</div>

<#include "../../inc/footer.ftl" />