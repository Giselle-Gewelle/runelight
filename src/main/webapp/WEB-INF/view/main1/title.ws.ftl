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
</div>

<div id="right">
	<div id="buttons">
		<a class="button" href="${url('create', 'index.html', true)}"><span>CREATE A<br />FREE ACCOUNT<br />(NEW USER)</span></a>
		<a class="button" href="${url('main1', 'detail.ws')}"><span>PLAY<br />${gameName?upper_case}<br />(EXISTING USER)</span></a>
	</div>
	
	<div class="scroll">
		<div class="top"></div>
		
		<div class="content">
			<br /><br /><br /><br /><br />
			<br /><br /><br /><br /><br />
			<br /><br /><br /><br /><br />
		</div>
		
		<div class="bottom"></div>
	</div>
</div>

<#include "../inc/footer.ftl" />