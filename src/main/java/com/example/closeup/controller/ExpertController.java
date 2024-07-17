package com.example.closeup.controller;

import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/expert")
public class ExpertController {
    @Autowired private ExpertService expertService;

    @GetMapping("map")
    public String getMap(Model model) {
        return "map/mapMain";
    }

    @GetMapping("myPageMain")
    public String getExpertMyPageMain(Model model) {
        return "user/myPage/expert/myPageMainEx";
    }

    @GetMapping("modifyExpertInfo")
    public String getModifyExpertInfo(Model model) {
        return "user/myPage/expert/modifyExpertInfo";
    }

    @GetMapping("addPortfolio")
    public String getAddPortfolio(Model model) {
        return "user/myPage/expert/addPortfolio";
    }

    @GetMapping("chatRequest")
    public String getChatRequest(Model model) {
        return "user/myPage/expert/chatRequest";
    }
}
