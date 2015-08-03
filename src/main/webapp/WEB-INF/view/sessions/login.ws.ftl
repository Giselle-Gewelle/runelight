<#include "../inc/header.ftl" />

<div class="titleFrame">
	<h1>Login Attempt</h1>
</div>

<br />

<div class="frame center">
	<#if error??>
		<#assign errorMessages = [
			"Sorry, there was an error trying to log you in to this part of the website.",
			"You must enter a valid username to proceed.",
			"The information you entered is incorrect."
		] />
		
		<p>${errorMessages[error]}</p>
		
		<#if requestedMod?? && requestedDest??>
			<p>Please <@a mod="main1" dest="loginform.ws?mod=${requestedMod?html}&amp;dest=${requestedDest?html}" secure=true>try again</@a>.</p>
		<#else>
			<p>Please <@a mod="main1" dest="title.ws">return to the main menu</@a>.</p>
		</#if>
	<#else>
		<p>Redirecting, please wait...</p>
	</#if>
</div>

<#include "../inc/footer.ftl" />