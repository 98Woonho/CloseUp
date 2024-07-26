package com.example.closeup.domain.mapper;


import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.domain.dto.community.ArticleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface AdminMapper {

    /**********관리자 페이지 유저************/
    List<UserDto> selectAllUsers();
    void updateUser(UserDto user);
    void deleteUser(String id);
    List<UserDto> selectUsersByRole(String role);
    List<UserDto> searchUsers(
            @Param("name") String name,
            @Param("id") String id,
            @Param("phone") String phone,
            @Param("expertStatus") String expertStatus);
    void deleteExpertUser(String id);

    /*********관리자 페이지 게시판***********/
    List<ArticleDto> selectAllArticles();
    List<ArticleDto> selectArticleByUser(String boardCode);

    List<ArticleDto> searchArticles(
            @Param("userId") String userId,
            @Param("title") String title,
            @Param("dateTime") String dateTime,
            @Param("content") String content);
    void deleteArticle(String userId);
}
