package com.example.closeup.domain.mapper;

import com.example.closeup.domain.dto.ChatMessageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMessageMapper {
    void insertChatMessage(ChatMessageDto chatMessageDto);

    List<ChatMessageDto> selectChatMessageDtoListByChatRoomId(Long chatRoomId);
}