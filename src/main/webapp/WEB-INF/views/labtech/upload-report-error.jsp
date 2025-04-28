<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<html>
<head>
    <title>🔬 Error Uploading Report - MediPath LabCare</title>
    <link rel="stylesheet" href="/css/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-4">
    <h2 class="mb-4">⚠️ Error Uploading Report</h2>
    <p>Sorry, there was an error uploading the report. Please try again.</p>
    <a href="${pageContext.request.contextPath}/labtech/upload-report" class="btn btn-danger">Try Again</a>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
</body>
</html>
