<#assign cssImports = [ 'main/kbase' ] />
<#include "../../inc/header.ftl" />

<div class="titleFrame">
	<h1>Knowledge Base</h1>
	<@a mod="main1" dest="title.ws">Main Menu</@a>
</div>

<div class="scroll">
	<div class="top"></div>
	
	<div class="content">
		<div class="location">
			<span>Location:</span>
			
			<ul>
				<li><@a mod="main1" dest="title.ws">Home</@a> &gt; Give us feedback
			</ul>
		</div>
		
		<p><strong>This page is temporary and will be replaced once the Knowledge Base system is added.</strong></p>
		
		<hr />
		
		<h2>Give us feedback</h2>
		Thank you for taking the time to comment on our service. We are keen to provide the best player support possible and as such have provided 
		numerous ways in which players can supply us with feedback. To ensure that all of the player feedback is utilized in the best way, we have 
		separated feedback into sections. Please read the following carefully.
		<br /><br />
		
		<#--<strong>Adverts</strong><br /><br />
		For information regarding our adverts and advertisers, please see the Knowledge Base category "Advertising" available <a>here</a>.
		<br /><br />-->
		
		<#--<strong>Game Suggestions</strong><br /><br />
		Forum stuff here
		<br /><br />-->
		
		<strong>Privacy Concerns</strong><br /><br />
		If you have any concerns about the way we handle your personal information, please take the time to read our <@a mod="main1" dest="privacy/privacy.ws">Privacy Policy</@a>.
		<br /><br />
		If this does not answer your question then you can contact us by sending in a <@a mod="ticketing" dest="privacy.html" secure=true>Privacy Concern Message</@a>.
		<br /><br />
		
		<strong>Complaint</strong><br /><br />
		If you have any genuine complaints regarding your account or the level of service you have received please click <@a mod="ticketing" dest="complaint.html" secure=true>here</@a>.
		<br /><br />
		
		<strong>Other Comments</strong><br /><br />
		If the above does not cover the type of feedback that you wish to provide, click <@a mod="ticketing" dest="other.html" secure=true>here</@a>.
		<br /><br />
	</div>
	
	<div class="bottom"></div>
</div>

<#include "../../inc/footer.ftl" />