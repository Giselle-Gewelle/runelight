<#assign showSessionBar=true />
<#assign cssImports = [ "staff/accounts" ] />
<#include "../../inc/header.ftl" />

<div class="titleFrame">
	<h1>User Account Details</h1>
	<@a mod="staff" dest="index.ws" secure=true>Staff Center</@a> - <@a mod="staff" dest="accounts/list.ws" secure=true>Account List</@a>
</div>

<div class="scroll">
	<div class="top"></div>
	
	<div class="content">
		
	</div>
	
	<div class="bottom"></div>
</div>

<#include "../../inc/footer.ftl" />