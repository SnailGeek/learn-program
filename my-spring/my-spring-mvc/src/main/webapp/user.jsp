<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>validate user</title>
</head>
<body>
<form:form commandName="user" action="/valid/signup" method="POST">
    <form:errors path="*"/><br/>用户名<form:input path="userName"/><form:errors path="userName"/>
</form:form>
</body>
</html>