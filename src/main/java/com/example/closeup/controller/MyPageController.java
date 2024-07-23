package com.example.closeup.controller;


import com.example.closeup.config.auth.PrincipalDetails;
import com.example.closeup.domain.dto.ChatRoomDto;
import com.example.closeup.service.MyPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@Controller
@RequestMapping("/myPage")
public class MyPageController {
    @Autowired
    private MyPageService myPageService;
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

  
    @GetMapping("/myPageMain")
    public String getMyPageMain(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            Model model
    ) {
        UserDto user = principalDetails.getUserDto();
        model.addAttribute("user", user);
        return "user/myPage/myPageMain";
    }

    @GetMapping("/modifyUserInfo")
    public String getModify(Model model) {
        return "user/myPage/modifyUserInfo";
    }

    // 회원정보 수정을 위한 비밀번호 입력 페이지
    @GetMapping("/modifyConfirm")
    public String getConfirm(Model model) {
        return "user/myPage/modifyConfirm";
    }

    @PostMapping("/modifyConfirm")
    public String postModifyConfirm(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam("password") String password,
            Model model
    ) {
        UserDto user = userService.findUserById(principalDetails.getUsername());

        if(passwordEncoder.matches(password, user.getPassword())) {
            return "redirect:/myPage/modifyUserInfo";
        } else {
            model.addAttribute("errorMessage", "비밀번호가 틀렸습니다.");
            return "/user/myPage/modifyConfirm";
        }
    }

    @GetMapping("chats")
    public String getChats(@RequestParam(value="roomId", required = false) Long roomId, Authentication auth, Model model) {
        PrincipalDetails principal = (PrincipalDetails) auth.getPrincipal();
        String userId = principal.getUserDto().getId();

        ChatRoomDto selectedChatRoomDto = myPageService.getChatRoomDto(roomId);

        List<ChatRoomDto> chatRoomDtoList = myPageService.getChatRoomDtoList(userId);

        for (ChatRoomDto chatRoomDto : chatRoomDtoList) {
            String lastChatMessage = myPageService.getLastChatMessage(chatRoomDto.getId());

            chatRoomDto.setLastChatMessage(lastChatMessage);
        }

        model.addAttribute("chatRoomDtoList", chatRoomDtoList);
        model.addAttribute("selectedChatRoomDto", selectedChatRoomDto);

        return "user/myPage/chatRecord";
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
