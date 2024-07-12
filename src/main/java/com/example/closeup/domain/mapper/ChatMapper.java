package com.example.closeup.domain.mapper;

import com.example.closeup.domain.dto.ChatRoomDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
    List<ChatRoomDto> selectChatRoomDtoListByUserId(String userId);

    ChatRoomDto selectChatRoomDtoById(Long id);

    void insertChatRoom(ChatRoomDto chatRoomDto);
}
