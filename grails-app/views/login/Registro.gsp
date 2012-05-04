<%@ page import="mx.com.robertoleon.anelisse.utils.Captcha" contentType="text/html;charset=ISO-8859-1" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
 <r:require modules="bootstrap"/>
<title>Registro de usuario Anelisse</title>

</head>
<body>
<r:layoutResources/>


<nav class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">

            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>

            <a class="brand" href="${createLink(uri: '/')}">Roberto León </a>


        </div>
    </div>
</nav>
<br>
<br>
<br>
  <div id='loginRegistro' class="inicioContenido" align="center">
  				<g:if test='${flash.message}'>
					<div class='login_message'>${flash.message}</div>
				</g:if>
				<g:hasErrors bean="${usuarioInstance}">
				    <div class="errors">
						<g:renderErrors bean="${usuarioInstance}" as="list" />
					</div>
				</g:hasErrors>
				<g:if test="${request.messageError}">
					<div class="errors">
						<ul><li>${request.messageError}</li></ul>
					</div>
				</g:if>
				<g:if test="${request.messageErrorCaptcha}">
					<div class="errors">
						<ul><li>${request.messageErrorCaptcha}</li></ul>
					</div>
				</g:if>

			<!--<g:formRemote id='loginForm' name="Name" url="[controller:'login',action:'save']"
									update="[update:'superBody',failure:'error']" autocomplete='off'>-->
      <form action="save" method='POST' id='loginForm'>
				<fieldset id="datosLogin">

					<br>
					<table>
						<tr>
					        <td align="right"><label><a class="requerido">*</a>Nombre:</label></td>
					        <td class="value ${hasErrors(bean: usuarioInstance, field: 'nombre', 'errors')}"><g:textField id='nombre' name="nombre" value="${usuarioInstance?.nombre}"/></td>
					        <td></td>
				        </tr>
						<tr>
					        <td align="right"><label><a class="requerido">*</a>Apellido paterno:</label></td>
					        <td class="value ${hasErrors(bean: usuarioInstance, field: 'apellidoPaterno', 'errors')}"><g:textField id='apellidoPaterno' name="apellidoPaterno" value="${usuarioInstance?.apellidoPaterno}"/></td>
					        <td></td>
				        </tr>
						<tr>
					        <td align="right"><label><a class="requerido">*</a>Apellido materno:</label></td>
					        <td class="value ${hasErrors(bean: usuarioInstance, field: 'apellidoMaterno', 'errors')}"><g:textField id='apellidoMaterno' name="apellidoMaterno" value="${usuarioInstance?.apellidoMaterno}"/></td>
					        <td></td>
				        </tr>
						<tr>
					        <td align="right"><label><a class="requerido">*</a>Puesto:</label></td>
					        <td class="value ${hasErrors(bean: usuarioInstance, field: 'puesto', 'errors')}"><g:textField id='puesto' name="puesto" value="${usuarioInstance?.puesto}"/></td>
					        <td></td>
				        </tr>
						<tr>
					        <td align="right"><label><a class="requerido">*</a>Correo electr&oacute;nico:</label></td>
					        <td class="value ${hasErrors(bean: usuarioInstance, field: 'username', 'errors')}"><g:textField id='username' name="username" value="${usuarioInstance?.username}"/></td>
					        <td></td>
				        </tr>
						<tr>
					        <td align="right"><label>Tel&eacute;fono:</label></td>
					        <td class="value ${hasErrors(bean: usuarioInstance, field: 'telefono', 'errors')}"><g:textField id='telefono' name="telefono" value="${usuarioInstance?.telefono}"/></td>
					        <td></td>
				        </tr>
						<tr>
					        <td align="right"><label><a class="requerido">*</a>Introduzca los datos de la imagen:</label></td>
					        <td  class="value ${captcha?: 'errors'}"><g:textField id='captcha' name="captcha" value=""/></td>
					        <td></td>
				        </tr>
					</table>
					<br>
					<div id="captchaImg" style="font-size: 50px; font-family:serif; text-align: center;">
						<%=session.captcha%>
					</div>
					<br>
                    <br>
                    <br>
					<table>
						<tr>
					        <td width="100px">
						        <input class="sinBorde" id="submit" type="image" src="${resource(dir:'images',file:'user1_add.png')}" alt="Aceptar" border="0"  />
					        </td>

					        <td>
					        	<a href="${createLink(uri: '/')}">
					        		<img id="btnCancelar" src="${resource(dir:'images',file:'user_business_close.png')}" alt="Cancelar" border="0" />
					        	</a>
					        </td>
				        </tr>
					</table>
				</fieldset>
      </form>
			<!--</g:formRemote>-->

  </div>
</body>
</html>