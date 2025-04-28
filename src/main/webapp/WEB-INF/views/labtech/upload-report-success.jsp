<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<html>
<head>
    <title>🔬 Report Upload Successful - MediPath LabCare</title>
    <link rel="stylesheet" href="/css/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-4">
    <h2 class="mb-4">🔬 Report Uploaded Successfully</h2>
    <p>Report for the patient has been uploaded successfully!</p>
    <a href="${pageContext.request.contextPath}/labtech/manage-reports" class="btn btn-primary">View Reports</a>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
</body>
</html>
