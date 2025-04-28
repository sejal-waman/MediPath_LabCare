<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>

<html>
<head>
    <title>📊 View Lab Reports</title>
    <link rel="stylesheet" href="/css/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<div class="container mt-5">
    <h2 class="mb-4">📊 All Lab Reports</h2>

    <table class="table table-bordered table-striped">
        <thead class="table-dark">
            <tr>
                <th>#</th>
                <th>Patient Name</th>
                <th>Test Name</th>
                <th>Result</th>
                <th>Report Date</th>
                <th>Status</th>
                <th>Download</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="report" items="${reports}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${report.patientName}</td>
                    <td>${report.labTest.testName}</td>
                    <td>${report.result}</td>
                    <td><c:out value="${report.reportDate}" /></td>
                    <td>${report.status}</td>

                    <td>
                       
						<a href="${pageContext.request.contextPath}/uploads/${report.pdfPath}"
						         class="btn btn-sm btn-primary" target="_blank">
						        Download PDF
						 </a> 
						
						         <!-- Dynamically link to the download-report method with the report's ID -->
						<!--<a href="${pageContext.request.contextPath}/admin/download-report/${report.id}" class="btn btn-sm btn-primary" target="_blank">
						        Download PDF
						 </a> -->
						                   


                    </td>
                </tr>
            </c:forEach>
			
        </tbody>
    </table>
</div>

</body>
</html>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
