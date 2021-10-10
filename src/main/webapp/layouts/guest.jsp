<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="parakeet" uri="/META-INF/tags/parakeet.tld"%>

<html>
<head>
    <title>App : <decorator:title default=""/></title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/media/icon.png?v=<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/packages/grid.css?v=<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/default.css?v=<%=System.currentTimeMillis()%>">

    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/packages/jquery.js"></script>

</head>
<body>

<div class="container">

    <div class="row">

        <div class="col-sm-12">

            <div id="guest-content">
                <decorator:body />
            </div>

            <div id="footer-navigation">
                <%if(!request.getServletPath().equals("/home")){%>
                    <a href="${pageContext.request.contextPath}/home" class="href-dotted">Home</a>
                <%}%>

                <%if(!request.getServletPath().equals("/about")){%>
                    &nbsp;<a href="${pageContext.request.contextPath}/about" class="href-dotted">About</a>
                <%}%>

                <parakeet:isAuthenticated>
                    &nbsp;<strong class="highlight" style="font-family: roboto-slab-semibold !important">Signed in <a href="${pageContext.request.contextPath}/" class="href-dotted">My Profile</a></strong>&nbsp;
                </parakeet:isAuthenticated>
                <parakeet:isAnonymous>
                    &nbsp;<a href="${pageContext.request.contextPath}/signin" class="href-dotted">Signin</a>&nbsp;
                </parakeet:isAnonymous>

            </div>

        </div>

    </div>
</div>

</body>
</html>