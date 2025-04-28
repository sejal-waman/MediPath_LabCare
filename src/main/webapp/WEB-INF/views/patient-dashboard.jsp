<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.medipath.labcare.entity.User" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="Patient Dashboard" />

<%
    User user = (User) session.getAttribute("loggedInUser");
    request.setAttribute("username", user.getUsername());
%>

<div class="container-fluid">
    <div class="row">
        <!-- Sidebar Section -->
        <div class="col-md-2 bg-light min-vh-100">
            <%@ include file="/WEB-INF/views/fragments/sidebar.jsp" %>
        </div>

        <!-- Main Content Section -->
        <div class="col-md-10 p-4">
            <!-- Welcome Message -->
            <h2 class="mb-4">
                <c:choose>
                    <c:when test="${not empty sessionScope.loggedInUser}"> 
                        <c:forEach var="r" items="${sessionScope.loggedInUser.roles}">
                            <c:if test="${r.name eq 'PATIENT'}">
                                👨‍⚕️ Welcome, Patient. <br>
                                ${sessionScope.loggedInUser.username}!
                            </c:if>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        👨‍⚕️ Welcome, Patient!
                    </c:otherwise>
                </c:choose>
            </h2>

            <!-- Display Patient's Age and Gender -->
            <c:if test="${not empty sessionScope.loggedInUser}">
                <div class="alert alert-info">
                    <h4>Your Details:</h4>
                    <p>
                        <strong>Age:</strong> ${sessionScope.loggedInUser.age} <br>
                        <strong>Gender:</strong> ${sessionScope.loggedInUser.gender} <br>
                    </p>
                </div>
            </c:if>

            <!-- Form to Update Age and Gender -->
            <h4 class="mt-4">Update Your Details</h4>
            <form action="${pageContext.request.contextPath}/patient/update-details" method="post">
                <div class="form-group">
                    <label for="age">Age:</label>
                    <input type="number" class="form-control" id="age" name="age" value="${sessionScope.loggedInUser.age}" required>
                </div>

                <div class="form-group">
                    <label for="gender">Gender:</label>
                    <select class="form-control" id="gender" name="gender" required>
                        <option value="Male" ${sessionScope.loggedInUser.gender == 'Male' ? 'selected' : ''}>Male</option>
                        <option value="Female" ${sessionScope.loggedInUser.gender == 'Female' ? 'selected' : ''}>Female</option>
                        <option value="Other" ${sessionScope.loggedInUser.gender == 'Other' ? 'selected' : ''}>Other</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Update Details</button>
				<br><br>
            </form>

            <!-- Assigned Doctor Section -->
            <c:if test="${not empty assignedDoctor}">
                <div class="alert alert-info">
                    <h4>Your Assigned Doctor:</h4>
                    <p>
                        <strong>Doctor's Name:</strong> ${assignedDoctor.username} <br>
                        <strong>Specialization:</strong> ${assignedDoctor.specialization}
                    </p>
                </div>
            </c:if>
			
			
			
			<!-- Patient Dashboard Links -->
			           <div class="list-group mt-4">
			               <a href="${pageContext.request.contextPath}/patient/my-tests" class="list-group-item list-group-item-action">
			                   🧾 View My Test Reports
			               </a>
			               <a href="${pageContext.request.contextPath}/patient/appointments" class="list-group-item list-group-item-action">
			                   📆 Track Appointments
			               </a>
						   
						   
						   <a href="${pageContext.request.contextPath}/patient/send-enquiries" class="list-group-item list-group-item-action">
							✉️ Send Enquiry
						   </a>

			              
			               <a href="${pageContext.request.contextPath}/patient/my-profile" class="list-group-item list-group-item-action">
			                   👤 Update Profile
			               </a>
			           </div>

            <!-- Available Doctors Section -->
            <h3 class="mt-5 mb-3">Available Doctors</h3>

            <table class="table table-hover table-striped table-bordered">
                <thead class="thead-dark">
                    <tr>
                        <th>Doctor Name</th>
                        <th>Specialization</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="doctor" items="${doctors}">
                        <tr>
                            <td>${doctor.username}</td>
                            <td>${doctor.specialization}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/patient/assign-doctor" method="post" style="display:inline;">
                                    <input type="hidden" name="doctorId" value="${doctor.id}" />
                                    <button type="submit" class="btn btn-success btn-sm">
                                        Assign
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

           
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
