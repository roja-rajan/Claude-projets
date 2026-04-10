package com.example.loginpage.controller;

import com.example.loginpage.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    // Show Login Page
    @GetMapping("/")
    public String showLoginPage(@RequestParam(required = false) String success, Model model) {
        if (success != null) {
            model.addAttribute("success", success);
        }
        return "login";
    }

    // Handle Login Form Submission
    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                               @RequestParam String password,
                               Model model,
                               HttpSession session) {

        if (authService.authenticate(username, password)) {
            // Save username in session
            session.setAttribute("loggedInUser", username);
            return "redirect:/welcome";
        } else {
            model.addAttribute("error", "Invalid username or password. Please try again.");
            return "login";
        }
    }

    // Show Welcome Page
    @GetMapping("/welcome")
    public String showWelcomePage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUser");

        // If not logged in, redirect to login
        if (username == null) {
            return "redirect:/";
        }

        model.addAttribute("username", username);
        return "welcome";
    }

    // Show Signup Page
    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup";
    }

    // Handle Signup Form Submission
    @PostMapping("/signup")
    public String handleSignup(@RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String confirmPassword,
                                Model model) {

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match. Please try again.");
            return "signup";
        }

        // Check if username is empty
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            model.addAttribute("error", "Username and password cannot be empty.");
            return "signup";
        }

        // Try to register
        if (authService.register(username, password)) {
            // Success — redirect to login with success message
            return "redirect:/?success=Account created successfully! Please log in.";
        } else {
            model.addAttribute("error", "Username already exists. Please choose another.");
            return "signup";
        }
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
