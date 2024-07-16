package com.example.closeup.controller;

import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.service.ExpertService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/expert")
public class ExpertController {
    @Autowired private ExpertService expertService;

    @GetMapping("/map")
    public String get_expert_information(Model model) {
        List<ExpertDto> expertInformation = expertService.getExpertInformation();
        model.addAttribute("expertInformation", expertInformation);
        System.out.println(expertInformation);

        return "map/mapMain";
    }
}
