package com.example.closeup.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {
    @GetMapping("/main")
    public String getMain() {
        return "admin/main";
    }

    @GetMapping("login")
    public String getLogin() {
        return "admin/login";
    }

    @GetMapping("users")
    public String getUsers() {
        return "admin/manage/member";
    }

    @GetMapping("posts")
    public String getPosts() {
        return "admin/manage/post";
    }

    @GetMapping("reviews")
    public String getReviews() {
        return "admin/manage/review";
    }

}
