<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>

<html>
<head>
    <title>Assigned Patients - MediPath LabCare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<div class="container mt-4">
    <h2>🏥 Assigned Patients</h2>

    <c:if test="${empty assignedPatients}">
        <div class="alert alert-info">No patients have assigned themselves to you yet.</div>
    </c:if>

    <c:if test="${not empty assignedPatients}">
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Patient Name</th>
                    <th>Age</th>
                    <th>Gender</th>
                    <th>Contact</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="patient" items="${assignedPatients}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td>${patient.username}</td>
                        <td>${patient.age}</td>
                        <td>${patient.gender}</td>
                        <td>${patient.mobileNo}</td>
                        <td>
                            <c:forEach var="appointment" items="${patient.appointments}">
                                <c:choose>
                                    <c:when test="${not appointment.appointmentApproved}">
                                        <form action="${pageContext.request.contextPath}/doctor/approve-appointment" method="post" style="display:inline;">
                                            <input type="hidden" name="appointmentId" value="${appointment.id}" />
                                            <button type="submit" class="btn btn-success btn-sm">Approve</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn btn-secondary btn-sm" disabled>Approved</button>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
</body>
</html>
