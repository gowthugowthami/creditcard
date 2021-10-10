<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="Parakeet" uri="/META-INF/tags/parakeet.tld"%>
<%@ page import="xyz.goioc.Parakeet" %>

<html>
<head>
    <title>App : <decorator:title default=""/></title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/media/icon.png?v=<%=System.currentTimeMillis()%>">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/packages/grid.css?v=<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/default.css?v=<%=System.currentTimeMillis()%>">

    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/packages/jquery.js"></script>

    <style>
        #header-wrapper a{
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="container">

    <div class="row">
        <div class="col-sm-12">
            <div id="header-wrapper">
                <a href="${pageContext.request.contextPath}/hi" class="logo">
                    M1 Starter<br/>
                    <span class="tagline">Starter J2ee project stub</span>
                </a>

                <div id="navigation">
                    <span id="welcome">Hello <a href="${pageContext.request.contextPath}/users/edit/${sessionScope.userId}"><strong>${sessionScope.username}</strong></a>!</span>
                </div>
                <br class="clear"/>
            </div>

            <decorator:body />

        </div>
    </div>


    <div class="go-right">
        <a href="${pageContext.request.contextPath}/users" class="href-dotted">Users</a>
        <a href="${pageContext.request.contextPath}/signout" class="href-dotted">Signout</a>
    </div>

    <br class="clear"/>


</body>
</html>