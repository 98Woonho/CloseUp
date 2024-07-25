package com.example.closeup.service;


import com.example.closeup.domain.dto.community.ArticleDto;
import com.example.closeup.domain.dto.community.ArticleFileDto;
import com.example.closeup.domain.dto.community.BoardDto;
import com.example.closeup.domain.dto.community.CommentDto;
import com.example.closeup.domain.mapper.CommunityMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public List<ArticleDto> getMyPageArticles(String userId) {
        return communityMapper.selectMyPageArticles(userId);
    }

    public List<ArticleDto> getArticlesByTitle(String keyword){
        return communityMapper.selectArticlesByTitle(keyword);
    }

    public List<BoardDto> getAllBoards() {
        return communityMapper.selectAllBoards();
    }

    public List<BoardDto> getBoardsExceptCustomerService() {
    return communityMapper.selectBoardsExceptCustomerService();
}

//    /**************게시글 작성***************/
//    public void createArticle(ArticleDto articleDto) {
//        articleDto.setWrittenAt(LocalDateTime.now());
//        communityMapper.insertArticle(articleDto);
//    }
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

    public void saveFile(MultipartFile file, Integer articleId) throws IOException {
        ArticleFileDto fileDto = new ArticleFileDto();
        fileDto.setArticleId(articleId);
        fileDto.setName(file.getOriginalFilename());
        fileDto.setType(file.getContentType());
        fileDto.setSize((int) file.getSize());
        fileDto.setData(file.getBytes());
        fileDto.setCreatedAt(LocalDateTime.now());
        communityMapper.insertFile(fileDto);
    }
    public ArticleFileDto getFileById(Integer fileId) {
        return communityMapper.selectFileById(fileId);
    }


    public List<ArticleFileDto> getFilesByArticleId(Integer articleId) {
        return communityMapper.selectFilesByArticleId(articleId);
    }
    public Integer createArticle(ArticleDto articleDto) {
        articleDto.setWrittenAt(LocalDateTime.now());
        communityMapper.insertArticle(articleDto);
        return articleDto.getId(); // 게시글 ID 반환
    }

    /***********관리자 페이지 게시글 수정*************/
    public List<ArticleDto> getAllArticles(Integer articleId){
        return communityMapper.selectAllArticles(articleId);
    }
    public void updateArticle(ArticleDto articleDto) {
        // 기존 게시글 정보 가져오기
        ArticleDto existingArticle = communityMapper.selectArticleById(articleDto.getId());

        // 변경된 필드만 업데이트
        if (articleDto.getBoardCode() != null) {
            existingArticle.setBoardCode(articleDto.getBoardCode());
        }
        if (articleDto.getTitle() != null) {
            existingArticle.setTitle(articleDto.getTitle());
        }
        if (articleDto.getContent() != null) {
            existingArticle.setContent(articleDto.getContent());
        }

        existingArticle.setModifiedAt(LocalDateTime.now());

        communityMapper.updateArticle(existingArticle);
    }

    /***********고객센터*************/
    public List<ArticleDto> getArticlesByBoardCode(String boardCode) {
        return communityMapper.selectArticlesByBoardCode(boardCode);
    }


}

