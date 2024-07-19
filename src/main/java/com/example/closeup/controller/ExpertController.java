package com.example.closeup.controller;

import com.example.closeup.config.auth.PrincipalDetails;
import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/expert")
public class ExpertController {
    @Autowired
    private ExpertService expertService;

    @GetMapping("/{id}")
    @ResponseBody
    public ExpertDto getExpert(@PathVariable String id) {
        return expertService.selectExpertDtoByUserId(id);
    }

    @GetMapping("map")
    public String getMap(Model model) {
        List<ExpertDto> expertInformation = expertService.selectExpertInformation();
        model.addAttribute("expertInfo", expertInformation);

        return "map/mapMain";
    }

    @GetMapping("myPageMain")
    public String getExpertMyPageMain(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            Model model
    ) {
        ExpertDto expert = expertService.selectExpertDtoByUserId(principalDetails.getUsername());
        model.addAttribute("expert", expert);
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

    @PostMapping("/addPortfolio")
    public String addPortfolio() {
        return "redirect:/myPage/myPageMain";
    }

    @GetMapping("chatRequest")
    public String getChatRequest(Model model) {
        return "user/myPage/expert/chatRequest";
    }
}
