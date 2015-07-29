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
			<div id="newsContainer">
				<img id="newsHeader" src="${url('main1', 'img/main/title/recentNews.png')}" alt="Recent News" />
				
				<div class="newsItem">
					<div class="header">
						<div class="floatRight">02-Jul-2015</div>
						
						<div>This is a placeholder title</div>
					</div>
					
					<img src="" alt="" />
					
					<div class="description">
						Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus sodales nunc, at mattis metus tempus id. 
						Nulla eget laoreet ligula. Duis et est suscipit, finibus orci sit amet, lobortis lacus. Fusce porta dictum lacus sit amet aliquam. 
						Duis suscipit tincidunt vestibulum. Sed eleifend mattis ullamcorper. Nam vel lorem nulla. 
						Fusce quis diam faucibus, tempus magna non, aliquam nisi. Suspendisse dictum leo id ante pretium aliquam. 
						Vivamus eleifend augue at ex ornare scelerisque. Suspendisse iaculis tempus accumsan. 
					</div>
					
					<div class="more">
						<a href="">Read more...</a>
					</div>
				</div>
				
				<div class="newsItem">
					<div class="header">
						<div class="floatRight">01-Jul-2015</div>
						
						<div>This is a placeholder title</div>
					</div>
					
					<img src="" alt="" />
					
					<div class="description">
						Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus sodales nunc, at mattis metus tempus id. 
						Nulla eget laoreet ligula. Duis et est suscipit, finibus orci sit amet, lobortis lacus. Fusce porta dictum lacus sit amet aliquam. 
						Duis suscipit tincidunt vestibulum. Sed eleifend mattis ullamcorper. Nam vel lorem nulla. 
					</div>
					
					<div class="more">
						<a href="">Read more...</a>
					</div>
				</div>
				
				<div class="newsItem">
					<div class="header">
						<div class="floatRight">02-Jul-2015</div>
						
						<div>This is a placeholder title</div>
					</div>
					
					<img src="" alt="" />
					
					<div class="description">
						Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus sodales nunc, at mattis metus tempus id. 
						Nulla eget laoreet ligula. Duis et est suscipit, finibus orci sit amet, lobortis lacus. Fusce porta dictum lacus sit amet aliquam. 
						Duis suscipit tincidunt vestibulum. Sed eleifend mattis ullamcorper. Nam vel lorem nulla. 
						Fusce quis diam faucibus, tempus magna non, aliquam nisi. Suspendisse dictum leo id ante pretium aliquam. 
						Vivamus eleifend augue at ex ornare scelerisque. Suspendisse iaculis tempus accumsan. 
					</div>
					
					<div class="more">
						<a href="">Read more...</a>
					</div>
				</div>
			</div>
		</div>
		
		<div class="bottom"></div>
	</div>
</div>

<#include "../inc/footer.ftl" />