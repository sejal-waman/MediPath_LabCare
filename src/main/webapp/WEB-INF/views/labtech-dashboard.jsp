<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.medipath.labcare.entity.User" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="Lab Technician Dashboard" />


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
            <!-- Welcome Heading -->
            <h2 class="mb-4">
                <c:choose>
                    <c:when test="${not empty sessionScope.loggedInUser}">
                        👋 Welcome, Lab Tech <br> ${sessionScope.loggedInUser.username}!
                    </c:when>
                    <c:otherwise>
                        👋 Welcome, Lab Tech!
                    </c:otherwise>
                </c:choose>
            </h2>

            <!-- Roles Section -->
            <c:if test="${not empty user and not empty user.roles}">
                <h5>Your Roles:</h5>
                <ul class="list-unstyled">
                    <c:forEach var="r" items="${user.roles}">
                        <li>🔰 ${r.name}</li>
                    </c:forEach>
                </ul>
            </c:if>

            <!-- Navigation Section -->
            <div class="list-group mt-4 shadow-sm">
                <a href="${pageContext.request.contextPath}/labtech/add-results" class="list-group-item list-group-item-action">🧪 Enter Test Results</a>
                <a href="${pageContext.request.contextPath}/labtech/manage-samples" class="list-group-item list-group-item-action">🔬 Manage Lab Samples</a>
                <a href="${pageContext.request.contextPath}/labtech/upload-report" class="list-group-item list-group-item-action">📤 Upload Reports</a>
                <a href="${pageContext.request.contextPath}/labtech/equipment" class="list-group-item list-group-item-action">🛠 Equipment Status</a>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
