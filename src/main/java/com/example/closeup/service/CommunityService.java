package com.example.closeup.service;


import com.example.closeup.domain.dto.ArticleDto;
import com.example.closeup.domain.mapper.CommunityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityService {
    @Autowired
    CommunityMapper communityMapper;

    public void CommunityWrite(ArticleDto articleDto){
        communityMapper.insertCommunityWrite(articleDto);
    }
}
