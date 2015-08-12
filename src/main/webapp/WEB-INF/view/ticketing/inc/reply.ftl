<#if sentTo??>
	<#if loginSession.user.staff>
		<div id="header">
			<div class="titleFrame">
				<h1>Reply Sent</h1>
			</div>
		</div>
		
		<br />
		
		<div class="frame center">
			<p>Your reply has been successfully sent to <span class="orange">${sentTo}</span>.</p>
			<p>Please click <@a mod="ticketing" dest="inbox.ws" secure=true>here</@a> to continue.</p>
		</div>
	<#else>
		<div id="header">
			<div class="titleFrame">
				<h1>Thank You!</h1>
			</div>
		</div>
		
		<br />
		
		<div class="frame center">
			<p>Your message has been successfully received.</p>
			<p>If appropriate, you will receive a reply from our Customer Support staff via your Message Center as soon as possible.</p>
			<p>Please click <@a mod="ticketing" dest="inbox.ws" secure=true>here</@a> to continue.</p>
		</div>
	</#if>
<#else>
	<div id="header">
		<div class="titleFrame">
			<h1>Send a Message</h1>
			<@a mod="ticketing" dest="inbox.ws" secure=true>Back to Message Center</@a>
		</div>
	</div>
	
	<#if loginSession.user.staff>
		<div class="center"><p>${currentTitle?html}</p></div>
	<#else>
		<br />
	</#if>
	
	<div class="frame center">
		<#assign errorMessages = [
			"Please enter a message.",
			"Sorry, but the message you have sent is too long. Please ensure your message length is ${loginSession.user.staff?string('50000', '1000')} characters or less.",
			"An unknown error has occurred, please try again."
		] />
		
		<form id="replyForm" method="post" action="${url('ticketing', 'inbox.ws', true)}">
			<input type="hidden" name="replyid" value="${replyId}" />
			
			<label for="formMessage">Message:</label><br />
			
			<#if errorCode??>
				<span class="orange">${errorMessages[errorCode]}</span><br />
			</#if>
			
			<#if loginSession.user.staff>
				<input type="hidden" id="inputMessageChars" value="50000" />
				
				<textarea id="inputMessage" name="inputMessage" maxlength="50000"></textarea>
				<div id="formMessageCharlimiter"></div>
				
				<input type="checkbox" id="inputCanReply" name="inputCanReply" checked="checked" value="yes" /> 
				<label for="inputCanReply">Recipient <strong>CAN</strong> reply</label>
				
				<br /><br />
			<#else>
				<input type="hidden" id="inputMessageChars" value="1000" />
				
				<textarea id="inputMessage" name="inputMessage" maxlength="1000"></textarea>
				<div id="inputMessageCharlimiter"></div>
				<br />
			</#if>
			
			<input type="submit" name="submit" value="Send" />
		</form>
	</div>
</#if>

<script type="text/javascript">
	new Charlimiter("inputMessage");
</script>