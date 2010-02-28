<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<link rel="stylesheet" href="/styles/standard.css" type="text/css" />

		<title>G(A)estbook!</title>
	</head>
<body>
<h1>Welcome to G(a)estbook!</h1>
<p>This is a sample web application developed with Spring Framework and OpenJPA...</p>

<div id="messageFormContainer">
	<form action="/app/messagePosted" method="post">
		<div><label for="name">Name:</label></div>
		<div><input type="text" name="name" id="name"></input></div>
		<div><label for="email">E-mail:</label></div>
		<div><input type="text" name="email" id="email"></input></div>
		<div><label for="message">Message:</label></div>
		<div><textarea rows="5" cols="5" name="message" id="message"></textarea></div>
		<div class="buttonArea">
			<input type="submit" value="Post new message" />
			<input type="reset" value="Reset" />
		</div>
	</form>
</div>

<div id="messages">
	
	<c:forEach items="${messages}" var="item">
		<div class="message">
			<div class="message-list">
				<p>${item.text}</p>
			</div>
			<div class="footer">
				Posted by <span id="name-list">${item.name}</span> on <span id="email-list">${item.created}</span>
			</div>
		</div>
	</c:forEach>
	
</div>

</body>
</html>
