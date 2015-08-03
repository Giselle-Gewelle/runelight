<#assign showSessionBar=true />
<#assign cssImports = [ "staff/index" ] />
<#include "../inc/header.ftl" />

<div class="titleFrame">
	<h1>${gameName} Staff Center</h1>
</div>

<div class="scroll">
	<div class="top"></div>
	
	<div class="content">
		<div id="staffArea">
			<a href="${url('staff', 'accounts/list.ws', true)}" class="box">
				<span class="bg"></span>
				
				<span class="inner">
					<span>User Accounts</span>
					<span>View and alter all registered accounts.</span>
				</span>
			</a>
			
			<a href="${url('staff', 'news/article.ws', true)}" class="box">
				<span class="bg"></span>
				
				<span class="inner">
					<span>Post News Article</span>
					<span>Publish a news article to the main page.</span>
				</span>
			</a>
		</div>
	</div>
	
	<div class="bottom"></div>
</div>

<#include "../inc/footer.ftl" />