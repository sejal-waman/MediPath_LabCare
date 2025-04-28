<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<html>
<head>
    <title>Assigned Appointments - MediPath LabCare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
    <div class="container mt-4">
        <h2>📅 Assigned Appointments</h2>

        <!-- If no appointments -->
        <c:if test="${empty appointments}">
            <div class="alert alert-info">No appointments available.</div>
        </c:if>

        <!-- Appointments available -->
        <c:if test="${not empty appointments}">
            <table class="table table-bordered table-striped">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Doctor Name</th>
                        <th>Appointment Date</th>
                        <th>Status</th> <!-- Only status shown -->
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="appointment" items="${appointments}" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>${appointment.doctor.username}</td>
                            <td><fmt:formatDate value="${appointment.appointmentTime}" pattern="dd-MM-yyyy HH:mm" /></td>
                            <td>
                                <c:choose>
                                    <c:when test="${appointment.appointmentApproved}">
                                        <span class="badge bg-success">Approved</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-warning">Pending</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</body>
</html>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
