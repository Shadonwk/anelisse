
<html>
<head>
    <r:require modules="bootstrap"/>
    <gui:resources components="['dialog','toolTip']"/>
    <title><g:layoutTitle/></title>
</head>
<body>

<nav class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">

            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>

            <a class="brand" href="${createLink(uri: '/')}">Roberto León </a>

            <div class="nav-collapse">
                <ul class="nav">
                    <!--<li<%= request.forwardURI == "${createLink(uri: '/')}" ? ' class="active"' : '' %>><a href="${createLink(uri: '/')}">Home</a></li>-->
                    <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                        <g:if test="${c.name !='Login'}">
                            <g:if test="${c.name =='Logout'}">
                                <sec:ifLoggedIn>
                                    <li<%= c.logicalPropertyName == controllerName ? ' class="active"' : '' %>><g:link controller="${c.logicalPropertyName}">${c.name}</g:link></li>
                                </sec:ifLoggedIn>
                            </g:if>
                            <g:else>
                                <li<%= c.logicalPropertyName == controllerName ? ' class="active"' : '' %>><g:link controller="${c.logicalPropertyName}">${c.name}</g:link></li>
                            </g:else>
                        </g:if>

                    </g:each>
                </ul>
            </div>


        </div>
    </div>
</nav>

<br>
<br>
<br>

<div class="container-fluid">
    <div class="row-fluid" >
        <div class="span2">
            <!--Sidebar content-->
            <br>
            <br>

            <sec:ifNotLoggedIn>
            <div id='login' align="left">
                <b>Inicio de sesión</b>
                <g:if test='${flash.message}'>
                    <div class='errors'>${flash.message}</div>
                </g:if>

                <form action='${postUrl}' method='POST' id='loginForm'   autocomplete='off'>
                    <!--<fieldset id="datosLogin" >-->
                        <table id="tableLogin" >
                            <tr>
                                <td><label><g:message code="mx.con.robertoleon.Algo" default="Nombre" />:</label></td>
                                <td class="value ${hasErrors(bean: miembroInstance, field: 'nombre', 'errors')}"><g:textField id='username' name="j_username" value="admin@robertoleon.com.mx" class="input-small" /></td>
                            </tr>
                            <tr>
                                <td><label><g:message code="mx.con.robertoleon.Algo"  default="Contrase&ntilde;a" /></label></td>
                                <td class="value ${hasErrors(bean: miembroInstance, field: 'empresa', 'errors')}"><g:passwordField id='password' name="j_password" value="admin" class="input-small"/></td>
                            </tr>
                        </table>
                        <g:link controller="login" action="registro"><g:message code="mx.con.robertoleon.Algo" default="Reg&iacute;strese aqu&iacute;" /></g:link>
                    <!--</fieldset>-->
                    <br>
                    <p>
                        <input type="image" id="submit" title="Aceptar"  alt="Iniciar" src="${resource(dir:'images', file:'login.png')}" />
                    </p>
                </form>
            </div>
            </sec:ifNotLoggedIn>

            <br>
            <br>
            <br>
            <div>
                <b>Post más recientes</b>
                <br>
                Aqui apareceran los post mas recientes...
            </div>

            <br>
            <br>
            <br>
            <div>
                <b>Categorias</b>
                Aqui apareceran todas las categorias del blog...
            </div>

            <br>
            <br>
            <br>
            <div>
                <b>Contenido popular</b>
                Aqui aparecera el contenido con mayor numero de votos de todo el tiempo...
            </div>

            <script type='text/javascript'>
                <!--
                (function() {
                    document.forms['loginForm'].elements['j_username'].focus();
                })();
                // -->
            </script>


        </div>
        <div class="span10">
            <!--Body content-->
            <g:layoutBody />


        </div>
    </div>
</div>
<hr>
<footer>
    <p align="center">&copy; Company 2012</p>
</footer>

<r:layoutResources/>

</body>
</html>