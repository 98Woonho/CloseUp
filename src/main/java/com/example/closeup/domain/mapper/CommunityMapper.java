package com.example.closeup.domain.mapper;


import com.example.closeup.domain.dto.community.ArticleDto;
import com.example.closeup.domain.dto.community.ArticleFileDto;
import com.example.closeup.domain.dto.community.BoardDto;
import com.example.closeup.domain.dto.community.CommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommunityMapper {
    /**************게시글 목록 조회***************/
    List<ArticleDto> selectArticles(@Param("boardCode") String boardCode, @Param("keyword") String keyword);
      List<ArticleDto> selectArticlesByTitle(String title);
      List<BoardDto> selectAllBoards();
    List<BoardDto> selectBoardsExceptCustomerService();
    List<ArticleDto> selectMyPageArticles(String userId);
    /**************게시글 작성***************/
    void insertArticle(ArticleDto articleDto);
    /**************게시글 상세 조회***************/
    ArticleDto selectArticleById(Integer id);
    /**************게시글 조회수 증가***************/
    void incrementViewCount(Integer id);
    /**************댓글 작성***************/
    void insertComment(CommentDto commentDto);
    List<CommentDto> selectCommentsByArticleId(Integer articleId);
//    List<ArticleFileDto> selectFilesByArticleId(Integer articleId);
    /**************게시글 좋아요 확인***************/
    boolean checkLikeExists(Integer articleId, String userId);
    /**************게시글 좋아요 삭제***************/
    void deleteLike(Integer articleId, String userId);
    /**************게시글 좋아요 추가***************/
    void insertLike(Integer articleId, String userId);
    int selectLikeCount(Integer articleId);
    CommentDto selectCommentById(Integer commentId);
    void deleteComment(Integer commentId);
    /**************댓글 좋아요 확인***************/
    boolean checkCommentLikeExists(Integer commentId, String userId);
    /**************댓글 좋아요 삭제***************/
    void deleteCommentLike(Integer commentId, String userId);
    /**************댓글 좋아요 추가***************/
    void insertCommentLike(Integer commentId, String userId);
    /**************댓글 삭제***************/
    int selectCommentLikeCount(Integer commentId);

    void insertFile(ArticleFileDto fileDto);
    List<ArticleFileDto> selectFilesByArticleId(Integer articleId);
    ArticleFileDto selectFileById(@Param("fileId") Integer fileId);

    /************관리자 게시글 수정************/
    List<ArticleDto> selectAllArticles(Integer articleId);
    void updateArticle(ArticleDto articleDto);

    /************고객센터************/
    List<ArticleDto> selectArticlesByBoardCode(@Param("boardCode") String boardCode);


}

