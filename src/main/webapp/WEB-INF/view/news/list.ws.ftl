<#assign cssImports = [ 'media/news' ] />
<#assign jsImports = [ 'media/news' ] />
<#include "../inc/header.ftl" />

<#macro nav name>
	<#if (currentPage > 1)>
		<a href="${url('news', 'list.ws?cat=${categoryId}&amp;page=1')}"><img class="arrow" title="First" alt="First" src="${url('main1', 'img/media/news/first.png')}" /></a>
		&nbsp;<a href="${url('news', 'list.ws?cat=${categoryId}&amp;page=${(currentPage - 1)}')}"><img class="arrow" title="Prev" alt="Prev" src="${url('main1', 'img/media/news/prev.png')}" /></a>
	<#else>
		<img class="doubleArrow" alt="" src="${url('main1', 'img/layout/blank.png')}" />
		&nbsp;
	</#if>
	
	<form name="${name}" action="${url('news', 'list.ws')}" method="post">
		<input type="hidden" name="pageCount" value="${pageCount}" />
		<input type="hidden" name="currentPage" value="${currentPage}" />
		<input type="hidden" name="cat" value="${categoryId}" />
		page <input type="text" name="page" size="2" maxlength="4" value="${currentPage}" onchange="updatePage('${name}')" />&nbsp;of ${pageCount}
	</form>
	
	<#if (currentPage < pageCount)>
		<a href="${url('news', 'list.ws?cat=${categoryId}&amp;page=${(currentPage + 1)}')}"><img class="arrow" title="Next" alt="Next" src="${url('main1', 'img/media/news/next.png')}" /></a>
		&nbsp;<a href="${url('news', 'list.ws?cat=${categoryId}&amp;page=${pageCount}')}"><img class="arrow" title="Last" alt="Last" src="${url('main1', 'img/media/news/last.png')}" /></a>
	<#else>
		&nbsp;
		<img class="doubleArrow" alt="" src="${url('main1', 'img/layout/blank.png')}" />
	</#if>
</#macro>

<div id="header">
	<div class="titleFrame">
		<h1><#if newsListTitle??>${newsListTitle}<#else>Latest</#if> News</h1>
		
		<a href="${url('main1', 'title.ws')}">Main Menu</a> - <a href="${url('news', 'list.ws')}">News List</a>
	</div>
</div>

<div class="scroll">
	<div class="top"></div>
	
	<div class="content">
		<div id="categoryList">
			<img src="${url('main1', 'img/media/news/all_categories.png')}" alt="" />&nbsp;<a href="${url('news', 'list.ws?cat=0')}">All Categories</a> - 
			<img src="${url('main1', 'img/media/news/game_updates.png')}" alt="" />&nbsp;<a href="${url('news', 'list.ws?cat=1')}">Game Updates</a> - 
			<img src="${url('main1', 'img/media/news/website.png')}" alt="" />&nbsp;<a href="${url('news', 'list.ws?cat=2')}">Website</a><br />
			<img src="${url('main1', 'img/media/news/customer_support.png')}" alt="" />&nbsp;<a href="${url('news', 'list.ws?cat=3')}">Customer Support</a> - 
			<img src="${url('main1', 'img/media/news/technical.png')}" alt="" />&nbsp;<a href="${url('news', 'list.ws?cat=4')}">Technical</a> - 
			<img src="${url('main1', 'img/media/news/behind_the_scenes.png')}" alt="" />&nbsp;<a href="${url('news', 'list.ws?cat=6')}">Behind the Scenes</a>
		</div>
		
		<br />
		
		<#if newsList??>
			<div class="topNav">
				<@nav name="topForm" />
			</div>
			
			<table>
				<thead>
					<tr>
						<td class="category">Category</td>
						<td class="title">News Item</td>
						<td class="date">Date</td>
					</tr>
				</thead>
				
				<tbody>
					<#list newsList as newsItem>
						<tr>
							<td class="category"><img src="${url('main1', 'img/media/news/${newsItem.category.iconName}.png')}" alt="" /> ${newsItem.category.name1}</td>
							<td class="title"><a href="${url('news', 'newsitem.ws?id=${newsItem.id}')}">${newsItem.title?html}</a></td>
							<td class="date">${newsItem.date}</td>
						</tr>
					</#list>
				</tbody>
			</table>
			
			<div class="bottomNav">
				<@nav name="bottomForm" />
			</div>
		<#else>
			<p id="noNews">There are currently no news items in this category.</p>
		</#if>
	</div>
	
	<div class="bottom"></div>
</div>

<#include "../inc/footer.ftl" />