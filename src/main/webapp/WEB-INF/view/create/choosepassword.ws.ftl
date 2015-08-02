<#assign cssImports = [ 'account/create' ] />
<#include "../inc/header.ftl" />
<#include "local.ftl" />

<#if termsError??>
	<@progressBar activeStage="2" />
	
	<div class="frame">
	    <br /><br />
	    
	    <span class="red">You must agree with the Terms and Conditions in order to create an account.</span><br /><br />
	    
	    If you do not agree with the Terms and Conditions below, please <a href="${url('main1', 'title.ws')}">click here</a> to return to the main menu.<br /><br />
	    
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
<#else>
	<@progressBar activeStage="3" />
	
	<div class="frame">
		<ul>
			<li><b>NEVER</b> give anyone your password, not even to ${companyName} staff.</li>
			<li>${companyName} staff will never ask you for your password.</li>
			<li>Passwords must be between 5 and 20 characters long. We recommend you use a mixture of numbers and letters in your password to make it harder for someone to guess.</li>
		</ul>
		
		<form method="post" action="${url('create', 'createaccount.ws', true)}" autocomplete="off">
			<input type="hidden" name="age" value="${createData.ageRange?html}" />
			<input type="hidden" name="country" value="${createData.countryCode?html}" />
			<input type="hidden" name="username" value="${createData.username?html}" />
			<input type="hidden" name="agree_terms" value="on" />
			
			<table id="passwordTable">
				<tbody>
					<tr>
						<td class="right" style="width: 60%;">Desired Username:</td>
						<td>${createData.formattedUsername?html} 
							(<a href="${url('create', 'chooseagerange.ws?age=${createData.ageRange?html}&amp;country=${createData.countryCode?html}', true)}">Change</a>)</td>
					</tr>
					
					<tr>
						<td class="right">Desired Password:</td>
						<td><input type="password" name="password1" value="" maxlength="20" /></td>
					</tr>
					
					<tr>
						<td class="right">Confirm Password:</td>
						<td><input type="password" name="password2" value="" maxlength="20" /></td>
					</tr>
					
					<tr>
						<td class="right">I have read and agree with the <a href="${url('main1', 'privacy/privacy.ws')}">Privacy Policy</a>:</td>
						<td><input type="checkbox" name="agree_privacy" value="on" /></td>
					</tr>
					
					<tr>
						<td>&nbsp;</td>
						<td>
							<input type="submit" name="submit" value="Create Account" />
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</#if>

<#include "../inc/footer.ftl" />