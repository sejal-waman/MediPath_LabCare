<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/views/fragments/header.jsp" %> <!-- Only if you access via absolute path and have servlet mapping to allow it -->


<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
</head>

<div style="display: flex;">
   <%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

    <div style="flex-grow: 1; padding: 20px;">
        <h2>💊 Manage Prescriptions</h2>

        <!-- 🔔 Display Error If Any -->
        <c:if test="${not empty error}">
            <div style="color: red; font-weight: bold;">${error}</div>
        </c:if>

        <!-- 💊 Prescription Form -->
        <form action="${pageContext.request.contextPath}/doctor/prescriptions" method="post">
            <label>Patient:</label>
            <select name="patientId" required>
                <c:forEach var="patient" items="${patients}">
                    <option value="${patient.id}">${patient.name}</option>
                </c:forEach>
            </select><br><br>

            <label>Medication:</label>
            <input type="text" name="medication" required><br><br>

            <label>Dosage:</label>
            <input type="text" name="dosage" required><br><br>

            <label>Instructions:</label><br>
            <textarea name="instructions" rows="3" cols="40"></textarea><br><br>

            <button type="submit">Prescribe</button>
        </form>
		
		<br><br><br>

		
		
        <!-- 📋 Prescription List -->
        <h3>📝 Previous Prescriptions</h3>
        <table border="1" cellpadding="8" cellspacing="0">
            <tr style="background-color: #e0e0e0;">
                <th>Date</th>
                <th>Patient</th>
                <th>Medication</th>
                <th>Dosage</th>
                <th>Instructions</th>
            </tr>
            <c:forEach var="prescription" items="${prescriptions}">
                <tr>
                    <td>${prescription.prescribedDate}</td>
                    <td>${prescription.patient.name}</td>
                    <td>${prescription.medication}</td>
                    <td>${prescription.dosage}</td>
                    <td>${prescription.instructions}</td>
                </tr>
            </c:forEach>
        </table> 
    </div>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
