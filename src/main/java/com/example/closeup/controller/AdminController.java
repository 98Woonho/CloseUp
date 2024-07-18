package com.example.closeup.controller;

import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.domain.dto.community.ArticleDto;
import com.example.closeup.service.CommunityService;
import com.example.closeup.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    CommunityService communityService;
    @GetMapping("/main")
    public String getMain() {
        return "admin/main";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "admin/login";
    }

    @GetMapping("/manage/member")
    public String getUsers(Model model) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/manage/member";
    }

    @GetMapping("/manage/post")
    public String getPosts(Model model) {
    List<ArticleDto> articles = communityService.getAllArticles();
    model.addAttribute("articles", articles);
        return "admin/manage/post";
    }

    @GetMapping("/manage/review")
    public String getReviews() {
        return "admin/manage/review";
    }

}
