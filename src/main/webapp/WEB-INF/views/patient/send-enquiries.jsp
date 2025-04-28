<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.medipath.labcare.entity.PatientEnquiry" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<c:set var="pageTitle" value="Send Enquiry" />

<div class="container-fluid">
    <div class="row">
        <!-- Sidebar Section -->
        <div class="col-md-2 bg-light min-vh-100">
            <%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>
        </div>

        <!-- Main Content Section -->
        <div class="col-md-10 p-4">
            <!-- Page Title -->
            <h2 class="mb-4">Send Enquiry</h2>

            <!-- Enquiry Form -->
            <form action="${pageContext.request.contextPath}/patient/send-enquiries" method="post">
                <div class="form-group">
                    <label for="subject">Subject:</label>
                    <input type="text" class="form-control" id="subject" name="subject" required>
                </div>

                <div class="form-group">
                    <label for="message">Message:</label>
                    <textarea class="form-control" id="message" name="message" rows="4" required></textarea>
                </div>

                <button type="submit" class="btn btn-primary">Send Enquiry</button>
            </form>

            <!-- Success/Failure Message -->
            <c:if test="${not empty message}">
                <div class="alert alert-success mt-4">
                    ${message}
                </div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger mt-4">
                    ${errorMessage}
                </div>
            </c:if>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
