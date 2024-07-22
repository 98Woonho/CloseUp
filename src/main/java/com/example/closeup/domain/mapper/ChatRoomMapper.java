package com.example.closeup.domain.mapper;

import com.example.closeup.domain.dto.ChatMessageDto;
import com.example.closeup.domain.dto.ChatRoomDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatRoomMapper {
    List<ChatRoomDto> selectChatRoomDtoListByUserId(String userId);

    List<ChatRoomDto> selectChatRoomDtoListByExpertNickname(String expertNickname);

    ChatRoomDto selectChatRoomDtoById(Long id);

    ChatRoomDto selectChatRoomDtoByUserIdAndExpertNickname(ChatRoomDto chatRoomDto);

    void insertChatRoom(ChatRoomDto chatRoomDto);

    void updateChatRoom(ChatRoomDto chatRoomDto);
}
