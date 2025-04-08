package com.callsprediction.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {


    @GetMapping("/req/login")
    public String login(){
        return "login";
    }

    @GetMapping("/req/signup")
    public String signup(){
        return "signup";
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/admin/login")
    public String showAdminLoginForm(){
        return "admin_login";
    }

    @GetMapping("/superadmin/login")
    public String showSuperAdminLoginForm(){
        return "superadmin_login";
    }

    @GetMapping("/admin/signup")
    public String showAdminSignupForm() {
        return "admin_signup"; // admin_signup.html
    }

    @GetMapping("/superadmin/signup")
    public String showSuperAdminSignupForm() {
        return "superadmin_signup"; // superadmin_signup.html
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin_dashboard";  // Thymeleaf view for admin
    }

    @GetMapping("/superadmin/dashboard")
    public String superadminDashboard() {
        return "superadmin_dashboard"; // Thymeleaf view for super admin
    }




}
