package com.example.closeup.controller;


import com.example.closeup.domain.dto.community.ArticleDto;
import com.example.closeup.domain.dto.community.BoardDto;
import com.example.closeup.domain.dto.community.CommentDto;
import com.example.closeup.service.CommunityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public String communityMain(
            @RequestParam(required = false) String boardCode,
            Model model) {
        List<ArticleDto> articles = communityService.getArticles(boardCode);
        List<BoardDto> boards = communityService.getAllBoards();
        model.addAttribute("articles", articles);
        model.addAttribute("boards", boards);
        return "/board/community/communityMain";
    }

    @PostMapping("/communityMain")
    public String communityMainPost(
            @RequestParam(required = false) String keyword,
            Model model) {
        List<ArticleDto> articles = communityService.getArticlesByTitle(keyword);
        List<BoardDto> boards = communityService.getAllBoards();
        model.addAttribute("articles", articles);
        model.addAttribute("boards", boards);
        model.addAttribute("title", keyword);
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

    @GetMapping("/communityPost/{articleId}")
    public String communityView(
            @PathVariable Integer articleId,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
            ) {
        ArticleDto article = communityService.getArticleById(articleId, request, response);
        List<CommentDto> comments = communityService.getCommentsByArticleId(articleId);
        List<BoardDto> boards = communityService.getAllBoards();
//        List<ArticleFileDto> files = communityService.getFilesByArticleId(id);
        model.addAttribute("boards", boards);
        model.addAttribute("article", article);
        model.addAttribute("comments", comments);
//        model.addAttribute("files", files);
        model.addAttribute("commentDto", new CommentDto());
        return "/board/community/communityPost";
    }


    // 댓글 작성 처리
    @PostMapping("/communityPost/{articleId}")
    public String communityPostComment(
            @PathVariable Integer articleId,
            CommentDto commentDto,
            Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = authentication.getName();
            commentDto.setUserId(userId);
        } else {
            return "redirect:/user/login";
        }
        commentDto.setArticleId(articleId);
        communityService.createComment(commentDto);
        return "redirect:/board/community/communityPost/" + articleId;
    }


    @PostMapping("/communityPost/{articleId}/like")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> likeArticle(
            @PathVariable Integer articleId,
            Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId = authentication.getName();
        boolean liked = communityService.toggleLike(articleId, userId);
        int likeCount = communityService.getLikeCount(articleId);
        Map<String, Object> response = new HashMap<>();
        response.put("liked", liked);
        response.put("likeCount", likeCount);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/comment/{commentId}")
    @ResponseBody
    public ResponseEntity<?> deleteComment(@PathVariable Integer commentId,
                                           Authentication authentication) {
        try {
            CommentDto comment = communityService.getCommentById(commentId);
            if (comment == null) {
                return ResponseEntity.notFound().build();
            }

            // 현재 로그인한 사용자와 댓글 작성자가 같은지 확인
            if (!comment.getUserId().equals(authentication.getName())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글을 삭제할 권한이 없습니다.");
            }

            communityService.deleteComment(commentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 삭제 실패");
        }
    }

}

