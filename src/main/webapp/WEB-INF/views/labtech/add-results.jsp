<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<html>
<head>
    <title>🧪 Enter Lab Results - MediPath LabCare</title>
    <link rel="stylesheet" href="/css/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<div class="container mt-4">
    <h2 class="mb-4">🧪 Enter Lab Test Results</h2>

    <!-- ✅ Success Message -->
    <c:if test="${not empty message}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/labtech/add-results" method="post" class="border p-4 rounded shadow-sm bg-light">

        <!-- 👤 Select Patient -->
        <div class="mb-3">
            <label for="patientId" class="form-label">Select Patient:</label>
            <select name="patientId" id="patientId" class="form-select" required>
                <option value="">-- Select a Patient --</option>
                <c:forEach var="patient" items="${patients}">
                    <option value="${patient.id}">${patient.username}</option>
                </c:forEach>
            </select>
        </div>

        <!-- Test Name -->
        <div class="mb-3">
            <label for="testName" class="form-label">Test Name:</label>
            <input type="text" name="testName" id="testName" class="form-control" required />
        </div>

        <!-- Test Type -->
        <div class="mb-3">
            <label for="testType" class="form-label">Test Type:</label>
            <input type="text" name="testType" id="testType" class="form-control" required />
        </div>

        <!-- Category -->
        <div class="mb-3">
            <label for="category" class="form-label">Category:</label>
            <input type="text" name="category" id="category" class="form-control" required />
        </div>

        <!-- Status -->
        <div class="mb-3">
            <label for="status" class="form-label">Status:</label>
            <select name="status" id="status" class="form-control" required>
                <option value="Pending" selected>Pending</option>
                <option value="Completed">Completed</option>
                <option value="Canceled">Canceled</option>
            </select>
        </div>

        <!-- Test Date -->
        <div class="mb-3">
            <label for="testDate" class="form-label">Test Date:</label>
            <input type="date" name="testDate" id="testDate" class="form-control" required />
        </div>

        <!-- Result -->
        <div class="mb-3">
            <label for="result" class="form-label">Result:</label>
            <textarea name="result" id="result" rows="3" class="form-control" required></textarea>
        </div>

        <!-- Normal Range -->
        <div class="mb-3">
            <label for="normalRange" class="form-label">Normal Range:</label>
            <input type="text" name="normalRange" id="normalRange" class="form-control" />
        </div>

        <!-- Unit -->
        <div class="mb-3">
            <label for="unit" class="form-label">Unit:</label>
            <input type="text" name="unit" id="unit" class="form-control" />
        </div>

        <!-- Description -->
        <div class="mb-3">
            <label for="description" class="form-label">Description:</label>
            <textarea name="description" id="description" rows="3" class="form-control"></textarea>
        </div>

        <!-- Cost -->
        <div class="mb-3">
            <label for="cost" class="form-label">Cost:</label>
            <input type="number" name="cost" id="cost" class="form-control" required min="0" step="0.01" placeholder="Enter the cost" aria-describedby="costHelp"/>
            <small id="costHelp" class="form-text text-muted">Please enter a valid positive cost (e.g., 100.50).</small>
        </div>

        <!-- Submit -->
        <button type="submit" class="btn btn-success">✅ Submit Result</button>
    </form>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>

</body>
</html>
