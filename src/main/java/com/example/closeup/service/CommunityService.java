package com.example.closeup.service;


import com.example.closeup.domain.dto.ArticleDto;
import com.example.closeup.domain.mapper.CommunityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommunityService {
    @Autowired
    private CommunityMapper communityMapper;

    public List<ArticleDto> getAllArticles() {
        return communityMapper.selectAllArticles();
    }

    public void createArticle(ArticleDto articleDto) {
        articleDto.setWrittenAt(LocalDateTime.now());
        articleDto.setModifiedAt(LocalDateTime.now());
        communityMapper.insertArticle(articleDto);
    }

    public ArticleDto getArticleById(Long id) {
        return communityMapper.selectArticleById(id);
    }
}
