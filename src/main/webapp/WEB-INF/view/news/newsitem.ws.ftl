<#assign cssImports = [ 'media/news' ] />
<#include "../inc/header.ftl" />

<div id="header">
	<div class="titleFrame">
		<h1>
			<#if newsItem??>
				${newsItem.category.name2} 
			</#if> 
			News
		</h1>
		
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
		
		<#if newsItem??>
			<div id="articleTitle">
				<#if (newsItem.prevId > 0)>
					<a href="${url('news', 'newsitem.ws?id=${newsItem.prevId}')}"><img class="arrow" title="Prev" alt="Prev" src="${url('main1', 'img/media/news/prev.png')}" /></a>
				<#else>
					<img class="arrow" alt="" src="${url('main1', 'img/layout/blank.png')}" />
				</#if>
				&nbsp;
				<span>${newsItem.date} - ${newsItem.title?html}</span>
				&nbsp;
				<#if (newsItem.nextId > 0)>
					<a href="${url('news', 'newsitem.ws?id=${newsItem.nextId}')}"><img class="arrow" title="Next" alt="Next" src="${url('main1', 'img/media/news/next.png')}" /></a>
				<#else>
					<img class="arrow" alt="" src="${url('main1', 'img/layout/blank.png')}" />
				</#if>
			</div>
			
			<div id="articleBody">
				<p>${newsItem.body}</p>
			</div>
			
			<div class="clear"></div>
			
			<#if loginSession.loggedIn>
				<#if loginSession.user.staff>
					<hr />
					
					<p><@a mod="staff" dest="news/article.ws?id=${newsItem.id}" secure=true>Edit Article</@a> - <@a mod="staff" dest="news/delete.ws?id=${newsItem.id}" secure=true>Delete Article</@a></p>
				</#if>
			</#if>
		<#else>
			<p id="articleNotFound">News Item Not Found</p>
		</#if>
	</div>
	
	<div class="bottom"></div>
</div>

<#include "../inc/footer.ftl" />