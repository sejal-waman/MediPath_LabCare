<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>

<html>
<head>
    <title>Choose Your Doctor - MediPath LabCare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <!-- SweetAlert2 -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>

<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<div class="container mt-4">
    <h2>🏥 Choose Your Doctor</h2>

    <!-- Form for choosing doctor -->
    <form action="${pageContext.request.contextPath}/patient/assign-doctor" method="post" id="assignDoctorForm">
        <!-- Hidden field for reportId -->
        <input type="hidden" name="reportId" value="${selectedReportId}" /> <!-- Ensure reportId is passed correctly -->

        <div class="form-group">
            <label for="doctorId">Available Doctors:</label>
            <select name="doctorId" id="doctorId" class="form-control" required>
                <c:forEach var="doctor" items="${doctors}">
                    <option value="${doctor.id}">
                        ${doctor.username}
                        <c:if test="${not empty doctor.specialization}">
                            - ${doctor.specialization}
                        </c:if>
                        <c:if test="${not empty doctor.email}">
                            (${doctor.email})
                        </c:if>
                    </option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="btn btn-primary mt-3">Assign Doctor</button>
    </form>
</div>

<!-- SweetAlert Success Popup (After Form Submit) -->
<script>
    document.getElementById("assignDoctorForm").addEventListener("submit", function(event) {
        event.preventDefault(); // Prevent the default form submit

        Swal.fire({
            title: 'Doctor Assigned!',
            text: 'Doctor assigned successfully!',
            icon: 'success',
            confirmButtonText: 'OK'
        }).then((result) => {
            if (result.isConfirmed) {
                this.submit(); // Submit the form after confirmation
            }
        });
    });
</script>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>

</body>
</html>
