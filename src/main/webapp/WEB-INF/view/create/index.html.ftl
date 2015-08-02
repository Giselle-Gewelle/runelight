<#assign cssImports = [ 'account/create' ] />
<#include "../inc/header.ftl" />
<#include "local.ftl" />

<@progressBar activeStage="0" />

<div class="frame center">
    <br />

    <form method="post" action="${url('create', 'chooseagerange.ws', true)}" autocomplete="off">
        <span>Please indicate your age:</span>
        <br /><br />

        <select name="age">
            <option value="-1">Select one</option>
            <option value="-2">----------------</option>
            
            <#list ageRangeMap?keys as key>
            	<option value="${key}">${ageRangeMap[key]}</option>
            </#list>
        </select><br /><br />

        <span>Please select your permanent country of residence:</span>
        <br /><br />

        <select name="country">
            <option value="-1">Select one</option>
            <option value="-2">----------------</option>
            
            <#list countryMap?keys as key>
            	<option value="${key}">${countryMap[key]}</option>
            </#list>
        </select><br /><br />

        <input type="submit" name="submit" value="Submit" />
    </form>

    <br />
</div>

<#include "../inc/footer.ftl" />