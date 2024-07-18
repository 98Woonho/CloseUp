package com.example.closeup.service;

import com.example.closeup.domain.dto.ChatRoomDto;
import com.example.closeup.domain.mapper.ChatMessageMapper;
import com.example.closeup.domain.mapper.ChatRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageService {
    @Autowired
    private ChatRoomMapper chatRoomMapper;

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    public List<ChatRoomDto> getChatRoomDtoList(String userId) {
        return chatRoomMapper.selectChatRoomDtoListByUserId(userId);
    }

    public String getLastChatMessage(Long chatRoomId) {
        return chatMessageMapper.selectLastChatMessageByChatRoomId(chatRoomId);
    }
}
