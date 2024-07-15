package com.example.closeup.controller;


import com.example.closeup.domain.dto.ArticleDto;
import com.example.closeup.service.CommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("board/community")
public class CommunityController {
    @Autowired
    CommunityService communityService;

    @GetMapping("/communityMain")
    public String communityMain(Model model) {
        List<ArticleDto> articles = communityService.getAllArticles();
        model.addAttribute("articles", articles);
        return "/board/community/communityMain";
    }

    @GetMapping("/communityWrite")
    public String communityWriteForm(Model model) {
        model.addAttribute("articleDto", new ArticleDto());
        return "/board/community/communityWrite";
    }

    @PostMapping("/communityWrite")
    public String communityWrite(@ModelAttribute ArticleDto articleDto) {
        communityService.createArticle(articleDto);
        return "redirect:/board/community/communityMain";
    }

    @GetMapping("/view/{id}")
    public String communityView(@PathVariable Long id, Model model) {
        ArticleDto article = communityService.getArticleById(id);
        model.addAttribute("article", article);
        return "community/view";
    }
}
