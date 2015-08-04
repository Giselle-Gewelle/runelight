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
			<div class="searchField">
				<label for="usernameSearch">Username:</label><br />
				<input type="text" name="usernameSearch" id="usernameSearch" maxlength="12" value="<#if accountList??>${accountList.usernameSearch?html}</#if>" /> 
			</div>
			
			<div class="searchField">
				<label for="ipSearch">IP:</label><br />
				<input type="text" name="ipSearch" id="ipSearch" maxlength="50" value="<#if accountList??>${accountList.ipSearch?html}</#if>" /> 
			</div>
			
			<div class="searchField">
				<input type="submit" name="submit" value="Search" />
			</div>
			
			<div class="searchField">
				<input type="submit" name="submit" value="Reset" onclick="window.location='${url("staff", "accounts/list.ws", true)}'; return false;" />
			</div>
		</form>
		
		<hr />
		
		<#if accountList??>
			<#macro pageUrl page=accountList.currentPage usernameSearch=accountList.usernameSearch ipSearch=accountList.ipSearch sort=accountList.sort sortDir=accountList.sortDir>${url('staff', 'accounts/list.ws?page=${page}&amp;usernameSearch=${usernameSearch}&amp;ipSearch=${ipSearch}&amp;sort=${sort}&amp;sortDir=${sortDir}', true)}</#macro>
			
			<#macro nav name>
				<#if (accountList.currentPage > 1)>
					<a href="<@pageUrl page=1 />"><img class="arrow" title="First" alt="First" src="${url('main1', 'img/media/news/first.png')}" /></a>
					&nbsp;<a href="<@pageUrl page=(accountList.currentPage - 1) />"><img class="arrow" title="Prev" alt="Prev" src="${url('main1', 'img/media/news/prev.png')}" /></a>
				<#else>
					<img class="doubleArrow" alt="" src="${url('main1', 'img/layout/blank.png')}" />
					&nbsp;
				</#if>
				&nbsp;
				<form class="nav" name="${name}" action="${url('staff', 'accounts/list.ws')}" method="post">
					<input type="hidden" name="pageCount" value="${accountList.pageCount}" />
					<input type="hidden" name="currentPage" value="${accountList.currentPage}" />
					<input type="hidden" name="usernameSearch" value="${accountList.usernameSearch?html}" />
					<input type="hidden" name="sort" value="${accountList.sort}" />
					<input type="hidden" name="sortDir" value="${accountList.sortDir}" />
					Page <input type="text" name="page" size="3" value="${accountList.currentPage}" onchange="updatePage('${name}')" /> of ${accountList.pageCount}
				</form>
				&nbsp;
				<#if (accountList.currentPage < accountList.pageCount)>
					<a href="<@pageUrl page=(accountList.currentPage + 1) />"><img class="arrow" title="Next" alt="Next" src="${url('main1', 'img/media/news/next.png')}" /></a>
					&nbsp;<a href="<@pageUrl page=accountList.pageCount />"><img class="arrow" title="Last" alt="Last" src="${url('main1', 'img/media/news/last.png')}" /></a>
				<#else>
					&nbsp;
					<img class="doubleArrow" alt="" src="${url('main1', 'img/layout/blank.png')}" />
				</#if>
			</#macro>
			
			<div class="center">
				<@nav name="topForm" />
			</div>
			
			<#macro sortableHeader value name>
				<td class="sort" onclick="window.location='<@pageUrl sort="${value}" sortDir=(accountList.sort == value)?then((accountList.sortDir == "ASC")?then("DESC", "ASC"), "DESC") />';">
					${name}<#if accountList.sort == value>
						<#if accountList.sortDir == "DESC">
							( v )
						<#else>
							( ^ )
						</#if>
					</#if>
				</td>
			</#macro>
			
			<table id="accountList">
				<thead>
					<tr>
						<@sortableHeader value="username" name="Username" />
						<@sortableHeader value="creationDate" name="Creation Date" />
						<@sortableHeader value="creationIP" name="Creation IP" />
						<@sortableHeader value="currentIP" name="Latest IP" />
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
							<td><@a mod="staff" dest="accounts/details.ws?accountId=${account.accountId}">Details</@a></td>
						</tr>
					</#list>
				</tbody>
			</table>
			
			<div class="center">
				<@nav name="bottomForm" />
			</div>
			
			<br />
		<#else>
			<p class="center"><strong>No user accounts were found with the specified parameters.</strong></p>
		</#if>
	</div>
	
	<div class="bottom"></div>
</div>

<#include "../../inc/footer.ftl" />