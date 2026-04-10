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
    public String showLoginPage() {
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

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
