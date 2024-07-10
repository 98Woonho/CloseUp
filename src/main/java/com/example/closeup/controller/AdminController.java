package com.example.closeup.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @GetMapping("login")
    public String getLogin() {
        return "admin/adminLogin";
    }

    @GetMapping("users")
    public String getUsers() {
        return "admin/admin_manage_member";
    }

    @GetMapping("posts")
    public String getPosts() {
        return "admin/admin_manage_post";
    }

    @GetMapping("reviews")
    public String getReviews() {
        return "admin/admin_manage_review";
    }
}
