<#assign showSessionBar=true />
<#assign cssImports = [ "account/feedback" ] />
<#assign jsImports = [ "lib/jquery-2.1.4.min", "Charlimiter" ] />
<#include "../../inc/header.ftl" />

<div class="titleFrame">
	<h1>Feedback Support</h1>
</div>

<br />

<div class="frame center">
	<#if submitted??>
		<#if errorCode??>
			<#assign errorMessages = [
				"You must fill out the <strong>comment</strong> field.",
				"You have gone over the max character count.",
				"You may only submit a Customer Support query once every 5 minutes.",
				"An error has occurred."
			] />
			
			<p>${errorMessages[errorCode]}</p>
			<p><@a mod="ticketing" dest=feedbackLink secure=true>Click here to go back.</@a></p>
		<#else>
			<p>Your feedback has been sent.</p>
			<p><@a mod="main1" dest="title.ws">Click here to return to the main page.</@a></p>
		</#if>
	<#else>
		<p><strong>Feedback Support</strong></p>
		<p><strong>Subject:</strong> ${feedbackSubject}</p>
		<p>
			<strong>Your comment:</strong><br />
			<span id="inputProblemCharlimiter"></span>
		</p>
		
		<form method="post" action="${url('ticketing', feedbackLink, true)}" autocomplete="off">
			<input type="hidden" id="inputProblemChars" value="400" />
			<textarea id="inputProblem" name="inputProblem" maxlength="400"></textarea><br /><br />
			<input type="submit" name="submit" value="Submit Message" />
		</form>
		
		<br />
	</#if>
</div>

<script type="text/javascript">
	new Charlimiter("inputProblem");
</script>

<#include "../../inc/footer.ftl" />