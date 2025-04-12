package com.callsprediction.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    // Handle requests for specific dashboards
    @GetMapping("/admin/dashboard")
    public String showAdminDashboard() {
        return "admin_dashboard";  // Return the admin dashboard view (HTML)
    }

    @GetMapping("/developer/dashboard")
    public String showDeveloperDashboard() {
        return "developer_dashboard";  // Return the developer dashboard view (HTML)
    }

    @GetMapping("/general/dashboard")
    public String showGeneralDashboard() {
        return "general_dashboard";  // Return the general user dashboard view (HTML)
    }
}
