<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>

<%
    if (session.getAttribute("loggedInUser") == null) {
        session.setAttribute("error", "Please login first.");
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>


<!-- Page title for layout -->
<c:set var="pageTitle" value="Admin Dashboard" />

 <div class="container-fluid">
    <div class="row">
        <!-- Sidebar Section -->
        <div class="col-md-2 bg-light min-vh-100">
            <%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>
        </div>

        <!-- Main Content Area -->
        <div class="col-md-10 p-4">
            <!-- Welcome Heading -->
            
			<!-- Welcome Heading -->
			<h2 class="mb-4">
			    <c:choose>
			        <c:when test="${not empty sessionScope.loggedInUser}">
			            👋 Welcome, Admin <br> ${sessionScope.loggedInUser.username}!
			        </c:when>
			        <c:otherwise>
			            👋 Welcome, Admin!
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
                <a href="${pageContext.request.contextPath}/admin/manage-users" class="list-group-item list-group-item-action">
                    👥 Manage Users
                </a>
                <a href="${pageContext.request.contextPath}/admin/manage-tests" class="list-group-item list-group-item-action">
                    🧪 Manage Lab Tests
                </a>
                <a href="${pageContext.request.contextPath}/admin/view-reports" class="list-group-item list-group-item-action">
                    📊 View Reports
                </a>
                <a href="${pageContext.request.contextPath}/admin/settings" class="list-group-item list-group-item-action">
                    ⚙️ System Settings
                </a>
            </div>
         </div>
     </div>
 </div>

 <%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
