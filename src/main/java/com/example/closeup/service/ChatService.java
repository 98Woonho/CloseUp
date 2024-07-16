package com.example.closeup.service;

import com.example.closeup.domain.dto.ChatMessageDto;
import com.example.closeup.domain.dto.ChatRoomDto;
import com.example.closeup.domain.mapper.ChatMessageMapper;
import com.example.closeup.domain.mapper.ChatRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatRoomMapper chatRoomMapper;

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Transactional(rollbackFor = Exception.class)
    public void createRoom(ChatRoomDto chatRoomDto) {
        chatRoomMapper.insertChatRoom(chatRoomDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public void createMessage(ChatMessageDto chatMessageDto) {
        chatMessageMapper.insertChatMessage(chatMessageDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public ChatRoomDto getChatRoomDto(Long id) {
        return chatRoomMapper.selectChatRoomDtoById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ChatRoomDto> getChatRoomDtoList(String userId) {
        return chatRoomMapper.selectChatRoomDtoListByUserId(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ChatMessageDto> getChatMessageDtoList(Long chatRoomId) {
        return chatMessageMapper.selectChatMessageDtoListByChatRoomId(chatRoomId);
    }
}
