<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="reset-password-form">

	<c:if test="${not empty message}">
		<div class="notify">${message}</div>
	</c:if>

	<h2>Reset Password</h2>

    <p style="line-height:1.23em;">Enter email that is registered with... to reset your password</p>

	<form action="${pageContext.request.contextPath}/users/send" method="post">

        <fieldset>

            <br/>
            <label for="username">Username</label>
            <input id="username" type="email" placeholder="Email" name="username" style="width:100%;">

            <br/>

            <div class="form-group" style="text-align:right;">
                <br/>
                <input type="submit" class="button retro" id="reset-password" value="Reset Password"/>
            </div>
        </fieldset>

	</form>

</div>


