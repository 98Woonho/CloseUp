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

    public List<ArticleDto> getArticles(String boardCode) {
        return communityMapper.selectArticles(boardCode);
    }

    public List<ArticleDto> getArticlesByTitle(String keyword){
        return communityMapper.selectArticlesByTitle(keyword);
    }

    public List<BoardDto> getAllBoards() {
        return communityMapper.selectAllBoards();
    }


    public void createArticle(ArticleDto articleDto) {
        articleDto.setWrittenAt(LocalDateTime.now());
        communityMapper.insertArticle(articleDto);
    }

        public void createComment(CommentDto commentDto){
            commentDto.setWrittenAt(LocalDateTime.now());
            communityMapper.insertComment(commentDto);
        }

    public ArticleDto getArticleById (Integer id, HttpServletRequest request, HttpServletResponse response ){
        incrementViewCountIfNeeded(id, request, response);
        return communityMapper.selectArticleById(id);
    }

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
    public void deleteComment(Integer commentId) {
        communityMapper.deleteComment(commentId);
    }
    public CommentDto getCommentById(Integer commentId) {
        return communityMapper.selectCommentById(commentId);
    }
    }
