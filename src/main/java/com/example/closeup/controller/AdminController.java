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
        return "admin/admin_login";
    }

    // admin_main은 무슨 페이지?

    @GetMapping("users")
    public String getUsers() {
        return "member";
    }

    @GetMapping("posts")
    public String getPosts() {
        return "post";
    }

    @GetMapping("reviews")
    public String getReviews() {
        return "review";
    }
}
