<#assign cssImports = [ 'account/create' ] />
<#include "../inc/header.ftl" />
<#include "local.ftl" />

<#if !ageError && !countryError>
	<@progressBar activeStage="1" />
	
	<div class="frame">
		<form method="post" action="${url('create', 'chooseusername.ws', true)}" autocomplete="off">
			<div id="usernameForm">
				Creating an account for ${gameName} is a very simple process.<br /><br />
				To begin you must first select a username. Once you have found a username that you like and is not already taken, you will be asked to choose a password.<br /><br />
				When you have done that, you are ready to play!<br /><br />
				Remember creating and account is totally free, you can keep playing on our free worlds for as long as you like with no obligation to buy anything.<br /><br />
				Usernames can be a maximum of 12 characters long and may contain letters, numbers and underscores.<br /><br />
				It should not contain your real name, birth date, or other personally identifiable information, to better protect your identity.<br /><br />
				It should not be offensive or break our <a href="${url('main1', 'terms/terms.ws')}">Terms+Conditions</a>.<br /><br />
				The Username you choose here is used as your Character name in the game. When playing ${gameName}, underscores in usernames are translated into 
					spaces and the first letters are capitalised. For example the username <b>red_rooster</b> would appear as <b>Red Rooster</b>.
				<br /><br />
				
				<table>
					<tbody>
						<tr>
							<td style="text-align: right; width: 46%;">Desired Username:</td>
							<td><input type="text" name="username" maxlength="12" value="" /></td>
						</tr>
						
						<tr>
							<td>&nbsp;</td>
							<td>
								<input type="hidden" name="age" value="${createData.ageRange?html}" />
								<input type="hidden" name="country" value="${createData.countryCode?html}" />
								<input type="submit" name="submit" value="Check Availability" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
	</div>
<#else>
	<@progressBar activeStage="0" />
	
	<div class="frame center">
	    <br />
	
	    <form method="post" action="${url('create', 'chooseagerange.ws', true)}" autocomplete="off">
	        <#if ageError><span class="red"><#else><span></#if>Please indicate your age:</span>
	        <br /><br />
	
	        <select name="age">
	            <option value="-1">Select one</option>
	            <option value="-2">----------------</option>
	            
	            <#list ageRangeMap?keys as key>
	            	<option value="${key}"<#if !ageError && createData.ageRange == key> selected="selected"</#if>>${ageRangeMap[key]}</option>
	            </#list>
	        </select><br /><br />
	
	        <#if countryError><span class="red"><#else><span></#if>Please select your permanent country of residence:</span>
	        <br /><br />
	
	        <select name="country">
	            <option value="-1">Select one</option>
	            <option value="-2">----------------</option>
	            
	            <#list countryMap?keys as key>
	            	<option value="${key}"<#if !countryError && createData.countryCode == key> selected="selected"</#if>>${countryMap[key]}</option>
	            </#list>
	        </select><br /><br />
	
	        <input type="submit" name="submit" value="Submit" />
	    </form>
	
	    <br />
	</div>
</#if>

<#include "../inc/footer.ftl" />