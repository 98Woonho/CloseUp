package com.example.closeup.controller;

import com.example.closeup.config.auth.PrincipalDetails;
import com.example.closeup.domain.dto.ChatMessageDto;
import com.example.closeup.domain.dto.ChatRoomDto;
import com.example.closeup.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("chat")
@Log4j2
public class ChatController {
    @Autowired
    private ChatService chatService;

    // 채팅 목록 조회
    @GetMapping(value = "/list")
    public void getRooms(Authentication auth, Model model) {
        PrincipalDetails principal = (PrincipalDetails) auth.getPrincipal();
        String userId = principal.getUserDto().getId();

        List<ChatRoomDto> chatRoomDtoList = chatService.getChatRoomDtoList(userId);

        model.addAttribute("chatRoomDtoList", chatRoomDtoList);
    }

    //채팅방 개설
    @PostMapping(value = "/room")
    public String postRoom(ChatRoomDto chatRoomDto){
        chatService.createRoom(chatRoomDto);

        return "redirect:/chat/list";
    }

    //채팅방 조회
    @GetMapping("/room")
    public void getRoom(@RequestParam("id") Long id, Model model) {
        ChatRoomDto chatRoomDto = chatService.getChatRoomDto(id);

        model.addAttribute("chatRoomDto", chatRoomDto);
    }
}
