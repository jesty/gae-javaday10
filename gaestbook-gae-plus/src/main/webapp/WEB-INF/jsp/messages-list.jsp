<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<link rel="stylesheet" href="/styles/standard.css" type="text/css" />
		<link rel="stylesheet" href="/styles/bubbles.css" type="text/css" />

		<title>G(A)estbook!</title>
	</head>
<body>
<div id="header">
	<div id="login">
		<c:choose>
			<c:when test="${userService.logged}">
				<a id="name-list" href="/app/messages/of/${userService.user.nickname}/">${userService.user.nickname}</a> - <a href="${userService.logoutUrl}">logout</a>
			</c:when>
			<c:otherwise>
				<a href="${userService.loginUrl}">login</a>
			</c:otherwise>
		</c:choose>
	</div>
	<div id="logo">
		<a href="/" alt="G-a-estbook" title="Home"><img alt="G-a-estbook" src="/images/logo.png"></a>
	</div>
</div>

<div id="messageFormContainer">
	<form action="/app/messagePosted" method="post">
	<c:if test="${!userService.logged}">
		<div><label for="name">Name:</label></div>
		<div><input type="text" name="name" id="name"></input></div>
		<div><label for="email">E-mail:</label></div>
		<div><input type="text" name="email" id="email"></input></div>
	</c:if>
		<div><label for="message">Message:</label></div>
		<div><textarea rows="5" cols="5" name="text" id="text"></textarea></div>
		<div class="buttonArea">
			<input type="submit" value="Post new message" />
			<input type="reset" value="Reset" />
		</div>
	</form>
</div>

<div id="messages">
	
	<c:forEach items="${messages}" var="item">
		<div class="bubble">
			<blockquote>
				<p>${item.text}</p>
			</blockquote>
			<cite>
				<strong>
					<c:choose>
						<c:when test="${item.anonymous}">
							${item.name} (anonymous user)
						</c:when>
						<c:otherwise>
							<a id="name-list" href="/app/messages/of/${item.name}/">${item.name}</a>
						</c:otherwise>
					</c:choose>
				</strong> 
				on ${item.created}
			</cite>
		</div>
	</c:forEach>
	
</div>

</body>
</html>
