package com.example.closeup.controller;


import com.example.closeup.domain.dto.community.ArticleDto;
import com.example.closeup.domain.dto.community.ArticleFileDto;
import com.example.closeup.domain.dto.community.BoardDto;
import com.example.closeup.domain.dto.community.CommentDto;
import com.example.closeup.service.CommunityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("board/community")
public class CommunityController {
    @Autowired
    CommunityService communityService;

    /**************게시글 목록 조회***************/
    @GetMapping("/communityMain")
    public String communityMain(
            @RequestParam(required = false) String boardCode,
            @RequestParam(required = false) String keyword,
            Model model) {
        List<ArticleDto> articles = communityService.getArticles(boardCode, keyword);
//        List<BoardDto> boards = communityService.getAllBoards();
        List<BoardDto> boards = communityService.getBoardsExceptCustomerService();
        model.addAttribute("articles", articles);
        model.addAttribute("boards", boards);
        model.addAttribute("keyword", keyword);
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

    /**************게시글 작성***************/
    @PostMapping("/communityWrite")
    public String communityWrite(
            @ModelAttribute ArticleDto articleDto,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            Authentication authentication) throws IOException {
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = authentication.getName();
            articleDto.setUserId(userId);
        } else {
            return "redirect:/user/login";
        }
        Integer articleId = communityService.createArticle(articleDto);

        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    communityService.saveFile(file, articleId);
                }
            }
        }

        return "redirect:/board/community/communityMain";
    }
    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Integer fileId) {
        ArticleFileDto file = communityService.getFileById(fileId);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.parseMediaType(file.getType()))
                .body(file.getData());
    }
    /*****************관리자 게시글 수정***********************/
    @GetMapping("communityWrite/{articleId}")
    public String adminCommunityModify(
            @PathVariable Integer articleId,
            Model model
    ){
        List<ArticleDto> articles = communityService.getAllArticles(articleId);
        model.addAttribute("articles", articles);

        return "/board/community/communityWrite";
    }
    @PostMapping("communityUpdate")
    public String adminCommunityUpdate(
            @RequestParam Integer articleId,
            @ModelAttribute ArticleDto articleDto
    ){
        articleDto.setId(articleId);
        communityService.updateArticle(articleDto);
        return "redirect:/board/community/communityPost/" + articleId; // 수정 후 리다이렉트할 경로
    }
    /**************게시글 상세 조회***************/
    @GetMapping("/communityPost/{articleId}")
    public String communityView(
            @PathVariable Integer articleId,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        ArticleDto article = communityService.getArticleById(articleId, request, response);
        List<CommentDto> comments = communityService.getCommentsByArticleId(articleId);
//        List<BoardDto> boards = communityService.getAllBoards();
        List<BoardDto> boards = communityService.getBoardsExceptCustomerService();
        List<ArticleFileDto> files = communityService.getFilesByArticleId(articleId);  // 파일 정보를 가져옴

        model.addAttribute("boards", boards);
        model.addAttribute("article", article);
        model.addAttribute("comments", comments);
        model.addAttribute("files", files);  // 모델에 파일 정보를 추가
        model.addAttribute("commentDto", new CommentDto());
        return "/board/community/communityPost";
    }


    /**************댓글 작성***************/
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

    /**************게시글 좋아요***************/
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
    /**************댓글 좋아요***************/
    @PostMapping("/comment/{commentId}/like")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> likeComment(
            @PathVariable Integer commentId,
            Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId = authentication.getName();
        boolean liked = communityService.toggleCommentLike(commentId, userId);
        int likeCount = communityService.getCommentLikeCount(commentId);
        Map<String, Object> response = new HashMap<>();
        response.put("liked", liked);
        response.put("likeCount", likeCount);
        return ResponseEntity.ok(response);
    }

    /**************댓글 삭제***************/
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

