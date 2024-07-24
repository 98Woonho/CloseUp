package com.example.closeup.service;


import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.domain.dto.community.ArticleDto;
import com.example.closeup.domain.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    AdminMapper adminMapper;
    /*********************관리자 페이지**********************/
    public List<UserDto> getAllUsers() {
        return adminMapper.selectAllUsers();
    }
    public void updateUser(UserDto user) {
        adminMapper.updateUser(user);
    }
    public List<UserDto> getUsersByRole(String role) {
        return adminMapper.selectUsersByRole(role);
    }
    public List<UserDto> searchUsers(String name, String id, String phone, String expertStatus) {
        return adminMapper.searchUsers(name, id, phone, expertStatus);
    }
    @Transactional
    public void deleteUser(String id) {
        adminMapper.deleteExpertUser(id);
        adminMapper.deleteUser(id);
    }
    /**************관리자 페이지****************/
    public List<ArticleDto> getAllArticles() {
        return adminMapper.selectAllArticles();
    }
    public List<ArticleDto> getAllArticlesByBoardCode(String boardCode){
        return adminMapper.selectArticleByUser(boardCode);
    }
    public List<ArticleDto> searchArticles(String userId, String title, String dateTime, String content ){
        return adminMapper.searchArticles(userId, title, dateTime, content);
    }
    public void deleteArticle(String id){
        adminMapper.deleteArticle(id);
    }

}
