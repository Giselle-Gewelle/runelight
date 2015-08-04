<#assign showSessionBar=true />
<#assign cssImports = [ "staff/accounts" ] />
<#include "../../inc/header.ftl" />

<div class="titleFrame">
	<h1>User Account Details</h1>
	<@a mod="staff" dest="index.ws" secure=true>Staff Center</@a> - <@a mod="staff" dest="accounts/list.ws" secure=true>Account List</@a>
</div>

<div id="userDetails" class="scroll">
	<div class="top"></div>
	
	<div class="content">
		<#if account??>
			<h4>#${account.accountId} - ${account.formattedUsername} (${account.username})</h4>
			
			<br />
			
			<div class="contentBox">
				<div class="header">
					Basic User Details
				</div>
				
				<div class="body">
					<table id="basicDetails">
						<tbody>
							<tr>
								<td>
									<strong>Age Range</strong><br />
									${account.ageRange}
								</td>
								
								<td>
									<strong>Country</strong><br />
									${account.country}
								</td>
								
								<td>
									<strong>Creation Date</strong><br />
									${account.creationDate}
								</td>
								
								<td>
									<strong>Creation IP</strong><br />
									${account.creationIP}
								</td>
								
								<td>
									<strong>Latest IP</strong><br />
									${account.currentIP}
								</td>
								
								<td>
									<strong>Last Login</strong><br />
									<#if account.lastLoginDate??>${account.lastLoginDate}<#else>Never</#if>
								</td>
							</tr>
							
							<tr class="lower">
								<td colspan="6">
									<strong>Rights</strong><br />
									<#if account.staff>
										Staff
									<#elseif account.pmod && account.fmod>
										Dual Mod
									<#elseif account.pmod>
										Player Mod
									<#elseif account.fmod>
										Forum Mod
									<#else>
										User
									</#if>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			<div class="contentBox">
				<div class="header">
					Recent Password Changes
				</div>
				
				<div class="body">
					
				</div>
			</div>
			
			<div class="contentBox">
				<div class="header">
					Recent Login Sessions
				</div>
				
				<div class="body">
					<#if account.sessionList??>
						<table class="full">
							<thead>
								<tr>
									<td>IP</td>
									<td>Start Date</td>
									<td>End Date</td>
									<td>Start Location</td>
									<td>Latest Location</td>
								</tr>
							</thead>
							
							<tbody>
								<#list account.sessionList as session>
									<tr>
										<td>${session.ip}</td>
										<td>${session.startDate}</td>
										<td>${session.endDate}</td>
										<td>${session.startMod} | ${session.startDest}</td>
										<td>${session.currentMod} | ${session.currentDest}</td>
									</tr>
								</#list>
							</tbody>
						</table>
						
						<hr />
						
						<a href="">View Full List</a>
					<#else>
						There are no recent sessions to display for this user.
					</#if>
				</div>
			</div>
			
			<div class="contentBox">
				<div class="header">
					Recent Login Attempts
				</div>
				
				<div class="body">
					
				</div>
			</div>
		<#else>
			<h4>User Not Found</h4>
			<p>The user account you were looking for was not found.</p>
			<p><@a mod="staff" dest="accounts/list.ws">Click here to return to the user account list.</@a></p>
		</#if>
	</div>
	
	<div class="bottom"></div>
</div>

<#include "../../inc/footer.ftl" />