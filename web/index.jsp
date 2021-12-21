<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Index</title>
</head>
<body>isCurrentRace
<%--<c:redirect url="dispatcher?command=races"></c:redirect>--%>
<jsp:forward page="dispatcher?command=races"/>
</body>
</html>