<#include "../inc/global.ftl" />
<#macro errorPage code name>
	<html lang="en-US">
		<head>
			<meta charset="UTF-8" />
			
			<title>${code} - ${name}</title>
			
			<style type="text/css">
				body {
					background-color: #FFFFFF;
					color: #000000;
					font-family: Arial, Verdana, Helvetica;
				}
				
				h1 {
					margin: 0;
					padding: 0;
					font-size: large;
					color: #666666;
					font-weight: bold;
				}
				
				hr {
					width: 300px;
					padding-left: 0;
					margin-left: 0;
				}
			</style>
		</head>
		
		<body>
			<h1>${code} - ${name}</h1>
			
			<hr />
			
			<p><#nested /></p>
		</body>
	</html>
</#macro>