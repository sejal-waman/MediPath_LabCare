package com.medipath.labcare.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // URLs that are allowed without login
        String[] allowedPaths = { "/login", "/register", "/processLogin", "/css/", "/js/", "/images/" };

        String path = request.getRequestURI();

        for (String allowed : allowedPaths) {
            if (path.startsWith(request.getContextPath() + allowed)) {
                return true; // allow login, register, static resources
            }
        }

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("loggedInUser") != null) {
            return true; // User is logged in
        }

        // User not logged in -> redirect to login page with message
        session = request.getSession(true);
        session.setAttribute("error", "Please login first.");
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }
}

