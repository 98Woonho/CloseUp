package com.example.closeup.controller;

import com.example.closeup.domain.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {
    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
//    @MessageMapping(value = "/chat/enter")
//    public void enter(ChatMessageDto chatMessageDto){
//        chatMessageDto.setContent("");
//        template.convertAndSend("/sub/chat/room/" + chatMessageDto.getChatRoomId(), chatMessageDto);
//    }

    // /pub/chat/message
    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDto chatMessageDto){
        template.convertAndSend("/sub/chat/room/" + chatMessageDto.getChatRoomId(), chatMessageDto);
    }
}
