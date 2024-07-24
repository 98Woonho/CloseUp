package com.example.closeup.controller;

import com.example.closeup.domain.dto.community.ArticleDto;
import com.example.closeup.service.CommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private CommunityService communityService;

    @GetMapping("/findExpertMain")
    public String getFindExpertMain() {
        return "board/findExpert/findExpertMain";
    }

    @GetMapping("/findExpertPost")
    public String getFindExpertPost() {
        return "board/findExpert/findExpertPost";
    }

    @GetMapping("/findExpertWrite")
    public String getFindExpertWrite() {
        return "board/findExpert/findExpertWrite";
    }

    @GetMapping("/CSCenter/CSMain")
    public String getCSMain(Model model) {
        List<ArticleDto> articles = communityService.getArticlesByBoardCode("customerService");
        model.addAttribute("articles", articles);
        return "board/CSCenter/CSMain";
    }

    @GetMapping("/CSCenter/CSWrite")
    public String getCSPost() {
        return "board/CSCenter/CSWrite";
    }

    @PostMapping("/CSCenter/CSWrite")
    public String postCSArticle(@ModelAttribute ArticleDto articleDto, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = authentication.getName();
            articleDto.setUserId(userId);
        } else {
            return "redirect:/user/login";
        }
        articleDto.setBoardCode("customerService");
        communityService.createArticle(articleDto);
        return "redirect:/board/CSCenter/CSMain";
    }

    @GetMapping("/CSCenter/CSPost")
    public String getCSWrite(){
        return "board/CSCenter/CSPost";
    }

    @PostMapping("/CSCenter/CSPost")
    public String postCSWrite() {

        return "board/CSCenter/CSPost";
    }
}

