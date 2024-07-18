package com.example.closeup.controller;


import com.example.closeup.config.auth.PrincipalDetails;
import com.example.closeup.domain.dto.ChatRoomDto;
import com.example.closeup.service.MyPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import com.example.closeup.config.auth.PrincipalDetails;
import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.Model;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/myPage")
public class MyPageController {

    @Autowired
    private MyPageService myPageService;

    @Autowired
    private UserService userService;


    @GetMapping("/myPageMain")
    public String getMyPageMain(Model model) {
        return "user/myPage/myPageMain";
    }

    @ResponseBody
    @GetMapping("/profileImage")
    public ResponseEntity<byte[]> getProfileImage(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) throws Exception {
        byte[] data = principalDetails.getUserDto().getProfileImg();
        return ResponseEntity.ok()
                .contentLength(data.length)
                .body(data);
    }

    @GetMapping("/modifyUserInfo")
    public String modify(Model model) {
        return "user/myPage/modifyUserInfo";
    }

    @GetMapping("/modifyConfirm")
    public String confirm(Model model) {
        return "user/myPage/modifyConfirm";
    }

    @GetMapping("chats")
    public String getChats(Authentication auth, Model model) {
        PrincipalDetails principal = (PrincipalDetails) auth.getPrincipal();
        String userId = principal.getUserDto().getId();

        List<ChatRoomDto> chatRoomDtoList = myPageService.getChatRoomDtoList(userId);

        System.out.println(chatRoomDtoList);

        for (ChatRoomDto chatRoomDto : chatRoomDtoList) {
            String lastChatMessage = myPageService.getLastChatMessage(chatRoomDto.getId());

            chatRoomDto.setLastChatMessage(lastChatMessage);
        }

        model.addAttribute("chatRoomDtoList", chatRoomDtoList);

        return "user/myPage/chatRecord";
    }

    @GetMapping("/wishlist")
    public String getWishlist(Model model) {
        return "user/myPage/wishlist";
    }

    @GetMapping("/payment")
    public String getPayment(Model model) {
        return "user/myPage/payment";
    }

    @GetMapping("/postmanage")
    public String getPostManage(Model model) {
        return "user/myPage/postmanage";
    }
}
