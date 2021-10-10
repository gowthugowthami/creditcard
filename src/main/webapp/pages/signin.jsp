<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Signin</title>
</head>
<body>

    <c:if test="${not empty message}">
        <p class="notify">${message}</p>
    </c:if>

    <h2>Signin</h2>

    <p>Default Super User login: <strong>croteau.mike@gmail.com</strong> & <strong>password</strong></p>

    <form action="${pageContext.request.contextPath}/authenticate" method="post" >

        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" name="username" class="form-control" id="username" placeholder=""  value=""  style="width:100%;">
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" name="password" class="form-control" id="password" style="width:100%;" value=""  placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;">
        </div>

        <div style="text-align:right; margin:30px 0px;">
            <input type="submit" class="button retro" value="Signin" style="width:100%;">
        </div>

    </form>

    <div id="signup-container" style="text-align: center;margin:21px auto 30px auto">
        <a href="${pageContext.request.contextPath}/signup" class="href-dotted">Sign Up!</a>&nbsp;&nbsp;
        <a href="${pageContext.request.contextPath}/user/reset" class="href-dotted">Forgot Password</a>&nbsp;&nbsp;
    </div>



</body>
</html>
