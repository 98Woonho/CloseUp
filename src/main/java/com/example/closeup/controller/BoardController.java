package com.example.closeup.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("board")
public class BoardController {

    @GetMapping("/communityMain")
    public String getCommunityMain() {
        return "board/community/communityMain";
    }

    @GetMapping("/communityPost")
    public String getCommunityPost() {
        return "board/community/communityPost";
    }

    @GetMapping("/communityWrite")
    public String getCommunityWrite() {
        return "board/community/communityWrite";
    }

    @GetMapping("/findExpertMain")
    public String getFindExpertMain() {
        return "board/findExpert/findExpertMain";
    }

    @GetMapping("/findExpertPost")
    public String getFindExpertPost() {
        return "board/findExpert/findExpertPost";
    }

    @GetMapping("/findExpertWrite")
    public String getFindExpertWrite() {
        return "board/findExpert/findExpertWrite";
    }

    @GetMapping("/cs")
    public String getCSCenter() {
        return "board/CSCenter/CSMain";
    }
}
