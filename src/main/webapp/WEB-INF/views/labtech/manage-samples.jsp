<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<html>
<head>
    <title>🔬 Manage Lab Samples - MediPath LabCare</title>
    <link rel="stylesheet" href="/css/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2 class="mb-4 text-center">🔬 Manage Lab Samples</h2>
<br><br>
    <!-- Display Success Message -->
    <c:if test="${not empty message}">
        <div class="alert alert-success text-center">
            ${message}
        </div>
    </c:if>

    <!-- Check if samples exist -->
    <c:if test="${not empty labSamples}">
        <table class="table table-bordered table-hover">
            <thead class="table-dark">
				<div class="text-center mt-4">
				        <a href="${pageContext.request.contextPath}/labtech/add-sample" class="btn btn-success">
				            ➕ Add New Sample
				        </a>
			   </div>
			   <br><br>
                <tr>
                    <th>Sample ID</th>
                    <th>Patient Name</th>
                    <th>Test Type</th>
                    <th>Status</th>
                    <th>Normal Range</th>
                    <th>Unit</th>
                    <th>Description</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="sample" items="${labSamples}">
                    <tr>
                        <td>${sample.id}</td>
                        <td>${sample.patientName}</td>
                        <td>${sample.testType}</td>
                        <td>${sample.status}</td>
                        <td>${sample.normalRange}</td>
                        <td>${sample.unit}</td>
                        <td>${sample.description}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/labtech/sample/edit/${sample.id}" class="btn btn-sm btn-warning">Edit</a>
                            <form action="${pageContext.request.contextPath}/labtech/sample/delete" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="${sample.id}" />
                                <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this sample?');">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <!-- No samples case -->
    <c:if test="${empty labSamples}">
        <div class="alert alert-info text-center">
            No lab samples found.
        </div>
    </c:if>

    
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
</body>
</html>
