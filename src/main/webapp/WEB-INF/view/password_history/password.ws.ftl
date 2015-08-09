<#assign showSessionBar=true />
<#assign cssImports = [ "account/changepass" ] />
<#include "../inc/header.ftl" />

<div class="titleFrame">
	<h1>Password Change Form</h1>
</div>

<br />

<div class="frame">
	<#if errorCode??>
		<#assign errorMessages = [
			"Please enter your new password in <strong>both</strong> boxes in the form below as well as your current password in the first box. This is to ensure you do not make a mistake in typing it in.",
			"The new passwords you entered <strong>did not match</strong>. Please make sure you carefully enter your password in both boxes below so that they match exactly.",
			"The password you entered was of an invalid length. Passwords must be between <strong>5 and 20 characters</strong> in length.",
			"The password you entered contained an invalid character. Passwords can only contain the letters <strong>A to Z</strong> and the numbers <strong>0 to 9</strong>.",
			"The current password you entered did not match against our records. Please check you entered it correctly and try again.",
			"The password you entered is the same as your current password. Please make sure the new password you choose is <strong>different</strong> to your current password, otherwise we will be unable to update it.",
			"The password you have requested is a fragment of your username or your username reversed. This is not recommended as it is easy to guess what the password is. Please choose another password.",
			"An error has occurred, please try again."
		] />
		
		<p><strong>There was a problem with your submission, please try again.</strong></p>
		<p>${errorMessages[errorCode]}</p>
		
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
	<#else>
		<p><strong>Your request has been accepted.</strong></p>
		<p>Your new password has been set. You may now use it to log into your account. Please be sure 
			to <strong>never</strong> disclose your new password to <strong>anyone</strong>. ${companyName} 
			staff will <strong>never</strong> ask you for your password or any account details.</p>
	</#if>
</div>

<#include "../inc/footer.ftl" />