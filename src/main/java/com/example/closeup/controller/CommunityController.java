package com.example.closeup.controller;


import com.example.closeup.domain.dto.ArticleDto;
import com.example.closeup.service.CommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("community")
public class CommunityController {
    @Autowired
    CommunityService communityService;


    @GetMapping("community_write")
    public void communityWrite(Model model) {

    }


    @PostMapping("/community_write")
    public String communityWrite(ArticleDto articleDto) {
        System.out.println(articleDto);
    communityService.CommunityWrite(articleDto);
    return "redirect:/community_main";
    }



}
