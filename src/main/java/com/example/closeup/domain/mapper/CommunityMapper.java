package com.example.closeup.domain.mapper;


import com.example.closeup.domain.dto.ArticleDto;
import com.example.closeup.domain.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMapper {
/**********communityMain 목록 표시***********/
    List<ArticleDto> selectAllArticles();
    /**********communityWrite 게시글 작성***********/
    void insertArticle(ArticleDto articleDto);
    /**********communityPost 게시글 확인***********/
    ArticleDto selectArticleById(Integer id);
    /***************댓글 작성*********************/
    void insertComment(CommentDto commentDto);
    /***************댓글 목록*********************/
   List<CommentDto>selectCommentsByArticleId(Integer articleId);
}
