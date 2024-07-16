package com.example.closeup.service;


import com.example.closeup.domain.dto.community.ArticleDto;
import com.example.closeup.domain.dto.community.BoardDto;
import com.example.closeup.domain.dto.community.CommentDto;
import com.example.closeup.domain.mapper.CommunityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommunityService {
    @Autowired
    private CommunityMapper communityMapper;

    public List<ArticleDto> getArticles(String boardCode) {
        return communityMapper.selectArticles(boardCode);
    }

    public List<BoardDto> getAllBoards() {
        return communityMapper.selectAllBoards();
    }


    public void createArticle(ArticleDto articleDto) {
        articleDto.setWrittenAt(LocalDateTime.now());
        articleDto.setModifiedAt(LocalDateTime.now());
        communityMapper.insertArticle(articleDto);
    }

        public ArticleDto getArticleById (Integer id){
            communityMapper.incrementViewCount(id);
            return communityMapper.selectArticleById(id);
        }

        @Transactional
        public void createComment(CommentDto commentDto){
            commentDto.setWrittenAt(LocalDateTime.now());
            communityMapper.insertComment(commentDto);
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
    }