<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>Users</title>
</head>

<body>

	<h1>Users</h1>

	<c:if test="${not empty message}">
		<div class="notify">${message}</div>
	</c:if>



	<c:choose>
		<c:when test="${users.size() > 0}">
					
			<div class="span12">

				<table class="table table-condensed">
					<thead>
						<tr>
							<th>Id</th>
							<th>Name</th>
							<th>Username</th>
							<th>Status</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="user" items="${users}">
							<tr>
								<td>${user.id}</td>
								<td>${user.username}</td>
								<td><a href="${pageContext.request.contextPath}/users/edit/${user.id}" title="Edit" class="button sky">Edit</a>
							</tr>									
						</c:forEach>
					</tbody>
				</table>
			</div>
			
		</c:when>
		<c:when test="${users.size() == 0}">
			<p>No users created yet.</p>
		</c:when>
	</c:choose>
</body>
</html>