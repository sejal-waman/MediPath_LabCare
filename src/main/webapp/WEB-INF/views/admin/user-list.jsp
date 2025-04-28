<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container mt-5">
    <h2>👥 Manage Users</h2>

    <a href="/admin/users/new" class="btn btn-primary mb-3">➕ Add New User</a>

    <table class="table table-bordered table-hover">
        <thead>
            <tr>
                <th>ID</th>
                <th>Email</th>
                <th>Username</th>
                <th>Role</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.email}</td>
                    <td>${user.username}</td>
                    <td>${user.role}</td>
                    <td>${user.status}</td>
                    <td>
                        <a href="/admin/users/edit/${user.id}" class="btn btn-warning btn-sm">✏️ Edit</a>
                        <a href="/admin/users/delete/${user.id}" class="btn btn-danger btn-sm" 
                           onclick="return confirm('Are you sure to delete this user?');">🗑️ Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
