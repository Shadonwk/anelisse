
<html>
<head>
    <r:require modules="bootstrap"/>
    <gui:resources components="['dialog','toolTip']"/>

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


        </div>
    </div>
</nav>

<br>
<br>
<br>

<div class="container-fluid">
    <div class="row-fluid" >

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