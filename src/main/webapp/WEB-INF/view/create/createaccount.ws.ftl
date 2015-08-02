<#assign cssImports = [ 'account/create' ] />
<#include "../inc/header.ftl" />
<#include "local.ftl" />

<#if passwordError??>
	<#assign passwordErrors = [
		"Please enter a valid password.",
		"The password you entered was of an invalid length. Passwords must be between <strong>5 and 20 characters</strong> in length.",
		"The values in the password and password confirmation boxes are not the same. Please re-enter them.",
		"The password you entered contained an invalid character. Passwords can only contain the letters <strong>A to Z</strong> and the numbers <strong>0 to 9</strong>.",
		"The password you have requested is a fragment of your username or your username reversed. This is not recommended as it is easy to guess what the password is. Please choose another password.",
		"You must agree with the Privacy Policy in order to create an account.",
		"An error has occurred, please try again."
	] />
	
	<@progressBar activeStage="3" />
	
	<div class="frame">
	    <p class="center red">${passwordErrors[passwordError]}</p>
	    
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
<#else>
	<@progressBar activeStage="-1" />
	
	<div class="frame">
		<div id="creationComplete">
			Your account <span class="orange">${createData.formattedUsername?html}</span> has now been created with the password you have chosen. 
			We recommend you make a note of it on a bit of paper and keep it somewhere <strong>really</strong> safe, in case you forget it.
			<br />
			<#--<br />
			We have many guides and manuals on the ${gameName} website that you will find extremely helpful. The following are some of the 
			most important that you will need to be aware of:
			<ul>
				<li><@a mod="kbase" dest="viewcategory.ws?ref=create&amp;cat_id=823">Read our Rules</@a></li>
				<li><@a mod="kbase" dest="viewcategory.ws?ref=create&amp;cat_id=827">Read out Safety + Security guidelines</@a></li>
				<li><@a mod="kbase" dest="viewarticle.ws?ref=create&amp;article_id=2058">Getting started guide</@a></li>
			</ul>-->
			<br />
			<div class="center">Click below to start playing:</div><br />
			<a id="playButton" href="${url('main1', 'detail.ws')}"><span>Play<br />${gameName}<br />(Existing User)</span></a>
			<br />
		</div>
	</div>
</#if>

<#include "../inc/footer.ftl" />