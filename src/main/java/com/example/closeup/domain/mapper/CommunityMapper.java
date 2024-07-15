package com.example.closeup.domain.mapper;


import com.example.closeup.domain.dto.community.ArticleDto;
import com.example.closeup.domain.dto.community.BoardDto;
import com.example.closeup.domain.dto.community.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMapper {
      List<ArticleDto> selectArticles(String boardCode);
    List<BoardDto> selectAllBoards();
    void insertArticle(ArticleDto articleDto);
    ArticleDto selectArticleById(Integer id);
    void incrementViewCount(Integer id);
    void insertComment(CommentDto commentDto);
    List<CommentDto> selectCommentsByArticleId(Integer articleId);
//    List<ArticleFileDto> selectFilesByArticleId(Integer articleId);
    boolean checkLikeExists(Integer articleId, String userId);
    void deleteLike(Integer articleId, String userId);
    void insertLike(Integer articleId, String userId);
    int selectLikeCount(Integer articleId);
}
