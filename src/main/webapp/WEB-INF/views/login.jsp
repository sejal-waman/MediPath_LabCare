<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <title>Login - MediPath LabCare</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="${pageContext.request.contextPath}/js/theme-toggle.js" defer></script>
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
        <h2>Login to MediPath</h2>

        <!-- 🔔 Flash messages for errors and success -->
        <c:if test="${not empty error}">
            <p style="color: red; text-align: center;"><c:out value="${error}" /></p>
        </c:if>
        <c:if test="${not empty message}">
            <p style="color: blue; text-align: center;"><c:out value="${message}" /></p>
        </c:if>

        <!-- 📄 Plain HTML form (no spring:form needed) -->
        <form id="loginForm" method="POST" action="${pageContext.request.contextPath}/processLogin">
            <div class="form-group">
                <label for="email">Email</label>
                <input name="email" id="email" type="email" class="form-control" placeholder="Enter your email" required="required" />
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input name="password" id="password" type="password" class="form-control" placeholder="Enter your password" required="required" /><br/>
            </div>

            <div class="form-group">
                <input type="submit" value="Login" class="btn btn-primary" />
            </div>
        </form>

        <p>Don't have an account? 
            <a href="${pageContext.request.contextPath}/register" class="btn">Register</a>
        </p>
    </main>

    <!-- 🌙 Theme & Validation Script -->
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

            // 🛡️ Client-side validation
            const loginForm = document.getElementById("loginForm");
            loginForm.addEventListener("submit", function (e) {
                const email = this.email.value.trim();
                const password = this.password.value.trim();

                if (email.length === 0 || !email.includes('@')) {
                    alert("Please enter a valid email address.");
                    e.preventDefault();
                } else if (password.length < 6) {
                    alert("Password must be at least 6 characters long.");
                    e.preventDefault();
                }
            });
        });
    </script>
</body>
</html>
