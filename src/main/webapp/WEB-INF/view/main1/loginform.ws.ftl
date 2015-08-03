<#assign cssImports = [ 'account/loginform' ] />
<#include "../inc/header.ftl" />

<div class="titleFrame">
	<h1>Secure Login</h1>
	<@a mod="main1" dest="title.ws">Main Menu</@a>
</div>

<br />

<div class="frame">
	<p class="center"><strong class="orange">You need to log in to access this feature.</strong></p>
	<p class="center">Never enter your <strong>${gameName}</strong> password anywhere except <strong>${formattedHostName}</strong></p>
	
	<form action="${url(toSubdomain?html, 'login.ws', true)}" method="post" autocomplete="off">
		<input type="hidden" name="dest" value="${toFullDest?html}" />
		
		<table>
			<tbody>
				<tr>
					<td><label for="username">${gameName} Username:</label></td>
					<td><input type="text" id="username" name="username" maxlength="12" value="" /></td>
				</tr>
				
				<tr>
					<td><label for="password">${gameName} Password:</label></td>
					<td><input type="password" id="password" name="password" maxlength="20" value="" /></td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" name="submit" value="Secure Login" />
				</tr>
			</tbody>
		</table>
	</form>
	
	<div id="boxContainer">
		<div class="box">
			<h3>Need an account?</h3>
			To create an account to access our game and secure services,
			<@a mod="create" dest="index.html" secure=true>Click Here</@a>
		</div>
		
		<div class="spacer"></div>
		
		<div class="box">
			<h3>Lost password?</h3>
			If you have lost/forgotten your password or need to recover your account,
			<@a mod="password" dest="passsupport.html" secure=true>Click Here</@a>
		</div>
	</div>
	
	<div class="clear"></div>
</div>

<#include "../inc/footer.ftl" />