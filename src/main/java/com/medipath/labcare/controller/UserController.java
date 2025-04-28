package com.medipath.labcare.controller;

import com.medipath.labcare.entity.Role;
import com.medipath.labcare.entity.User;
import com.medipath.labcare.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 🔐 Session check utility to ensure admin role
    private boolean isAdminLoggedIn(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        return loggedInUser != null && hasRole(loggedInUser, "ADMIN");
    }

    // 🔒 Helper Method to Check if User has Specific Role
    private boolean hasRole(User user, String roleName) {
        if (user == null) return false;
        Set<Role> roles = user.getRoles();
        return roles.stream().anyMatch(role -> role.getName().equalsIgnoreCase(roleName));
    }

    // 📋 Display list of users
    @GetMapping
    public String listUsers(Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/login";
        }
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user-list";
    }

    // ➕ Show form to add a new user
    @GetMapping("/new")
    public String showAddUserForm(Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/login";
        }
        model.addAttribute("user", new User());
        return "admin/user-form";
    }

    // ✏️ Show form to edit an existing user
    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/login";
        }
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "admin/user-form";
    }

    // 💾 Save user (add or update)
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/login";
        }
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    // ❌ Delete user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/login";
        }
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
