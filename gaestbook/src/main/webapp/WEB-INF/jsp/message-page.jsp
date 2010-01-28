<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
	<head>
		<link rel="stylesheet" href="/styles/standard.css" type="text/css" />
		 
		<title>G(a)estbook</title>
	</head>
	<body>
		<c:if test="${status != null}">
			<h2 id="message">${status}</h2>
		</c:if>
		
		<c:if test="${error != null}">
			<h2 id="error">${error}</h2>	
		</c:if>
		
		<c:if test="${message != null}">
			<div class="message">
				<div class="message-list">
					<p>${message.text}</p>
				</div>
				<div class="footer">
					Posted by <span id="name-list">${message.name}</span> on <span id="email-list">${message.created}</span>
				</div>
			</div>
		</c:if>
		
		<div class="buttonArea">
			<a href="/app/messages"> &laquo; back</a>
		</div>
	</body>
</html>
