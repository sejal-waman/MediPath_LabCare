<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Lab Test - MediPath LabCare</title>
    <link rel="stylesheet" href="/css/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2 class="mb-4">Edit Lab Test</h2>

    <%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

    <form action="/admin/update-test" method="post">
        <input type="hidden" name="id" value="${labTest.id}" />

        <div class="mb-3">
            <label for="testName" class="form-label">Test Name</label>
            <input type="text" class="form-control" name="testName" id="testName" value="${labTest.testName}" required>
        </div>

        <div class="mb-3">
            <label for="category" class="form-label">Category</label>
            <input type="text" class="form-control" name="category" id="category" value="${labTest.category}" required>
        </div>

        <div class="mb-3">
            <label for="normalRange" class="form-label">Normal Range</label>
            <input type="text" class="form-control" name="normalRange" id="normalRange" value="${labTest.normalRange}">
        </div>

        <div class="mb-3">
            <label for="unit" class="form-label">Unit</label>
            <input type="text" class="form-control" name="unit" id="unit" value="${labTest.unit}">
        </div>

        <div class="mb-3">
            <label for="cost" class="form-label">Cost (₹)</label>
            <input type="number" class="form-control" name="cost" id="cost" value="${labTest.cost}" required>
        </div>

        <button type="submit" class="btn btn-success">Update</button>
        <a href="/admin/manage-tests" class="btn btn-secondary">Cancel</a>
    </form>
</div>

</body>
</html>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
