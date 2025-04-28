<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ include file="/WEB-INF/views/fragments/header.jsp" %>

<html>
<head>
    <title>📄 Review Lab Reports - MediPath LabCare</title>
    <link rel="stylesheet" href="/css/styles.css">
    <script>
        // Function to disable the "Done" button once clicked
        function disableButton(button) {
            button.disabled = true;
            button.innerText = 'Reviewed';  // Change text to 'Reviewed'
            button.classList.remove('btn-success');
            button.classList.add('btn-secondary');  // Change button style to show it's disabled
        }

        // Confirm Review Function
        function confirmReview(reportId) {
            var confirmed = confirm('Mark this report as Reviewed?');
            if (confirmed) {
                // Submit the form using AJAX
                var form = document.getElementById('review-form-' + reportId);
                var button = form.querySelector('button'); // Get the button inside the form

                if (!form || !button) {
                    alert("Form or button not found.");
                    return;
                }

                var formData = new FormData(form);

                fetch(form.action, {
                    method: 'POST',
                    body: formData
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        // Disable the button and update its text and style
                        disableButton(button);
                    } else {
                        alert('Error: Could not mark the report as reviewed.');
                    }
                })
                .catch(error => {
                    alert('Error submitting the review: ' + error);
                });
            }
        }

        // Adding event listeners dynamically (if not already added in HTML)
        document.addEventListener("DOMContentLoaded", function() {
            const reviewButtons = document.querySelectorAll('[data-action="confirmReview"]');
            reviewButtons.forEach(button => {
                button.addEventListener('click', function() {
                    var reportId = this.getAttribute('data-report-id');
                    confirmReview(reportId);
                });
            });
        });
    </script>
</head>
<body>

<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>

<div class="container mt-4">
    <h2 class="mb-3">📄 Review Lab Reports</h2>

    <c:if test="${empty reports}">
        <div class="alert alert-info" role="alert">
            No lab reports found for your assigned patients.
        </div>
    </c:if>

    <c:if test="${not empty reports}">
        <div class="table-responsive">
            <table class="table table-bordered table-striped align-middle">
                <thead class="table-dark">
                    <tr>
                        <th>#</th>
                        <th>Patient Name</th>
                        <th>Test Name</th>
                        <th>Result Summary</th>
                        <th>Status</th>
                        <th>Report Date</th>
                        <th>Download</th>
                        <th>Review</th> <!-- ✅ New Column -->
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="report" items="${reports}" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>${report.patientName}</td>
                            <td>${report.labTest.testName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${fn:length(report.result) > 50}">${fn:substring(report.result, 0, 50)}...</c:when>
                                    <c:otherwise>${report.result}</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <span class="badge
                                    <c:choose>
                                        <c:when test="${report.status == 'COMPLETED'}">bg-success</c:when>
                                        <c:when test="${report.status == 'PENDING'}">bg-warning</c:when>
                                        <c:otherwise>bg-secondary</c:otherwise>
                                    </c:choose>">
                                    ${report.status}
                                </span>
                            </td>
                            <td>${report.formattedDate}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${not empty report.pdfPath}">
                                        <a href="${pageContext.request.contextPath}/doctor/download-report/${report.id}"
                                           class="btn btn-sm btn-primary" target="_blank">
                                            Download PDF
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">N/A</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <!-- ✅ Review Column -->
                           <td>
                               <c:choose>
                                   <c:when test="${report.reviewed}">
                                       <span class="badge bg-success">Reviewed</span>
                                   </c:when>
                                   <c:otherwise>
                                       <form action="${pageContext.request.contextPath}/doctor/mark-reviewed/${report.id}" method="post" id="review-form-${report.id}">
                                           <button type="button" class="btn btn-sm btn-success"
                                                   data-action="confirmReview" data-report-id="${report.id}">
                                               Done
                                           </button>
                                       </form>
                                   </c:otherwise>
                               </c:choose>
                           </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>

</body>
</html>
