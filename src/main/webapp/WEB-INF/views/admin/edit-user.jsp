<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>

<html>
<head>
    <title>Edit User - MediPath LabCare</title>
    <link rel="stylesheet" href="/css/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2>Edit User</h2>

    <form action="/admin/update-user" method="post">
        <input type="hidden" name="id" value="${user.id}" />

        <div class="mb-3">
            <label>Username:</label>
            <input type="text" name="username" class="form-control" value="${user.username}" required />
        </div>

        <div class="mb-3">
            <label>Email:</label>
            <input type="email" name="email" class="form-control" value="${user.email}" required />
        </div>

        <div class="mb-3">
            <label>Mobile Number:</label>
            <input type="text" name="mobileNo" class="form-control" value="${user.mobileNo}" required />
        </div>

        <div class="mb-3">
            <label>Enabled:</label>
            <input type="checkbox" name="enabled" ${user.enabled ? 'checked' : ''} />
        </div>

        <div class="mb-3">
            <label>Role(s):</label>
            <select name="roleIds" class="form-control" multiple>
                <c:forEach var="role" items="${roles}">
                    <option value="${role.id}"
                        <c:forEach var="ur" items="${user.roles}">
                            <c:if test="${ur.id == role.id}">selected</c:if>
                        </c:forEach>>
                        ${role.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="btn btn-success">Update</button>
        <a href="/admin/manage-users" class="btn btn-secondary">Cancel</a>
    </form>
</div>

</body>
</html>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
