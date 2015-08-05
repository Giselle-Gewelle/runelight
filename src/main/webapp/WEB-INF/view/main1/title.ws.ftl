<#assign cssImports = [ 'main/title' ] />
<#include "../inc/header.ftl" />

<#macro navBox title>
	<fieldset>
		<legend>${title}</legend>
		
		<ul>
			<#nested />
		</ul>
	</fieldset>
</#macro>

<#macro navItem mod dest icon="" secure=false>
	<#if icon != ""><li style="list-style-image: url(${url('main1', 'img/main/title/menus/bullets/${icon}.png')});"><#else><li></#if><a href="${url(mod, dest, secure)}"><#nested /></a></li>
</#macro>

<div id="header">
	<img src="${url('main1', 'img/main/title/logos/default1.png')}" alt="${gameName}" />
	
	<br />
	
	There are currently 0 people playing!
</div>

<div id="left">
	<@navBox title=gameName>
		<@navItem mod="create" dest="index.html" secure=true icon="create">Create a free account (New user)</@navItem>
		<@navItem mod="main1" dest="detail.ws" icon="play">Play ${gameName} (Existing user)</@navItem>
		<@navItem mod="main1" dest="screenshots/screenshots.ws" icon="screenshot">View in-game screenshots</@navItem>
	</@navBox>
	
	<@navBox title="Account Management">
		<@navItem mod="password_history" dest="passchange.html" secure=true icon="changepass">Change your password</@navItem>
	</@navBox>
</div>

<div id="right">
	<div id="buttons">
		<a class="button" href="${url('create', 'index.html', true)}"><span>CREATE A<br />FREE ACCOUNT<br />(NEW USER)</span></a>
		<a class="button" href="${url('main1', 'detail.ws')}"><span>PLAY<br />${gameName?upper_case}<br />(EXISTING USER)</span></a>
	</div>
	
	<div class="scroll">
		<div class="top"></div>
		
		<div class="content">
			<div id="newsContainer">
				<img id="newsHeader" src="${url('main1', 'img/main/title/recentNews.png')}" alt="Recent News" />
				
				<#if newsList??>
					<#list newsList as newsItem>
						<div class="newsItem">
							<div class="header">
								<div class="floatRight">${newsItem.date}</div>
								
								<div>${newsItem.title}</div>
							</div>
							
							<img src="${url('main1', 'image.res?name=newsIcons/${newsItem.iconName}')}" alt="[icon]" />
							
							<div class="description">
								${newsItem.description}
							</div>
							
							<div class="more">
								<a href="${url('news', 'newsitem.ws?id=${newsItem.id}')}">Read more...</a>
							</div>
						</div>
					</#list>
				<#else>
					<p>An error has occurred while attempting to load the news feed, please <a href="title.ws">reload the page</a> to try again.</p>
				</#if>
			</div>
			
			<div id="newsList">
				<a href="${url('news', 'list.ws')}">Browse the news archives</a>
			</div>
		</div>
		
		<div class="bottom"></div>
	</div>
</div>

<#include "../inc/footer.ftl" />