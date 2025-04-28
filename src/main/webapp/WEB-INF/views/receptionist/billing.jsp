<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>



<html>
<head>
    <title>💳 Generate Bills - MediPath LabCare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<div class="container mt-4">
    <h2>💳 Generate Bill</h2>

    <!-- Display Success or Error Message -->
    <c:if test="${not empty message}">
        <div class="alert alert-success">
            <p>${message}</p>
        </div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">
            <p>${errorMessage}</p>
        </div>
    </c:if>

    <!-- Patient Test Results Table -->
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>#</th>
                <th>Patient Name</th>
                <th>Test Name</th>
                <th>Test Type</th>
                <th>Test Date</th>
                <th>Cost</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="result" items="${labResults}">
                <tr>
                    <td>${result.id}</td>
                    <td>${result.patientName}</td>
                    <td>${result.testName}</td>
                    <td>${result.testType}</td>
                    <td>${result.testDate}</td>
                    <td>${result.cost}</td>
                    <td>
                        <!-- Action to generate bill -->
                        <form action="${pageContext.request.contextPath}/receptionist/generate-bill" method="post" target="_blank">
                            <input type="hidden" name="resultId" value="${result.id}">
                            <!-- Disable button if bill is already generated -->
									
									<button type="submit" class="btn btn-success btn-sm"
									    <c:if test="${result.billGenerated}">
									        disabled="disabled"
									    </c:if>
									>Generate Bill</button>

                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>

</body>
</html>
