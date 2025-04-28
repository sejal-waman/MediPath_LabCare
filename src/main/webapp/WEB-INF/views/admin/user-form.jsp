<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>

<div class="container mt-5">
    <h2>${user.id == null ? '➕ Add New User' : '✏️ Edit User'}</h2>

    <form action="${user.id == null ? '/admin/users/save' : '/admin/users/update'}" method="post">
        <input type="hidden" name="id" value="${user.id}"/>

        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" class="form-control" name="email" value="${user.email}" required/>
        </div>

        <div class="mb-3">
            <label class="form-label">Username</label>
            <input type="text" class="form-control" name="username" value="${user.username}" required/>
        </div>

        <div class="mb-3">
            <label class="form-label">Password</label>
            <input type="password" class="form-control" name="password" ${user.id == null ? 'required' : ''}/>
            <c:if test="${user.id != null}">
                <small class="form-text text-muted">Leave blank to keep current password.</small>
            </c:if>
        </div>

        <div class="mb-3">
            <label class="form-label">Role</label>
            <select class="form-select" name="role" required>
                <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>Admin</option>
                <option value="DOCTOR" ${user.role == 'DOCTOR' ? 'selected' : ''}>Doctor</option>
                <option value="LABTECH" ${user.role == 'LABTECH' ? 'selected' : ''}>Lab Technician</option>
                <option value="RECEPTION" ${user.role == 'RECEPTION' ? 'selected' : ''}>Receptionist</option>
                <option value="PATIENT" ${user.role == 'PATIENT' ? 'selected' : ''}>Patient</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Status</label>
            <select class="form-select" name="status" required>
                <option value="ACTIVE" ${user.status == 'ACTIVE' ? 'selected' : ''}>Active</option>
                <option value="INACTIVE" ${user.status == 'INACTIVE' ? 'selected' : ''}>Inactive</option>
            </select>
        </div>

        <button type="submit" class="btn btn-success">💾 Save </button>
        <a href="/admin/users" class="btn btn-secondary">🔙 Back </a>
    </form>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
