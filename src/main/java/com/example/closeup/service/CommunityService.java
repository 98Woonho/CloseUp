package com.example.closeup.service;


import com.example.closeup.domain.dto.community.ArticleDto;
import com.example.closeup.domain.dto.community.BoardDto;
import com.example.closeup.domain.dto.community.CommentDto;
import com.example.closeup.domain.mapper.CommunityMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommunityService {
    @Autowired
    private CommunityMapper communityMapper;
    /**************게시글 목록 조회***************/
    public List<ArticleDto> getArticles(String boardCode, String keyword) {
        return communityMapper.selectArticles(boardCode, keyword);
    }

    public List<ArticleDto> getArticlesByTitle(String keyword){
        return communityMapper.selectArticlesByTitle(keyword);
    }

    public List<BoardDto> getAllBoards() {
        return communityMapper.selectAllBoards();
    }

    /**************게시글 작성***************/
    public void createArticle(ArticleDto articleDto) {
        articleDto.setWrittenAt(LocalDateTime.now());
        communityMapper.insertArticle(articleDto);
    }
    /**************댓글 작성***************/
        public void createComment(CommentDto commentDto){
            commentDto.setWrittenAt(LocalDateTime.now());
            communityMapper.insertComment(commentDto);
        }
    /**************게시글 상세 조회***************/
    public ArticleDto getArticleById (Integer id, HttpServletRequest request, HttpServletResponse response ){
        incrementViewCountIfNeeded(id, request, response);
        return communityMapper.selectArticleById(id);
    }
    /**************쿠키로 조회수 조절***************/
    private void incrementViewCountIfNeeded(Integer articleId, HttpServletRequest request, HttpServletResponse response) {
        String cookieName = "viewedArticle_" + articleId;
        Cookie[] cookies = request.getCookies();
        boolean viewed = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    viewed = true;
                    break;
                }
            }
        }

        if (!viewed) {
            communityMapper.incrementViewCount(articleId);
            Cookie viewedCookie = new Cookie(cookieName, LocalDate.now().toString());
            viewedCookie.setMaxAge(24 * 60 * 60); // 쿠키 유효 기간을 24시간으로 설정
            viewedCookie.setPath("/"); // 쿠키의 유효 경로 설정
            response.addCookie(viewedCookie);
        }
    }

        public List<CommentDto> getCommentsByArticleId (Integer articleId){
            return communityMapper.selectCommentsByArticleId(articleId);
        }

//        public List<ArticleFileDto> getFilesByArticleId (Integer articleId){
//            return communityMapper.selectFilesByArticleId(articleId);
//        }
    /**************게시글 좋아요 토글***************/
    public boolean toggleLike (Integer articleId, String userId){
            boolean exists = communityMapper.checkLikeExists(articleId, userId);
            if (exists) {
                communityMapper.deleteLike(articleId, userId);
                return false;
            } else {
                communityMapper.insertLike(articleId, userId);
                return true;
            }
        }

        public int getLikeCount (Integer articleId){

        return communityMapper.selectLikeCount(articleId);
        }
    /**************댓글 삭제***************/
    public void deleteComment(Integer commentId) {
        communityMapper.deleteComment(commentId);
    }


    public CommentDto getCommentById(Integer commentId) {

        return communityMapper.selectCommentById(commentId);
    }
    /**************댓글 좋아요 토글***************/
    public boolean toggleCommentLike(Integer commentId, String userId) {
        boolean exists = communityMapper.checkCommentLikeExists(commentId, userId);
        if (exists) {
            communityMapper.deleteCommentLike(commentId, userId);
            return false;
        } else {
            communityMapper.insertCommentLike(commentId, userId);
            return true;
        }
    }

    public int getCommentLikeCount(Integer commentId) {
        return communityMapper.selectCommentLikeCount(commentId);
    }
}

