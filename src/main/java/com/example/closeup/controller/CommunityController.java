package com.example.closeup.controller;


import com.example.closeup.domain.dto.ArticleDto;
import com.example.closeup.domain.dto.CommentDto;
import com.example.closeup.service.CommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    // 커뮤니티 메인 페이지
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
    public String communityWrite(
            ArticleDto articleDto,
            Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = authentication.getName();
            articleDto.setUserId(userId);
        } else {
            // 인증되지 않은 경우 처리
            return "redirect:/user/login";
        }
        communityService.createArticle(articleDto);
        return "redirect:/board/community/communityMain";
    }

    @GetMapping("/communityPost/{id}")
    public String communityView(
            @PathVariable Integer id,
            Model model) {
        ArticleDto article = communityService.getArticleById(id);
        System.out.println(article);
        model.addAttribute("article", article);
        model.addAttribute("comments", communityService.getCommentsByArticleId(id));
        model.addAttribute("commentDto", new CommentDto());
        return "/board/community/communityPost";
    }


    // 댓글 작성 처리
    @PostMapping("/communityPost/{id}")
    public String communityPostComment(CommentDto commentDto) {
        communityService.createComment(commentDto);
        System.out.println(commentDto);
        log.info(commentDto.toString());
        // 댓글 작성 후 해당 게시글 상세보기 페이지로 리다이렉트
        return "redirect:/board/community/communityPost/" + commentDto.getArticleId();
    }
}
