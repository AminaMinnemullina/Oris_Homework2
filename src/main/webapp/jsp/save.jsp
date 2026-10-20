<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Save Users</title>
</head>
<body>
<h1>ADD USERS PAGE</h1>
<div>
    <form action="/save" method="post">
        <c:forEach var="i" begin="1" end="${count}">
            <div>
                <h3>User ${i}</h3>
                <input name="name" placeholder="First name">
                <input name="surname" placeholder="Last name">
                <input name="age" placeholder="Age">
                <input name="city" placeholder="City">
                <input name="country" placeholder="Country">
                <input name="email" placeholder="Email">
            </div>
            <br>
        </c:forEach>
        <input type="submit" value="Save All">
    </form>
</div>
</body>
</html>