<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty error}">
	<div class="notify">${error}</div>
</c:if>

<h1>Your Profile</h1>

<p><strong>Username: </strong> ${user.username}</p>
<p><strong>Password: </strong> ${user.password}</p>

