package com.example.closeup.controller;

import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.service.ExpertService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired private ExpertService expertService;

    @GetMapping("expert/mapData")
    public List<ExpertDto> getMapData(Model model) {
        List<ExpertDto> expertInformation = expertService.getExpertInformation();
        model.addAttribute("expertInfo", expertInformation);
        System.out.println(expertInformation);
        return expertInformation;
    }
}
