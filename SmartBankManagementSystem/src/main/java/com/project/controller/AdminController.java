package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.entity.Admin;
import com.project.service.AdminService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ADMIN LOGIN PAGE
    @GetMapping("/admin")
    public String adminLoginPage() {
        return "adminlogin";
    }

    // ADMIN LOGIN
    @PostMapping("/adminLogin")
    public String adminLogin(String email, String password, Model model, HttpSession session) {
        Admin admin = adminService.loginAdmin(email, password);
        if(admin != null) {
            session.setAttribute("admin", admin);
            model.addAttribute("loans", adminService.getAllLoans());
            return "adminDashboard";
        } else {
            model.addAttribute("error", "Invalid Credentials");
            return "adminlogin";
        }
    }

    // APPROVE LOAN
    @GetMapping("/approveLoan")
    public String approveLoan(@RequestParam int id, Model model) {
        adminService.approveLoan(id);
        model.addAttribute("loans", adminService.getAllLoans());
        return "adminDashboard";
    }

    // REJECT LOAN
    @GetMapping("/rejectLoan")
    public String rejectLoan(@RequestParam int id, Model model) {
        adminService.rejectLoan(id);
        model.addAttribute("loans", adminService.getAllLoans());
        return "adminDashboard";
    }

    // VIEW USERS
    @GetMapping("/viewUsers")
    public String viewUsers(Model model) {
        model.addAttribute("users",adminService.getAllUsers());
        return "users";
    }
}