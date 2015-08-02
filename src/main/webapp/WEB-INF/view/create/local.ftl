<#assign stageNames = {
	"0": "Indicate Age and Location",
	"1": "Choose a Username",
	"2": "Terms and Conditions", 
	"3": "Choose a Password", 
	"4": "Finish"
}>

<#macro stage name active complete>
	<#if active>
		<span class="stage active">${name}</span><#t />
	<#elseif complete>
		<span class="stage completed">${name}</span><#t />
	<#else>
		<span class="stage">${name}</span><#t />
	</#if>
</#macro>

<#macro progressBar activeStage="-1">
	<div id="progress"><#t />
		<#list stageNames?keys as key>
			<@stage name=stageNames[key] active=(activeStage != "-1" && activeStage == key) complete=(activeStage != "-1" && activeStage?number > key?number) />
		</#list>
	</div><#lt />
</#macro>

<div class="titleFrame">
	<h1>Create Account</h1>
	<a href="${url('main1', 'title.ws')}">Main Menu</a>
</div>

<br />