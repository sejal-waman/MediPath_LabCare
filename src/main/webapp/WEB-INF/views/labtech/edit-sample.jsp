<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Lab Sample</title>
    <link rel="stylesheet" href="/css/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-4">
    <h2>Edit Lab Sample</h2>

    <!-- Display any messages like success or error -->
    <c:if test="${param.message != null}">
        <p style="color: green;">${param.message}</p>
    </c:if>
    <c:if test="${param.error != null}">
        <p style="color: red;">${param.error}</p>
    </c:if>

    <!-- Form to edit the lab sample -->
    <form:form modelAttribute="sample" action="${pageContext.request.contextPath}/labtech/sample/edit/${sample.id}" method="POST">
        
        <!-- Hidden input for the sample ID -->
        <input type="hidden" name="id" value="${sample.id}" />

        <!-- Sample ID (read-only as it's not meant to be edited) -->
        <div>
            <label for="sampleId">Sample ID:</label>
            <input type="text" id="sampleId" name="sampleId" value="${sample.id}" readonly class="form-control" />
        </div>

        <!-- Patient Name -->
        <div class="mt-3">
            <label for="patientName">Patient Name:</label>
            <form:input path="patientName" id="patientName" class="form-control" required="true" />
        </div>

        <!-- Test Type -->
        <div class="mt-3">
            <label for="testType">Test Type:</label>
            <form:input path="testType" id="testType" class="form-control" required="true" />
        </div>

        <!-- Status -->
        <div class="mt-3">
            <label for="status">Status:</label>
            <form:input path="status" id="status" class="form-control" required="true" />
        </div>

        <!-- Normal Range -->
        <div class="mt-3">
            <label for="normalRange">Normal Range:</label>
            <form:input path="normalRange" id="normalRange" class="form-control" required="true" />
        </div>

        <!-- Unit -->
        <div class="mt-3">
            <label for="unit">Unit:</label>
            <form:input path="unit" id="unit" class="form-control" required="true" />
        </div>

        <!-- Description -->
        <div class="mt-3">
            <label for="description">Description:</label>
            <form:textarea path="description" id="description" class="form-control" rows="4" required="true"></form:textarea>
        </div>

        <!-- Action buttons -->
        <div class="mt-4">
            <button type="submit" class="btn btn-primary">Save Changes</button>
            <a href="${pageContext.request.contextPath}/labtech/manage-samples" class="btn btn-secondary">Cancel</a>
        </div>

    </form:form>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
</body>
</html>
