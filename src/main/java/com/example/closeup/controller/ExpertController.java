package com.example.closeup.controller;

import com.example.closeup.domain.dto.ExpertDetailDto;
import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/expert")
public class ExpertController {
    @Autowired
    private ExpertService expertService;

    // 닉네임으로 expert table에서 정보 가져오기
    @GetMapping("/{nickname}")
    @ResponseBody

    public ExpertDto getExpert(@PathVariable String nickname) {
        return expertService.getExpertDto(nickname);
    }

    // 닉네임과 카테고리로 expert_detail table에서 정보 가져오기
    @GetMapping("/detail/{nickname}/{category}")
    @ResponseBody
    public List<ExpertDetailDto> getDetail(@PathVariable String nickname,
                                           @PathVariable String category) {
        return expertService.getExpertDetailDtoList(nickname, category);

    }

    @GetMapping("map")
    public String getMap(Model model) {
        List<ExpertDto> expertInformation = expertService.selectExpertInformation();
        model.addAttribute("expertInfo", expertInformation);

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
