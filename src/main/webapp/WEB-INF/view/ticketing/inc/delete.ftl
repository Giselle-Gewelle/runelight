<div id="header">
	<div class="titleFrame">
		<h1>Question</h1>
	</div>
</div>

<br />

<div class="frame center">
	<p>Are you sure you want to permanently delete all messages in this dialogue?</p>
	
	<form id="deleteForm" method="post" action="${url('ticketing', 'inbox.ws', true)}">
		<input type="hidden" name="deleteid" value="${deleteId}" />
		
		<input type="submit" name="yes" value="Yes" />
		&nbsp;
		<input type="submit" name="no" value="No" />
	</form>
</div>