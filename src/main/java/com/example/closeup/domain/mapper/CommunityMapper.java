package com.example.closeup.domain.mapper;


import com.example.closeup.domain.dto.ArticleDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMapper {

    List<ArticleDto> selectAllArticles();
    void insertArticle(ArticleDto articleDto);
    ArticleDto selectArticleById(Long id);
}
