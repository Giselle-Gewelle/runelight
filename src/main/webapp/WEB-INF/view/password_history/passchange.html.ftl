<#assign showSessionBar=true />
<#assign cssImports = [ "account/changepass" ] />
<#include "../inc/header.ftl" />

<div class="titleFrame">
	<h1>Password Change Form</h1>
</div>

<br />

<div class="frame">
	<p>Use this form if you want to change the password you use for logging this account into ${gameName} and our website. If you do not wish to set a new password at this time, click the 
		logout link at the top right of this page.</p>
	<p>Please be absolutely sure that you only ever enter or change your password at <strong>${formattedHostName}</strong></p>
	<p>Please note that passwords must be between <strong>5 and 20</strong> characters in length. We recommend that you use a mixture of numbers and letters in your password to make it 
		harder for someone to guess.</p>
	
	<form action="${url('password_history', 'password.ws', true)}" method="post" autocomplete="off">
		<table>
			<tbody>
				<tr>
					<td><label for="current">Current Password:</label></td>
					<td><input type="password" id="current" name="current" maxlength="20" value="" /> 
				</tr>
				
				<tr>
					<td><label for="password1">New Password:</label></td>
					<td><input type="password" id="password1" name="password1" maxlength="20" value="" /></td>
				</tr>
				
				<tr>
					<td><label for="password2">Confirm New Password:</label></td>
					<td><input type="password" id="password2" name="password2" maxlength="20" value="" /></td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" name="submit" value="Submit" /></td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

<#include "../inc/footer.ftl" />