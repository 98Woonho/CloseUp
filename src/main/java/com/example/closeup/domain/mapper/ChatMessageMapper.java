package com.example.closeup.domain.mapper;

import com.example.closeup.domain.dto.ChatMessageDto;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ChatMessageMapper {
    void insertChatMessage(ChatMessageDto chatMessageDto);

    List<ChatMessageDto> selectChatMessageDtoListByChatRoomId(Long chatRoomId);

    String selectLastChatMessageByChatRoomId(Long chatRoomId);

    ChatMessageDto selectLastChatMessageDtoByChatRoomId(Long chatRoomId);

    void updateChatMessageWrittenAtById(Long id);

    int selectChatMessageCountByTimeBetweenAndChatRoomId(Long chatRoomId, LocalDateTime start, LocalDateTime end);
}
