<%@ page contentType="text/html;charset=ISO-8859-1" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="layout" content="inicio"/>
<title>Registro exitoso!</title>
<gui:resources components="['dialog','toolTip']"/>
    </head>
    <body>
		<div id='loginRegistro' align="center">
			<h1>REGISTRO EXITOSO</h1>
			<table  style="width: 500px;">	
				<tr>	
					<td>Su registro ha sido exitoso, el administrador del sistema se pondr&aacute; en contacto con usted.</td>
		        </tr>
			</table>
			<a href="${createLink(uri: '/')}">
		        		<img id="btnRegresar" src="${resource(dir:'images',file:'Icono_ok1.png')}" alt="Regresar" border="0" />
		    </a>
		</div>
    </body>
</html>