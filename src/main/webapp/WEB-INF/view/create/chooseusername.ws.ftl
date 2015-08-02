<#assign cssImports = [ 'account/create' ] />
<#include "../inc/header.ftl" />
<#include "local.ftl" />

<#if usernameError??>
	<#assign usernameErrors = [
		"Please input a valid username.",
		"Usernames can be a maximum of 12 characters long and may contain letters, numbers and underscores.<br /><br />Please enter a different username:",
		"Sorry, that username is no longer available.",
		"An error has occurred.<br /><br />Please try again:"
	] />
	
	<@progressBar activeStage="1" />
	
	<div class="frame center">
	    <form method="post" action="${url('create', 'chooseusername.ws', true)}" autocomplete="off">
			<input type="hidden" name="age" value="${createData.ageRange?html}" />
			<input type="hidden" name="country" value="${createData.countryCode?html}" />
			
			<div id="usernameForm">
				${usernameErrors[usernameError]}<br /><br />
				<input type="text" name="username" maxlength="12" value="" /><br /><br />
				<input type="submit" name="submit" value="Check Availability" />
			</div>
		</form>
	</div>
<#else>
	<@progressBar activeStage="2" />
	
	<div class="frame">
		<br /><br />
		
		The username <span class="orange">${createData.formattedUsername?html}</span> is currently available. 
		To sign up for an account you must agree to our Terms and Conditions:<br /><br />
		
		<#include "../inc/legal/terms.ftl" />
		
		<form method="post" action="${url('create', 'choosepassword.ws', true)}" autocomplete="off">
			<input type="hidden" name="age" value="${createData.ageRange?html}" />
			<input type="hidden" name="country" value="${createData.countryCode?html}" />
			<input type="hidden" name="username" value="${createData.username?html}" />
			
			<div id="termsForm">
				<button type="submit" name="agree_terms" value="on">I Agree</button>
				&nbsp;&nbsp;&nbsp;
				<button type="submit" name="agree_terms" value="off">I Do Not Agree</button>
			</div>
		</form>
	</div>
</#if>

<#include "../inc/footer.ftl" />