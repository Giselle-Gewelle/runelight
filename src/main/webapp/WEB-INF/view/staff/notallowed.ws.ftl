<#assign showSessionBar=true />
<#include "../inc/header.ftl" />

<div class="titleFrame">
	<h1>Not Allowed</h1>
	<@a mod="main1" dest="title.ws">Main Menu</@a>
</div>

<div class="scroll">
	<div class="top"></div>
	
	<div class="content center">
		<p><strong>You do not have authorization to view this page.</strong></p>
		
		<p><@a mod="main1" dest="title.ws">Click here to return to the main menu.</@a>
	</div>
	
	<div class="bottom"></div>
</div>

<#include "../inc/footer.ftl" />