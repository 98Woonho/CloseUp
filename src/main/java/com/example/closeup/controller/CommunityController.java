package com.example.closeup.controller;


import com.example.closeup.domain.dto.community.ArticleDto;
import com.example.closeup.domain.dto.community.BoardDto;
import com.example.closeup.domain.dto.community.CommentDto;
import com.example.closeup.service.CommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("board/community")
public class CommunityController {
    @Autowired
    CommunityService communityService;
    // 커뮤니티 메인 페이지
    @GetMapping("/communityMain")
    public String communityMain(@RequestParam(required = false) String boardCode, Model model) {
        List<ArticleDto> articles = communityService.getArticles(boardCode);
        List<BoardDto> boards = communityService.getAllBoards();
        model.addAttribute("articles", articles);
        model.addAttribute("boards", boards);
        return "/board/community/communityMain";
    }

    @GetMapping("/communityWrite")
    public String communityWriteForm(Model model) {
        List<BoardDto> boards = communityService.getAllBoards();
        model.addAttribute("boards", boards);
        model.addAttribute("articleDto", new ArticleDto());
        return "/board/community/communityWrite";
    }


    @PostMapping("/communityWrite")
    public String communityWrite(
            @ModelAttribute ArticleDto articleDto,
//            @RequestParam(value = "files", required = false) MultipartFile[] files,
            Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = authentication.getName();
            articleDto.setUserId(userId);
        } else {
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
        List<CommentDto> comments = communityService.getCommentsByArticleId(id);
//        List<ArticleFileDto> files = communityService.getFilesByArticleId(id);
        model.addAttribute("article", article);
        model.addAttribute("comments", comments);
//        model.addAttribute("files", files);
        model.addAttribute("commentDto", new CommentDto());
        return "/board/community/communityPost";
    }


    // 댓글 작성 처리
    @PostMapping("/communityPost/{id}")
    public String communityPostComment(
            @PathVariable("id") Integer articleId,
            CommentDto commentDto) {
        communityService.createComment(commentDto);
        System.out.println(commentDto);
        log.info(commentDto.toString());
        // 댓글 작성 후 해당 게시글 상세보기 페이지로 리다이렉트
        return "redirect:/board/community/communityPost/" + articleId;
    }


    @PostMapping("/communityPost/{id}/like")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> likeArticle(@PathVariable Integer id, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId = authentication.getName();
        boolean liked = communityService.toggleLike(id, userId);
        int likeCount = communityService.getLikeCount(id);
        Map<String, Object> response = new HashMap<>();
        response.put("liked", liked);
        response.put("likeCount", likeCount);
        return ResponseEntity.ok(response);
    }
}

