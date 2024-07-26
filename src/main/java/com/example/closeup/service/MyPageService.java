package com.example.closeup.service;

import com.example.closeup.domain.dto.ChatRoomDto;
import com.example.closeup.domain.dto.community.ArticleDto;
import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.domain.mapper.ChatMessageMapper;
import com.example.closeup.domain.mapper.ChatRoomMapper;
import com.example.closeup.domain.mapper.MyPageMapper;
import com.example.closeup.domain.mapper.ExpertMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageService {
    @Autowired
    private ChatRoomMapper chatRoomMapper;

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Autowired
    private MyPageMapper myPageMapper;

    @Autowired
    private ExpertMapper expertMapper;

    public List<ChatRoomDto> getChatRoomDtoList(String userId) {
        return chatRoomMapper.selectChatRoomDtoListByUserId(userId);
    }

    public String getLastChatMessage(Long chatRoomId) {
        return chatMessageMapper.selectLastChatMessageByChatRoomId(chatRoomId);
    }

    public ChatRoomDto getChatRoomDto(Long roomId) {
        return chatRoomMapper.selectChatRoomDtoById(roomId);
    }

//    public ExpertDto getExpertDtoBySelectedChatRoom(String userId) {
//        return expertMapper.selectExpertByUserId(userId);
//    }
    /******************** 마이페이지 - 게시글 관리 *********************/
    public List<ArticleDto> selectArticle(String userId) {
        return myPageMapper.selectArticleByUserId(userId);
    }
    // 삭제

    public void deleteArticle(List<Long> articles){
        myPageMapper.deleteArticleById(articles);
    }

}
