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

<#macro navItem icon mod dest secure=false>
	<li><a href="${url(mod, dest, secure)}"><#nested /></a></li>
</#macro>

<div id="header">
	<img src="${url('main1', 'img/main/title/logos/rslogo3.gif')}" alt="${gameName}" />
	
	<br />
	
	There are currently 0 people playing!
</div>

<div id="left">
	<@navBox title=gameName>
		<@navItem icon="" mod="create" dest="index.html" secure=true>Create a free account (New user)</@navItem>
		<@navItem icon="" mod="main1" dest="detail.ws">Play ${gameName} (Existing user)</@navItem>
		<@navItem icon="" mod="main1" dest="screenshots/screenshots.ws">View in-game screenshots</@navItem>
	</@navBox>
</div>

<div id="right">
	
</div>

<#include "../inc/footer.ftl" />