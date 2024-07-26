package com.example.closeup.controller;

import com.example.closeup.domain.dto.community.ArticleDto;
import com.example.closeup.domain.dto.community.CommentDto;
import com.example.closeup.service.CommunityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.closeup.config.auth.PrincipalDetails;
import com.example.closeup.domain.dto.PaymentDto;
import com.example.closeup.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private CommunityService communityService;

    @Autowired
    private BoardService boardService;

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
    public String getCSMain(Model model,
                            @RequestParam(required = false) String keyword) {
        List<ArticleDto> articles;
        if (keyword != null && !keyword.isEmpty()) {
            // 키워드가 제공된 경우 검색 수행
            articles = communityService.getArticles("customerService", keyword);
        } else {
            // 키워드가 없는 경우 모든 게시글 가져오기
            articles = communityService.getArticlesByBoardCode("customerService");
        }
        model.addAttribute("articles", articles);
        model.addAttribute("keyword", keyword);

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

    @GetMapping("/CSCenter/CSPost/{articleId}")
    public String getCSPost(@PathVariable Integer articleId, Model model, HttpServletRequest request, HttpServletResponse response) {
        ArticleDto article = communityService.getArticleById(articleId, request, response);
        List<CommentDto> comments = communityService.getCommentsByArticleId(articleId);

        model.addAttribute("article", article);
        model.addAttribute("comments", comments);
        return "/board/CSCenter/CSPost";
    }

    @PostMapping("/CSCenter/CSPost/{articleId}")
    public String postCSComment(
            @PathVariable Integer articleId,
            @ModelAttribute CommentDto commentDto,
            Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = authentication.getName();
            commentDto.setUserId(userId);
        } else {
            return "redirect:/user/login";
        }
        commentDto.setArticleId(articleId);
        communityService.createComment(commentDto);
        return "redirect:/board/CSCenter/CSPost/" + articleId;
    }
    // 고객 센터
    @GetMapping("/cs")
    public String getCSCenter() {
        return "board/CSCenter/CSMain";
    }

    @GetMapping("/csWrite")
    public String getCSCenterWrite() {
        return "board/CSCenter/CSWrite";
    }
    @GetMapping("/csPost")
    public String getCSCenterPost() {
        return "board/CSCenter/CSPost";
    }

    /**************댓글 좋아요***************/
    @PostMapping("/CSCenter/comment/{commentId}/like")
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
    @DeleteMapping("/CSCenter/comment/{commentId}")
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

    @PostMapping("findExpertWrite")
    public ResponseEntity<String> postFindExpertWrite(@RequestBody PaymentDto paymentDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println(paymentDto);
        paymentDto.setUserId(principalDetails.getUsername());
        boardService.payment(paymentDto);

        return ResponseEntity.ok().body("결제가 완료 되었습니다. 결제 내역 페이지로 이동합니다.");
    }

}
