<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>



<!DOCTYPE html>
<html>
<head>
    <title>Patient Enquiries</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
</head>
<body>

<div class="main-content" style="margin-left: 250px; padding: 20px;">

    <div class="container mt-5">
        <h2 class="mb-4">❓ Patient Enquiries</h2>

        <!-- Patient Enquiries Table -->
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Patient Name</th>
                    <th>Message</th> <!-- Replaced Enquiry Details with Message -->
                    <th>Action</th>  <!-- Action Column -->
                </tr>
            </thead>
            <tbody>
                <!-- Iterate through the enquiries list -->
                <c:forEach var="enquiry" items="${enquiries}">
                    <tr>
                        <td>${enquiry.id}</td>
                        <td>${enquiry.patientName}</td>
                        <td>${enquiry.message}</td> <!-- Show the Message instead of Enquiry Details -->
                        
                        <td>
                            <c:choose>
                                <c:when test="${enquiry.status eq 'Pending'}">
                                    <!-- Only show Solve button if status is 'Pending' -->
                                    <form action="${pageContext.request.contextPath}/receptionist/solve-enquiry" method="post">
                                        <input type="hidden" name="enquiryId" value="${enquiry.id}">
                                        <button type="submit" class="btn btn-success">Solve</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <!-- Disable button if the enquiry is already solved -->
                                    <button class="btn btn-secondary" disabled>Solved</button>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </div>

</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>

<script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>

</body>
</html>
