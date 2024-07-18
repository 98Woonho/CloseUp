package com.example.closeup.controller;

import ch.qos.logback.core.model.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("myPage")
public class MyPageController {
    @GetMapping("/myPageMain")
    public String getMyPageMain(Model model) {
        return "user/myPage/myPageMain";
    }

    @GetMapping("/modifyUserInfo")
    public String modify(Model model) {
        return "user/myPage/modifyUserInfo";
    }

    @GetMapping("/modifyConfirm")
    public String confirm(Model model) {
        return "user/myPage/modifyConfirm";
    }

    @GetMapping("/chats")
    public String getChats(Model model) {
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
