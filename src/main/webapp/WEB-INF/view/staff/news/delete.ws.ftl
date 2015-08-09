<#assign showSessionBar=true />
<#assign cssImports = [ "staff/news" ] />
<#include "../../inc/header.ftl" />

<div class="titleFrame">
	<h1>Delete News Article</h1>
	<@a mod="staff" dest="index.ws" secure=true>Staff Center</@a>
</div>

<div class="scroll">
	<div class="top"></div>
	
	<div class="content">
		<h2>Delete News Article</h2>
		
		<hr />
		
		<#if deleteArticle??>
			<#if attemptingDelete>
				<#if deleteSuccessful>
					<p>Your news article has been successfully deleted.</p>
					<p><@a mod="staff" dest="index.ws" secure=true>Click here to return to the Staff Center index.</@a>
				<#else>
					<p>An error has occurred while attempting to delete the specified news article.</p>
				</#if>
			<#else>
				<p>Are you sure you wish to delete the article (${deleteArticle.title?html})? This action can never be undone.</p>
				
				<form method="post" action="${url('staff', 'news/delete.ws?id=${deleteArticle.id}', true)}">
					<input type="submit" name="confirm" value="Confirm" />
					&nbsp;
					<input type="submit" name="cancel" value="Cancel" />
				</form>
			</#if>
		<#else>
			<p>The article you were looking for was not found.</p>
			<p><@a mod="staff" dest="index.ws" secure=true>Click here to return to the Staff Center index.</@a></p>
		</#if>
	</div>
	
	<div class="bottom"></div>
</div>

<#include "../../inc/footer.ftl" />