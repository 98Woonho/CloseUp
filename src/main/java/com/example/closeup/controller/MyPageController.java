package com.example.closeup.controller;


import com.example.closeup.config.auth.PrincipalDetails;
import com.example.closeup.domain.dto.ChatRoomDto;
import com.example.closeup.domain.dto.community.ArticleDto;
import com.example.closeup.domain.dto.community.BoardDto;
import com.example.closeup.service.CommunityService;
import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.domain.dto.community.ArticleDto;
import com.example.closeup.service.MyPageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private CommunityService communityService;


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

    @PostMapping("/modifyUserInfo")
    public String modifyUserInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam("password") String password,
            @RequestParam("phone") String phone,
            Model model
    ) {
        userService.updateUserProfileById(principalDetails.getUsername(), passwordEncoder.encode(password), phone);
        model.addAttribute("message", "정보 변경이 완료되었습니다.");
        return "redirect:/myPage/myPageMain";
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
            model.addAttribute("errorMessage", "비밀번호를 잘못 입력하셨습니다.");
            return "/user/myPage/modifyConfirm";
        }
    }

    @GetMapping("/chats")
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
    public String getPostManage(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            Model model
    ) {
        List<ArticleDto> articles = new ArrayList<>();
        if (!Objects.isNull(principalDetails)) {
            articles = myPageService.selectArticle(principalDetails.getUserDto().getId());
        }
        System.out.println(articles);
        model.addAttribute("articles", articles);

//    public String getPostManage(
//            Model model,
//            Authentication authentication,
//            @ModelAttribute ArticleDto articleDto) {
//        String userId = authentication.getName();
////        articleDto.setUserId(userId);
//        List<ArticleDto> articles = communityService.getMyPageArticles(userId);
//        System.out.println(articles);
//        List<BoardDto> boards = communityService.getAllBoards();
//        System.out.println(articles);
//        model.addAttribute("boards", boards);
//        model.addAttribute("articles", articles);
        return "user/myPage/postmanage";
    }

    @ResponseBody
    @DeleteMapping("/postmanage")
    public ResponseEntity<Void> deletePostManage(
            @RequestBody List<Long> articleIds
    ){
        System.out.println(articleIds);
        myPageService.deleteArticle(articleIds);
        return ResponseEntity.ok().body(null);
    }

    // 리뷰 관리
    @GetMapping("/review")
    public String getReview(Model model) {
        return "user/myPage/reviewWrite";
    }


}
