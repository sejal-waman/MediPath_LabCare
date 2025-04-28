<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>

<%
    if (session.getAttribute("loggedInUser") == null) {
        session.setAttribute("error", "Please login first.");
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>


<c:set var="pageTitle" value="Doctor Dashboard" />

<div class="container-fluid">
    <div class="row">
        <!-- Sidebar Section -->
        <div class="col-md-2 bg-light min-vh-100">
            <%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>
        </div>

        <!-- Main Content Area -->
        <div class="col-md-10 p-4">
            <h2 class="mb-4">
                <c:choose>
                    <c:when test="${not empty sessionScope.loggedInUser}">
                        <c:forEach var="r" items="${sessionScope.loggedInUser.roles}">
                            <c:if test="${r.name eq 'DOCTOR'}">
                                👨‍⚕️ Welcome, Dr. ${sessionScope.loggedInUser.username}!
                            </c:if>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        👨‍⚕️ Welcome, Doctor!
                    </c:otherwise>
                </c:choose>
            </h2>

            <!-- Display Doctor's Specialization -->
            <c:if test="${not empty user.specialization}">
                <h4>Specialization: ${user.specialization}</h4>
                <table class="table table-bordered mt-3">
                    <thead>
                        <tr>
                            <th>Specialization</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>${user.specialization}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/doctor/update-specialization" class="btn btn-warning btn-sm">
                                    Edit
                                </a>
                                <a href="${pageContext.request.contextPath}/doctor/delete-specialization" class="btn btn-danger btn-sm">
                                    Delete
                                </a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </c:if>
            
            <c:if test="${empty user.specialization}">
                <a href="${pageContext.request.contextPath}/doctor/update-specialization" class="btn btn-info mt-3">
                    Add Your Specialization
                </a>
            </c:if>

            <!-- Roles Section -->
            <c:if test="${not empty sessionScope.loggedInUser and not empty sessionScope.loggedInUser.roles}">
                <h5>Your Roles:</h5>
                <ul class="list-unstyled">
                    <c:forEach var="r" items="${sessionScope.loggedInUser.roles}">
                        <li>🩺 ${r.name}</li>
                    </c:forEach>
                </ul>
            </c:if>

            <!-- Navigation Section -->
            <div class="list-group mt-4 shadow-sm">
                <a href="${pageContext.request.contextPath}/doctor/assigned-patients" class="list-group-item list-group-item-action">
                    🧑‍⚕️ View Assigned Patients
                </a>
                <a href="${pageContext.request.contextPath}/doctor/add-diagnosis" class="list-group-item list-group-item-action">
                    📝 Add Diagnosis
                </a>
                <a href="${pageContext.request.contextPath}/doctor/reports" class="list-group-item list-group-item-action">
                    📄 Review Lab Reports
                </a>
                <a href="${pageContext.request.contextPath}/doctor/prescriptions" class="list-group-item list-group-item-action">
                    💊 Manage Prescriptions
                </a>
            </div>

            <!-- ✨ Appointment Approval Section -->
            <div class="mt-5">
                <h3>🗓️ Your Appointments and Approval Status</h3>
                <c:if test="${not empty appointments}">
                    <table class="table table-striped mt-3">
                        <thead>
                            <tr>
                                <th>Patient Name</th>
                                <th>Appointment Date</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="appointment" items="${appointments}">
                                <tr>
                                    <td>${appointment.patient.username}</td>
                                    <td>${appointment.appointmentDate}</td>
                                    <td>
                                        <c:set var="doctorApprovals" value="${appointment.doctorApprovals}" />
                                        <c:choose>
                                            <c:when test="${doctorApprovals[sessionScope.loggedInUser.id]}">
                                                <span class="badge bg-success">Approved</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-warning text-dark">Pending</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${empty appointments}">
                    <p class="text-muted">No appointments assigned yet.</p>
                </c:if>
            </div>

        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
