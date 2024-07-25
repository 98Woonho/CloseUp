package com.example.closeup.service;

import com.example.closeup.domain.dto.ChatMessageDto;
import com.example.closeup.domain.dto.ChatRoomDto;
import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.domain.mapper.ChatMessageMapper;
import com.example.closeup.domain.mapper.ChatRoomMapper;
import com.example.closeup.domain.mapper.ExpertMapper;
import com.example.closeup.domain.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatRoomMapper chatRoomMapper;

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Autowired
    private ExpertMapper expertMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public Long createRoom(ChatRoomDto chatRoomDto) {
        chatRoomMapper.insertChatRoom(chatRoomDto);

        return chatRoomDto.getId();
    }

    public ExpertDto getExpertDto(String userId) {
        return expertMapper.selectExpertByUserId(userId);
    }

    public String getUserName(String userId) {
        return userMapper.selectUserNameById(userId);
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
    public ChatRoomDto getChatRoomDto(ChatRoomDto chatRoomDto) {
        return chatRoomMapper.selectChatRoomDtoByUserIdAndExpertNickname(chatRoomDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ChatRoomDto> getChatRoomDtoListByUserId(String userId) {
        return chatRoomMapper.selectChatRoomDtoListByUserId(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ChatRoomDto> getChatRoomDtoListByExpertNickname(String nickname) {
        return chatRoomMapper.selectChatRoomDtoListByExpertNickname(nickname);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ChatMessageDto> getChatMessageDtoList(Long chatRoomId) {
        return chatMessageMapper.selectChatMessageDtoListByChatRoomId(chatRoomId);
    }

    @Transactional(rollbackFor = Exception.class)
    public int getMessageCount(Long chatRoomId, LocalDateTime start, LocalDateTime end) {
        return chatMessageMapper.selectChatMessageCountByTimeBetweenAndChatRoomId(chatRoomId, start, end);
    }

    @Transactional(rollbackFor = Exception.class)
    public String getLastChatMessage(Long chatRoomId) {
        return chatMessageMapper.selectLastChatMessageByChatRoomId(chatRoomId);
    }

    @Transactional(rollbackFor = Exception.class)
    public ChatMessageDto getLastChatMessageDto(Long chatRoomId) {
        return chatMessageMapper.selectLastChatMessageDtoByChatRoomId(chatRoomId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateChatRoom(ChatRoomDto chatRoomDto) {
        chatRoomMapper.updateChatRoom(chatRoomDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateChatMessageWrittenAt(Long id) {
        chatMessageMapper.updateChatMessageWrittenAtById(id);
    }
}