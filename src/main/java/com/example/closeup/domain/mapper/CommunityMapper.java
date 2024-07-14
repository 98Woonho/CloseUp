package com.example.closeup.domain.mapper;


import com.example.closeup.domain.dto.ArticleDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommunityMapper {

    ArticleDto insertCommunityWrite(ArticleDto articleDto);
}
