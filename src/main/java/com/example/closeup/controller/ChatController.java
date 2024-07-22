
package com.example.closeup.controller;

import com.example.closeup.config.auth.PrincipalDetails;
import com.example.closeup.domain.dto.ChatMessageDto;
import com.example.closeup.domain.dto.ChatRoomDto;
import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("chat")
@Log4j2
public class ChatController {
    @Autowired
    private ChatService chatService;

    @GetMapping("messages/{chatRoomId}")
    @ResponseBody
    public List<ChatMessageDto> getMessages(@PathVariable Long chatRoomId) {
        return chatService.getChatMessageDtoList(chatRoomId);
    }

    // 채팅 목록 조회
    @GetMapping("list")
    @ResponseBody
    public List<ChatRoomDto> getRooms(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<ChatRoomDto> chatRoomDtoList = null;

        if (principalDetails != null) {
            String userId = principalDetails.getUserDto().getId();
            String role = principalDetails.getUserDto().getRole();

            if (role.equals("ROLE_USER")) {
                chatRoomDtoList = chatService.getChatRoomDtoListByUserId(userId);
            }

            if (role.equals("ROLE_EXPERT")) {
                ExpertDto expertDto = chatService.getExpertDto(userId);
                chatRoomDtoList = chatService.getChatRoomDtoListByExpertNickname(expertDto.getNickname());
            }

            for (ChatRoomDto chatRoomDto : chatRoomDtoList) {
                String lastChatMessage = chatService.getLastChatMessage(chatRoomDto.getId());
                String userName = chatService.getUserName(chatRoomDto.getUserId());

                chatRoomDto.setLastChatMessage(lastChatMessage);
                chatRoomDto.setUserName(userName);
            }
        }

        return chatRoomDtoList;
    }

    //채팅방 개설
    @PostMapping("room")
    public ResponseEntity<Long> postRoom(Authentication auth, @RequestBody ChatRoomDto chatRoomDto){
        PrincipalDetails principal = (PrincipalDetails) auth.getPrincipal();
        String userId = principal.getUserDto().getId();

        chatRoomDto.setUserId(userId);

        ChatRoomDto originalChatRoomDto = chatService.getChatRoomDto(chatRoomDto);

        Long roomId = originalChatRoomDto == null ? chatService.createRoom(chatRoomDto) : originalChatRoomDto.getId();

        return ResponseEntity.ok(roomId);
    }

    // 채팅방 조회
    @GetMapping("room")
    public void getRoom(@RequestParam("id") Long id, Model model) {
        ChatRoomDto chatRoomDto = chatService.getChatRoomDto(id);

        List<ChatMessageDto> chatMessageDtoList = chatService.getChatMessageDtoList(id);

        model.addAttribute("chatRoomDto", chatRoomDto);
        model.addAttribute("chatMessageDtoList", chatMessageDtoList);
    }

    @PostMapping("message")
    public ResponseEntity<Void> postMessage(@RequestBody ChatMessageDto chatMessageDto) {
        LocalDateTime date = LocalDateTime.now();
        chatMessageDto.setWrittenAt(date);

        chatService.createMessage(chatMessageDto);

        return ResponseEntity.ok().build();
    }
}
