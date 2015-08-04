<#assign showSessionBar=true />
<#assign cssImports = [ "staff/accounts" ] />
<#assign jsImports = [ 'media/news' ] />
<#include "../../inc/header.ftl" />

<div class="titleFrame">
	<h1>User Accounts</h1>
	<@a mod="staff" dest="index.ws" secure=true>Staff Center</@a>
</div>

<div class="scroll">
	<div class="top"></div>
	
	<div class="content">
		<h4>Search</h4>
		
		<form name="searchForm" action="${url('staff', 'accounts/list.ws')}" method="post">
			<label for="usernameSearch">Username:</label>
			<input type="text" name="usernameSearch" id="usernameSearch" maxlength="12" value="<#if accountList??>${accountList.usernameSearch?html}</#if>" /> 
			<input type="submit" name="submit" value="Go" />
		</form>
		
		<hr />
		
		<#if accountList??>
			<#macro nav name>
				<#if (accountList.currentPage > 1)>
					<a href="${url('staff', 'accounts/list.ws?page=1&amp;usernameSearch=${accountList.usernameSearch?html}')}"><img class="arrow" title="First" alt="First" src="${url('main1', 'img/media/news/first.png')}" /></a>
					&nbsp;<a href="${url('staff', 'accounts/list.ws?page=${(accountList.currentPage - 1)}&amp;usernameSearch=${accountList.usernameSearch?html}')}"><img class="arrow" title="Prev" alt="Prev" src="${url('main1', 'img/media/news/prev.png')}" /></a>
				<#else>
					<img class="doubleArrow" alt="" src="${url('main1', 'img/layout/blank.png')}" />
					&nbsp;
				</#if>
				&nbsp;
				<form class="nav" name="${name}" action="${url('staff', 'accounts/list.ws')}" method="post">
					<input type="hidden" name="pageCount" value="${accountList.pageCount}" />
					<input type="hidden" name="currentPage" value="${accountList.currentPage}" />
					<input type="hidden" name="usernameSearch" value="${accountList.usernameSearch?html}" />
					Page <input type="text" name="page" size="3" value="${accountList.currentPage}" onchange="updatePage('${name}')" /> of ${accountList.pageCount}
				</form>
				&nbsp;
				<#if (accountList.currentPage < accountList.pageCount)>
					<a href="${url('staff', 'accounts/list.ws?page=${(accountList.currentPage + 1)}&amp;usernameSearch=${accountList.usernameSearch?html}')}"><img class="arrow" title="Next" alt="Next" src="${url('main1', 'img/media/news/next.png')}" /></a>
					&nbsp;<a href="${url('staff', 'accounts/list.ws?page=${accountList.pageCount}&amp;usernameSearch=${accountList.usernameSearch?html}')}"><img class="arrow" title="Last" alt="Last" src="${url('main1', 'img/media/news/last.png')}" /></a>
				<#else>
					&nbsp;
					<img class="doubleArrow" alt="" src="${url('main1', 'img/layout/blank.png')}" />
				</#if>
			</#macro>
			
			<div class="center">
				<@nav name="topForm" />
			</div>
			
			<table id="accountList">
				<thead>
					<tr>
						<td>Username</td>
						<td>Creation Date</td>
						<td>Creation IP</td>
						<td>Latest IP</td>
						<td>Actions</td>
					</tr>
				</thead>
				
				<tbody>
					<#list accountList.accountList as account>
						<tr>
							<td>${account.formattedUsername}</td>
							<td>${account.creationDate}</td>
							<td>${account.creationIP}</td>
							<td>${account.currentIP}</td>
							<td><@a mod="staff" dest="accounts/details.ws?id=${account.accountId}">Details</@a></td>
						</tr>
					</#list>
				</tbody>
			</table>
			
			<div class="center">
				<@nav name="bottomForm" />
			</div>
			
			<br />
		<#else>
			<p class="center"><strong>No user accounts were found with the specified username.</strong></p>
		</#if>
	</div>
	
	<div class="bottom"></div>
</div>

<#include "../../inc/footer.ftl" />