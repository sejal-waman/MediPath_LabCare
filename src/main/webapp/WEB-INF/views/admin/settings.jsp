<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<html>
<head>
    <title>⚙️ System Settings</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>

<div class="container mt-5">
    <h2 class="mb-4">⚙️ System Settings</h2>

    <c:if test="${not empty message}">
        <div class="alert alert-success">${message}</div>
    </c:if>

    <form:form method="post" modelAttribute="settings" class="form">
        <div class="mb-3">
            <label for="appName" class="form-label">App Name:</label>
            <form:input path="appName" cssClass="form-control"/>
            <form:errors path="appName" cssClass="text-danger"/>
        </div>

        <div class="mb-3">
            <label for="supportEmail" class="form-label">Support Email:</label>
            <form:input path="supportEmail" cssClass="form-control"/>
            <form:errors path="supportEmail" cssClass="text-danger"/>
        </div>

        <button type="submit" class="btn btn-success">💾 Save Settings</button>
    </form:form>
</div>

</body>
</html>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
