<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored ="false" %>

<html>
<head>
    <title>Home Page</title>

</head>
<body>

<h1>Hello there, ${requestScope.username}!</h1>

<a href="/logout?logout">Logout</a>

</body>
</html>
