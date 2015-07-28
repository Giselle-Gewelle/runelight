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
	<img src="${url('main1', 'img/main/title/logos/rslogo3.gif')}" alt="${gameName}" />
	
	<br />
	
	There are currently 0 people playing!
</div>

<div id="left">
	<@navBox title=gameName>
		<@navItem mod="create" dest="index.html" secure=true icon="create">Create a free account (New user)</@navItem>
		<@navItem mod="main1" dest="detail.ws" icon="play">Play ${gameName} (Existing user)</@navItem>
		<@navItem mod="main1" dest="screenshots/screenshots.ws" icon="screenshot">View in-game screenshots</@navItem>
	</@navBox>
</div>

<div id="right">
	
</div>

<#include "../inc/footer.ftl" />