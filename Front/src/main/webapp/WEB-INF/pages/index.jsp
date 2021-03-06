<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html ng-app="mainApp" ng-controller="indexController">
<head>
    <title>{{application.title}}</title>
    <meta charset="utf-8"/>
    <meta name="viewport content=" width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="/resources/bower_components/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/bower_components/bootstrap/dist/css/bootstrap-theme.min.css">

    <link href='http://fonts.googleapis.com/css?family=Lora&subset=latin,cyrillic' rel='stylesheet' type='text/css'>
    <link href="/resources/css/index.css" rel="stylesheet">
    <link href='http://fonts.googleapis.com/css?family=Lora:400,700&subset=latin,cyrillic' rel='stylesheet'
          type='text/css'>
    <link rel="stylesheet" href="/resources/bower_components/fontawesome/css/font-awesome.min.css">
</head>
<body>

<header class="header-basic">
    <nav class="navbar-inverse">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                    <div class="navbar-brand logo">
                        <a ui-sref="home">
                            <img class="logo2" src="/resources/img/logo.png">
                        </a>
                    </div>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav nav-logo">
                    <li><a href="{{'#/'+application.language+'/trade-link'}}">{{application.text.HowToPlay}}</a></li>
                    <li><a href="{{'#/'+application.language+'/history'}}">{{application.text.History}}</a></li>
                    <li><a href="{{'#/'+application.language+'/top'}}">{{application.text.TopWinners}}</a></li>
                    <li><a href="{{'#/'+application.language+'/rules'}}">{{application.text.Rules}}</a></li>
                    <li><a href="{{'#/'+application.language+'/contact'}}">{{application.text.Contact}}</a></li>
                </ul>

                <ul class="nav navbar-nav navbar-right nav-logo">
                    <li ng-class="application.language == 'en' ? 'active' : ''" ><a ng-click="changeLanguage('en')">en</a></li>
                    <li ng-class="application.language == 'ru' ? 'active' : ''"><a ng-click="changeLanguage('ru')">ru</a></li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>

</header>



<div class="wrapper">
    <div ui-view></div>
</div>

<script src="/resources/bootstrap/jquery-1.11.3.js"></script>
<script src="/resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="/resources/bower_components/angular/angular.min.js"></script>
<script src="/resources/bower_components/angular-ui-router/release/angular-ui-router.min.js"></script>
<script src="/resources/bower_components/ng-websocket/ng-websocket.js"></script>
<script src="/resources/bower_components/angular-progress-arc/angular-progress-arc.min.js"></script>
<script src="/resources/application/platform.js"></script>
<script src="/resources/application/index.js"></script>
<c:forEach items="${scripts}" var="src">
    <script src="${src}"></script>
</c:forEach>

<!-- Scripts html5 up -->


</body>
</html>