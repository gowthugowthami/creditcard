<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="p" uri="/META-INF/tags/parakeet.tld" %>
<html>
<head>
    <title>M1 Starter</title>
</head>
<body>
<style>

</style>
<div id="overview-wrapper">

    <c:if test="${not empty message}">
        <div class="notify">${message}</div>
    </c:if>

    <h1>M1 Starter</h1>

    <p>This starter app includes everything you need including security to
    start a J2ee application. Please review the repo, web and service directories
    for examples. Only Super Users can access User Maintenance.
        <strong>croteau.mike@gmail.com</strong> with password as
        <strong>password</strong>.</p>

    <p:isAnonymous>
        <p>You must log in in order to maintain users.</p>
    </p:isAnonymous>

    <p:isAuthenticated>
        <p>You are now logged in. </p>
    </p:isAuthenticated>

    <p>We hope you enjoy.</p>

</div>
</body>