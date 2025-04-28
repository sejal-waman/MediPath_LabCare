<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="list-group">
    <a href="${pageContext.request.contextPath}/dashboard" class="list-group-item list-group-item-action active">🏠 Dashboard</a>

    <!-- ADMIN -->
    <c:forEach var="r" items="${sessionScope.loggedInUser.roles}">
        <c:if test="${r.name eq 'ADMIN'}">
			<a href="${pageContext.request.contextPath}/admin/dashboard" class="list-group-item list-group-item-action">🛡️ Admin Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/manage-users" class="list-group-item list-group-item-action">👥 Manage Users</a>
            <a href="${pageContext.request.contextPath}/admin/manage-tests" class="list-group-item list-group-item-action">🧪 Manage Tests</a>
            <a href="${pageContext.request.contextPath}/admin/view-reports" class="list-group-item list-group-item-action">📊 View Reports</a>
            <a href="${pageContext.request.contextPath}/admin/settings" class="list-group-item list-group-item-action">⚙️ Settings</a>
        </c:if>
    </c:forEach>

    <!-- DOCTOR -->
    <c:forEach var="r" items="${sessionScope.loggedInUser.roles}">
        <c:if test="${r.name eq 'DOCTOR'}">
            <a href="${pageContext.request.contextPath}/doctor/dashboard" class="list-group-item list-group-item-action">🩺 Doctor Dashboard</a>
            <a href="${pageContext.request.contextPath}/doctor/assigned-patients" class="list-group-item list-group-item-action">📁 Assigned Patients</a>
            <a href="${pageContext.request.contextPath}/doctor/add-diagnosis" class="list-group-item list-group-item-action">📝 Add Diagnosis</a>
            <a href="${pageContext.request.contextPath}/doctor/reports" class="list-group-item list-group-item-action">📑 Review Lab Reports</a>
            <a href="${pageContext.request.contextPath}/doctor/prescriptions" class="list-group-item list-group-item-action">💊 Manage Prescriptions</a>
        </c:if>
    </c:forEach>

    <!-- LAB TECHNICIAN -->
    <c:forEach var="r" items="${sessionScope.loggedInUser.roles}">
        <c:if test="${r.name eq 'LABTECH'}">
            <a href="${pageContext.request.contextPath}/labtech/dashboard" class="list-group-item list-group-item-action">🧫 Lab Dashboard</a>
            <a href="${pageContext.request.contextPath}/labtech/add-results" class="list-group-item list-group-item-action">🧪 Enter Test Results</a>
            <a href="${pageContext.request.contextPath}/labtech/manage-samples" class="list-group-item list-group-item-action">🧴 Manage Samples</a>
            <a href="${pageContext.request.contextPath}/labtech/upload-report" class="list-group-item list-group-item-action">📤 Upload Reports</a>
            <a href="${pageContext.request.contextPath}/labtech/equipment" class="list-group-item list-group-item-action">🔧 Equipment Status</a>
        </c:if>
    </c:forEach>

    <!-- RECEPTIONIST -->
    <c:forEach var="r" items="${sessionScope.loggedInUser.roles}">
        <c:if test="${r.name eq 'RECEPTIONIST'}">
			<a href="${pageContext.request.contextPath}/reception/dashboard" class="list-group-item list-group-item-action">🧫 Reception Dashboard</a>
            <a href="${pageContext.request.contextPath}/receptionist/patient-enquiries" class="list-group-item list-group-item-action">❓ Patient Enquiries</a>
            <a href="${pageContext.request.contextPath}/receptionist/billing" class="list-group-item list-group-item-action">💵 Generate Bills</a>
        </c:if>
    </c:forEach>
	
	
	

    <!-- PATIENT -->
    <c:forEach var="r" items="${sessionScope.loggedInUser.roles}">
        <c:if test="${r.name eq 'PATIENT'}">
            <a href="${pageContext.request.contextPath}/patient/dashboard" class="list-group-item list-group-item-action">👤 My Dashboard</a>
            <a href="${pageContext.request.contextPath}/patient/my-tests" class="list-group-item list-group-item-action">🧬 My Test Reports</a>
            <a href="${pageContext.request.contextPath}/patient/appointments" class="list-group-item list-group-item-action">📆 Appointments</a>
			<a href="${pageContext.request.contextPath}/patient/send-enquiries" class="list-group-item list-group-item-action">✉️ Send Enquiry</a>
            <a href="${pageContext.request.contextPath}/patient/my-profile" class="list-group-item list-group-item-action">🛠️ Update Profile</a>
        </c:if>
    </c:forEach>
</div>
