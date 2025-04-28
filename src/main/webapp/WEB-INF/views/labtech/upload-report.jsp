<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<html>
<head>
    <title>📤 Upload Lab Report - MediPath LabCare</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css' />">
</head>
<body>

<div class="container mt-4">
    <h2>📤 Upload Lab Report</h2>

    <c:if test="${not empty message}">
        <div class="alert alert-success">${message}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/labtech/upload-report"
          method="POST" enctype="multipart/form-data">

        <!-- 1) pick which test this PDF belongs to -->
        <div class="mb-3">
            <label for="labTestId" class="form-label">Select Test</label>
            <select name="labTestId" id="labTestId" class="form-control" required>
                <option value="">-- choose a test --</option>
                <c:forEach var="test" items="${labTests}">
                    <option value="${test.id}">
                        ${test.testName} (patient: ${test.patientName})
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- 2) pick the PDF file -->
        <div class="mb-3">
            <label for="reportFile" class="form-label">Select Report PDF</label>
            <input type="file"
                   id="reportFile"
                   name="reportFile"
                   class="form-control"
                   accept="application/pdf"
                   required>
        </div>

        <button type="submit" class="btn btn-primary">Upload Report</button>
        <a href="${pageContext.request.contextPath}/labtech/manage-samples"
           class="btn btn-secondary">Back</a>
    </form>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
</body>
</html>
