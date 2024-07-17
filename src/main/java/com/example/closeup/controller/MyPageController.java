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
    @GetMapping("chats")
    public String getChats(Model model) {
        return "myPage/chat_record";
    }
}
