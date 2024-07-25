package com.example.closeup.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomDto {
    private Long id;
    private String userId;
    private String userName;
    private String lastChatMessage;
    private String expertNickname;
    private int notReadUserMessageCount;
    private int notReadExpertMessageCount;
    private String action;

    //WebSocketSession은 Spring에서 Websocket Connection이 맺어진 세션
    private Set<WebSocketSession> sessions = new HashSet<>();
}
