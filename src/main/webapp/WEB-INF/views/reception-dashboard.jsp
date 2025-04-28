<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.medipath.labcare.entity.User" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="Receptionist Dashboard" />

<%
    User user = (User) session.getAttribute("loggedInUser");
    request.setAttribute("username", user.getUsername());
%>
<%
    if (session.getAttribute("loggedInUser") == null) {
        session.setAttribute("error", "Please login first.");
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>


<div class="container-fluid">
    <div class="row">
        <div class="col-md-2 bg-light min-vh-100">
            <%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>
        </div>
        <div class="col-md-10 p-4">
            <h2 class="mb-4">Welcome Receptionist, ${username}!</h2>
            <div class="list-group">
              <!--  <a href="${pageContext.request.contextPath}/register-patient" class="list-group-item list-group-item-action">📝 Register New Patient</a>
                <a href="${pageContext.request.contextPath}/manage-appointments" class="list-group-item list-group-item-action">📅 Manage Appointments</a>
				-->
                <a href="${pageContext.request.contextPath}/receptionist/patient-enquiries" class="list-group-item list-group-item-action">❓ Patient Enquiries</a>
                <a href="${pageContext.request.contextPath}/receptionist/billing" class="list-group-item list-group-item-action">💳 Generate Bills</a>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
