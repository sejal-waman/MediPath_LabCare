<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<html>
<head>
    <title>Add New Lab Sample - MediPath LabCare</title>
    <link rel="stylesheet" href="/css/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-4">
    <h2 class="mb-4">Add New Lab Sample</h2>

    <!-- Display success or error messages -->
    <c:if test="${not empty param.message}">
        <div class="alert alert-success">
            ${param.message}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/labtech/add-sample" method="POST">

        <div class="mb-3">
            <label for="patientNameField" class="form-label">Patient Name</label>
            <input type="text" class="form-control" id="patientNameField" name="patientName" required>
        </div>

        <div class="mb-3">
            <label for="testTypeSelect" class="form-label">Test Type</label>
            <select class="form-control" id="testTypeSelect" name="testType" required>
                <option value="hematology">Hematology</option>
                <option value="coagulation">Coagulation</option>
                <option value="urinalysis">Urinalysis</option>
                <option value="body_fluid">Body Fluids</option>
                <!-- Add more test types as necessary -->
            </select>
        </div>
		

      
		<div class="mb-3">
		    <label for="statusField" class="form-label">Status</label>
		    <input type="text" class="form-control" id="statusField" name="status" required>
		</div>


        <!-- Adding missing fields -->
        <div class="mb-3">
            <label for="normalRangeField" class="form-label">Normal Range</label>
            <input type="text" class="form-control" id="normalRangeField" name="normalRange">
        </div>

        <div class="mb-3">
            <label for="unitField" class="form-label">Unit</label>
            <input type="text" class="form-control" id="unitField" name="unit">
        </div>

        <div class="mb-3">
            <label for="descriptionField" class="form-label">Description</label>
            <textarea class="form-control" id="descriptionField" name="description" rows="3"></textarea>
        </div>

        <button type="submit" class="btn btn-success">Add Sample</button>
    </form>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
</body>
</html>
