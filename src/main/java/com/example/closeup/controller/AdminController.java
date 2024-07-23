package com.example.closeup.controller;

import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.domain.dto.community.ArticleDto;
import com.example.closeup.domain.dto.community.BoardDto;
import com.example.closeup.service.AdminService;
import com.example.closeup.service.CommunityService;
import com.example.closeup.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminService adminService;
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
        List<UserDto> users = adminService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/manage/member";
    }
    @GetMapping("/manage/member/{role}")
    public String getMembersByRole(@PathVariable String role, Model model) {
        List<UserDto> users = adminService.getUsersByRole(role);
        model.addAttribute("users", users);
        return "admin/manage/member";
    }

    @GetMapping("/manage/member/search")
    public String searchUsers(@RequestParam String name,
                              @RequestParam String id,
                              @RequestParam String phone,
                              @RequestParam String expertStatus,
                              Model model) {
        List<UserDto> users = adminService.searchUsers(name, id, phone, expertStatus);
        model.addAttribute("users", users);
        return "admin/manage/member";
    }
    @GetMapping("/manage/member/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        adminService.deleteUser(id);
        return "redirect:/admin/manage/member";
    }

    @GetMapping("/manage/post")
    public String getPosts(Model model) {
    List<ArticleDto> articles = adminService.getAllArticles();
    List<BoardDto> boards = communityService.getAllBoards();
    model.addAttribute("boards", boards);
    model.addAttribute("articles", articles);
        return "admin/manage/post";
    }

    @GetMapping("/manage/post/{boardCode}")
    public String getPostsByBoardCode(
            @PathVariable String boardCode,
            Model model
    ){
        List<ArticleDto> articles = adminService.getAllArticlesByBoardCode(boardCode);
        model.addAttribute("articles", articles);
        return "admin/manage/post";
    }
    @GetMapping("/manage/post/search")
    public String searchArticles(
            @RequestParam String userId,
            @RequestParam String title,
            @RequestParam String dateTime,
            @RequestParam String content,
            Model model

    ){
        List<ArticleDto> articles = adminService.searchArticles(userId, title, dateTime, content);
        model.addAttribute("articles", articles);
        return "admin/manage/post";
    }

    @GetMapping("/manage/post/delete/{id}")
    public String deleteArticle(@PathVariable String id) {
        adminService.deleteArticle(id);
        log.info("딜리트실행");
        return "redirect:/admin/manage/post";
    }

    @GetMapping("/manage/review")
    public String getReviews() {
        return "admin/manage/review";
    }

}
