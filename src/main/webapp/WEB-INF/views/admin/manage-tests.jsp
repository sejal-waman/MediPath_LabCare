<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Lab Tests - MediPath LabCare</title>
    <link rel="stylesheet" href="/css/styles.css">
    <!-- Optional: Bootstrap for styling -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2 class="mb-4">Lab Test Management</h2>

    <%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

    <!-- 💡 Flash message for success -->
    <c:if test="${not empty message}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <table class="table table-bordered table-striped">
        <thead class="table-dark">
            <tr>
                <th>#</th>
                <th>Test Name</th>
                <th>Category</th>
                <th>Normal Range</th>
                <th>Unit</th>
                <th>Cost (₹)</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="test" items="${labTests}">
                <tr>
                    <td>${test.id}</td>
                    <td>${test.testName}</td>
                    <td>${test.category}</td>
                    <td>${test.normalRange}</td>
                    <td>${test.unit}</td>
                    <td>${test.cost}</td>
                    <td>
                        <a href="/admin/edit-test/${test.id}" class="btn btn-sm btn-primary">Edit</a>
                        <a href="/admin/delete-test/${test.id}" class="btn btn-sm btn-danger"
                           onclick="return confirm('Are you sure you want to delete this test?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
