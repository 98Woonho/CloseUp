package com.example.closeup.domain.mapper;

import com.example.closeup.domain.dto.community.ArticleDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {
    List<ArticleDto> selectArticleByUserId(String userId);
    void deleteArticleById(List<Long> articles);
}
