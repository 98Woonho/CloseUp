package com.example.closeup.service;

import com.example.closeup.domain.dto.ChatRoomDto;
import com.example.closeup.domain.mapper.ChatMapper;
import com.example.closeup.domain.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatMapper chatMapper;


    public void createRoom(ChatRoomDto chatRoomDto) {
        chatMapper.insertChatRoom(chatRoomDto);
    }

    public ChatRoomDto getChatRoomDto(Long id) {
        return chatMapper.selectChatRoomDtoById(id);
    }

    public List<ChatRoomDto> getChatRoomDtoList(String userId) {
        return chatMapper.selectChatRoomDtoListByUserId(userId);
    }
}
