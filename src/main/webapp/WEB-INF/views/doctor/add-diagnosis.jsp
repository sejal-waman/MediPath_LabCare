<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ include file="/WEB-INF/views/fragments/header.jsp" %>

<html>
<head>
    <title>Add Diagnosis - MediPath LabCare</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>

<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<div class="container mt-4">
    <h2>📝 Add Patient Diagnosis </h2>

    <form:form modelAttribute="diagnosis" method="post" class="form">
        <div class="form-group">
            <label for="patient">Select Patient:</label>
            <form:select path="patient.id" cssClass="form-control">
                <form:options items="${patients}" itemValue="id" itemLabel="name" />
            </form:select>
        </div>
        <br/>

        <div class="form-group">
            <label for="diagnosisDetails">Diagnosis Details:</label>
            <form:textarea path="diagnosisDetails" rows="5" cols="40" cssClass="form-control"/>
        </div>
        <br/>

        <div class="form-group">
            <label for="diagnosisDate">Date:</label>
            <form:input path="diagnosisDate" type="date" cssClass="form-control"/>
        </div>
        <br/>

        <button type="submit" class="btn btn-primary">Submit Diagnosis</button>
    </form:form>

    <c:if test="${not empty message}">
        <div class="alert alert-success mt-3">${message}</div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="alert alert-danger mt-3">${error}</div>
    </c:if>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>

</body>
</html>
