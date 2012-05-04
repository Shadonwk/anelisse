
<html>
<head>
    <r:require modules="bootstrap"/>
    <r:layoutResources/>

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
                    <li<%= request.forwardURI == "${createLink(uri: '/')}" ? ' class="active"' : '' %>><a href="${createLink(uri: '/')}">Home</a></li>
                    <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                        <li<%= c.logicalPropertyName == controllerName ? ' class="active"' : '' %>><g:link controller="${c.logicalPropertyName}">${c.name}</g:link></li>
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
    <div class="row-fluid">
        <div class="span2">
            <!--Sidebar content-->
            barra
            <br>
            barra1
            <br>
            barra2
            <br>
            barra3
            <br>
            barra4
            <br>

        </div>
        <div class="span10">
            <!--Body content-->
            <g:layoutBody />

        </div>
    </div>
</div>
<hr>
<footer>
    <p align="center">&copy; Company 2011</p>
</footer>

<r:layoutResources/>

</body>
</html>