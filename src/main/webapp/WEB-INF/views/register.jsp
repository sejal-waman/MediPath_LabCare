<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
    if (session.getAttribute("loggedInUser") == null) {
        session.setAttribute("error", "Please login first.");
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - MediPath LabCare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="${pageContext.request.contextPath}/js/theme-toggle.js" defer></script>
    <style>
        /* Styling omitted for brevity */
    </style>
</head>

<body>
    <!-- 🌗 Theme toggle button -->
    <button id="themeToggleBtn" class="theme-toggle-btn" title="Toggle dark/light mode">
        <svg id="sunIcon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" viewBox="0 0 24 24" style="display:none;">
            <path d="M12 4.354a1 1 0 0 1 1-1h.003a1 1 0 0 1 .997 1.004A7.645 7.645 0 1 1 12 4.354zm0 2.292a5.354 5.354 0 1 0 0 10.708 5.354 5.354 0 0 0 0-10.708z"/>
        </svg>
        <svg id="moonIcon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" viewBox="0 0 24 24">
            <path d="M21 12.79A9 9 0 0 1 11.21 3 7 7 0 1 0 21 12.79z"/>
        </svg>
    </button>

    <main class="form-box">
        <h2>Register for MediPath</h2>

        <c:if test="${not empty error}">
            <p style="color: red; text-align:center;"><c:out value="${error}"/></p>
        </c:if>
        <c:if test="${not empty message}">
            <p style="color: blue; text-align:center;"><c:out value="${message}"/></p>
        </c:if>
        <c:if test="${not empty success}">
            <p style="color: green; text-align:center;"><c:out value="${success}"/></p>
        </c:if>

        <form:form id="registerForm" method="POST" action="${pageContext.request.contextPath}/processRegister" modelAttribute="user">
            <label for="username">Username:</label>
            <form:input path="username" id="username" class="form-control" placeholder="Enter your username" /><br/>

            <label for="email">Email:</label>
            <form:input path="email" id="email" class="form-control" placeholder="Enter your email" /><br/>

            <label for="password">Password:</label>
            <form:password path="password" id="password" class="form-control" placeholder="Create a password" /><br/>

            <label for="mobileNo">Mobile Number:</label>
            <form:input path="mobileNo" id="mobileNo" class="form-control" placeholder="Enter your mobile number" /><br/>

           <!----- <label for="roles">Select Roles:</label>
            <div class="roles-container">
                <c:forEach var="r" items="${roles}">
                    <label>
                        <input type="radio" name="roles" value="${r.id}" /> ${r.name}
                    </label>
                    <br/>
                </c:forEach>
            </div> ---->
			
			<label for="roles">Select Roles:</label>
			<form:select path="roles" id="roles" class="form-control">
			    <option value="">Select a Role</option>
			    <c:forEach var="r" items="${roles}">
			        <option value="${r.id}">${r.name}</option>
			    </c:forEach>
			</form:select>
			
			


            <input type="submit" value="Register" class="btn"/>
        </form:form>

        <p>Already have an account? 
            <a href="${pageContext.request.contextPath}/login" class="btn">Login</a>
        </p>
    </main>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const toggleBtn = document.querySelector(".theme-toggle-btn");
            const body = document.body;
            if (localStorage.getItem("dark-mode") === "true") {
                body.classList.add("dark-mode");
            }
            toggleBtn?.addEventListener("click", () => {
                body.classList.toggle("dark-mode");
                localStorage.setItem("dark-mode", body.classList.contains("dark-mode"));
            });
        });
    </script>
</body>
</html>
